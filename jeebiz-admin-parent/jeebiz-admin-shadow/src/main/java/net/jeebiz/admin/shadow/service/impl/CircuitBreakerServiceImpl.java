/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.shadow.utils.CircuitBreakerUtil;

@Slf4j
public class CircuitBreakerServiceImpl {

	@Autowired
    private RemoteServiceConnector remoteServiceConnector;
    
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;
    
    @Autowired
    private TimeLimiter timeLimiter;
    
    public List<User> circuitBreakerNotAOP(){
        // 通过注册器获取熔断器的实例
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行开始前：", circuitBreaker);
        // 使用熔断器包装连接器的方法
        CheckedFunction0<List<User>> checkedSupplier = CircuitBreaker.
            decorateCheckedSupplier(circuitBreaker, remoteServiceConnector::process);
        // 使用Try.of().recover()调用并进行降级处理
        Try<List<User>> result = Try.of(checkedSupplier).
                    recover(CallNotPermittedException.class, throwable -> {
                        log.info("熔断器已经打开，拒绝访问被保护方法~");
                        CircuitBreakerUtil
                        .getCircuitBreakerStatus("熔断器打开中:", circuitBreaker);
                        List<User> users = new ArrayList();
                        return users;
                    })
                    .recover(throwable -> {
                        log.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
                        CircuitBreakerUtil
                        .getCircuitBreakerStatus("降级方法中:",circuitBreaker);
                        List<User> users = new ArrayList();
                        return users;
                    });
            CircuitBreakerUtil.getCircuitBreakerStatus("执行结束后：", circuitBreaker);
            return result.get();
    }
    
    public List<User> circuitBreakerTimeLimiter(){
        // 通过注册器获取熔断器的实例
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行开始前：", circuitBreaker);
        // 创建单线程的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //将被保护方法包装为能够返回Future的supplier函数
        Supplier<Future<List<User>>> futureSupplier = () -> pool.submit(remoteServiceConnector::process);
        // 先用限时器包装，再用熔断器包装
        Callable<List<User>> restrictedCall = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
        Callable<List<User>> chainedCallable = CircuitBreaker.decorateCallable(circuitBreaker, restrictedCall);
        // 使用Try.of().recover()调用并进行降级处理
        Try<List<User>> result = Try.of(chainedCallable::call)
            .recover(CallNotPermittedException.class, throwable ->{
                log.info("熔断器已经打开，拒绝访问被保护方法~");
                CircuitBreakerUtil.getCircuitBreakerStatus("熔断器打开中", circuitBreaker);
                List<User> users = new ArrayList<>();
                return users;
            })
            .recover(throwable -> {
                log.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
                CircuitBreakerUtil.getCircuitBreakerStatus("降级方法中:",circuitBreaker);
                List<User> users = new ArrayList<>();
                return users;
            });
        CircuitBreakerUtil.getCircuitBreakerStatus("执行结束后：", circuitBreaker);
        return result.get();
    }
    
}
