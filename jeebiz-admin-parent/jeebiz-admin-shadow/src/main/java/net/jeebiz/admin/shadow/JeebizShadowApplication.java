/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.beust.jcommander.internal.Lists;

import io.micrometer.core.instrument.MeterRegistry;
import net.jeebiz.boot.autoconfigure.EnableJeebiz;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

/**
 * 应用启动入口
 */
@EnableJeebiz
@EnableScheduling
@EnableWebMvc
//@EnableAdminServer
//其他路径可以单独添加注解
//@MapperScan({"net.jeebiz.**.dao"})
//@ComponentScan({"net.jeebiz.**.setup", "net.jeebiz.**.service", "net.jeebiz.**.aspect", "net.jeebiz.**.task"})
@SpringBootApplication
public class JeebizShadowApplication implements CommandLineRunner {

	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	
	@Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(JeebizShadowApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		try {
			
			// 加库存
			//Long rtLong1 = redisOperationTemplate.executeLuaScript(RedisLua.INCR_SCRIPT, Long.class, Lists.newArrayList("test"), 5000);
			//Long rtLong1 = redisOperationTemplate.luaIncr("test", 5000);
			//System.out.println(rtLong1);
			// 减库存
			//Long rtLong2 = redisOperationTemplate.executeLuaScript(RedisLua.DECR_SCRIPT, Long.class, Lists.newArrayList("test"), 31822);
			//Long rtLong2 = redisOperationTemplate.luaDecr("test", 31822);
			//System.out.println(rtLong2);
			// 加库存
			Long rtLong3 = redisOperationTemplate.luaHincr("test2", "coin", 3822);
			System.out.println(rtLong3);
			// 减库存
			//Long rtLong4 = redisOperationTemplate.executeLuaScript(RedisLua.HDECR_SCRIPT, Long.class, Lists.newArrayList("test2", "coin"), 343);
			Long rtLong4 = redisOperationTemplate.luaHdecr("test2", "coin", 343);
			System.out.println(rtLong4);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}
	
}
