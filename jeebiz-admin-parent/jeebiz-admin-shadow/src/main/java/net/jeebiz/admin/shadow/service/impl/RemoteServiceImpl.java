/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.shadow.service.IRemoteService;
import net.jeebiz.boot.api.exception.BizCheckedException;
import net.jeebiz.boot.api.exception.BizRuntimeException;

@Slf4j
public class RemoteServiceImpl implements IRemoteService {
    
    private static AtomicInteger count = new AtomicInteger(0);

    public List<User> process() throws BizCheckedException {
        int num = count.getAndIncrement();
        log.info("count的值 = " + num);
        if (num % 4 == 1){
            throw new BizRuntimeException("异常A，不需要被记录");
        }
        if (num % 4 == 2 || num % 4 == 3){
            throw new BizCheckedException("异常B，需要被记录");
        }
        log.info("服务正常运行，获取用户列表");
        // 模拟数据库的正常查询
        return new ArrayList<>();
    }
}
