/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.utils;

import io.github.resilience4j.bulkhead.Bulkhead;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BulkhdadUtil {

    /**
     * @Description: 获取bulkhead的状态
     */
    public static void getBulkheadStatus(String time, Bulkhead bulkhead){
        Bulkhead.Metrics metrics = bulkhead.getMetrics();
        // Returns the number of parallel executions this bulkhead can support at this point in time.
        int availableConcurrentCalls =  metrics.getAvailableConcurrentCalls();
        // Returns the configured max amount of concurrent calls
        int maxAllowedConcurrentCalls = metrics.getMaxAllowedConcurrentCalls();

        log.info(time  + ", metrics[ availableConcurrentCalls=" + availableConcurrentCalls +
                ", maxAllowedConcurrentCalls=" + maxAllowedConcurrentCalls + " ]");
    }

    /**
     * @Description: 监听bulkhead事件
     */
    public static void addBulkheadListener(Bulkhead bulkhead){
        bulkhead.getEventPublisher()
                .onCallFinished(event -> log.info(event.toString()))
                .onCallPermitted(event -> log.info(event.toString()))
                .onCallRejected(event -> log.info(event.toString()));
    }
}