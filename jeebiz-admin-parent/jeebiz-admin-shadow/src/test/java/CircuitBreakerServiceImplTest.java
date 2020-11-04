import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.shadow.service.impl.CircuitBreakerServiceImpl;

/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
@Slf4j
public class CircuitBreakerServiceImplTest{
    
    @Autowired
    private CircuitBreakerServiceImpl circuitService;
    
    @Test
    public void circuitBreakerThreadTest() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i=0; i<15; i++){
            pool.submit(
                // circuitService::circuitBreakerAOP
                circuitService::circuitBreakerNotAOP);
        }
        pool.shutdown();

        while (!pool.isTerminated());

        Thread.sleep(10000);
        log.info("熔断器状态已转为半开");
        pool = Executors.newCachedThreadPool();
        for (int i=0; i<15; i++){
            pool.submit(
                // circuitService::circuitBreakerAOP
                circuitService::circuitBreakerNotAOP);
        }
        pool.shutdown();

        while (!pool.isTerminated());
        for (int i=0; i<10; i++){
            
        }
    }
    
    @Test
    public void circuitBreakerTimeLimiterTest() {
        for (int i=0; i<10; i++){
            circuitService.circuitBreakerTimeLimiter();
        }
    }
    
}
