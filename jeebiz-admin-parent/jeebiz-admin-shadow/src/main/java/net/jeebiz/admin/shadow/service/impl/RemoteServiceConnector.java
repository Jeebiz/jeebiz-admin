/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.shadow.utils.CircuitBreakerUtil;

@Slf4j
public class RemoteServiceConnector{
    
	@Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;
	
    @CircuitBreaker(name = "backendA", fallbackMethod = "fallBack")
    public List<User> process() throws TimeoutException, InterruptedException {
        List<User> users;
        users = remoteServic.process();
        return users;
    }
    
    private List<User> fallBack(Throwable throwable){
        log.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
        CircuitBreakerUtil.getCircuitBreakerStatus("降级方法中:", circuitBreakerRegistry.circuitBreaker("backendA"));
        List<User> users = new ArrayList();
        return users;
    }
    
    private List<User> fallBack(CallNotPermittedException e){
        log.info("熔断器已经打开，拒绝访问被保护方法~");
        CircuitBreakerUtil.getCircuitBreakerStatus("熔断器打开中:", circuitBreakerRegistry.circuitBreaker("backendA"));
        List<User> users = new ArrayList();
        return users;
    }
    
}