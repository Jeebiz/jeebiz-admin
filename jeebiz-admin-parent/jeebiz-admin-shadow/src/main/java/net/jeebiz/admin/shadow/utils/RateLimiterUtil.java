/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.utils;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateLimiterUtil {

    /**
     * @Description: 获取rateLimiter的状态
     */
    public static void getRateLimiterStatus(String time, RateLimiter rateLimiter){
        RateLimiter.Metrics metrics = rateLimiter.getMetrics();
        // Returns the number of availablePermissions in this duration.
        int availablePermissions =  metrics.getAvailablePermissions();
        // Returns the number of WaitingThreads
        int numberOfWaitingThreads = metrics.getNumberOfWaitingThreads();

        log.info(time  + ", metrics[ availablePermissions=" + availablePermissions +
                ", numberOfWaitingThreads=" + numberOfWaitingThreads + " ]");
    }

    /**
     * @Description: 监听rateLimiter事件
     */
    public static void addRateLimiterListener(RateLimiter rateLimiter){
        rateLimiter.getEventPublisher()
                .onSuccess(event -> log.info(event.toString()))
                .onFailure(event -> log.info(event.toString()));
    }
}