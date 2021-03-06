/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow;

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
import net.jeebiz.boot.extras.redis.setup.RedisLua;
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
	
	 public static final String LOCK_STOCK_LUA2 =  
		      "if (redis.call('exists', KEYS[1]) == 1) then"
		    + "    local stock = tonumber(redis.call('get', KEYS[1]));"
		    + "    local num = tonumber(ARGV[1]);"
		    + "    if (num <= 0) then"
		    + "        return -4;"
		    + "    end;"
		    + "    if (stock <= 0) then"
		    + "        return -1;"
		    + "    end;"
		    + "    if (stock >= num) then"
		    + "        return redis.call('incrBy', KEYS[1], 0 - num);"
		    + "    end;"
		    + "    return -2;"
		    + "end;"
		    + "return -3;";
	 
	@Override
	public void run(String... args) throws Exception {
		
		try {
			//redisOperationTemplate.unlock("xax", "xas");
			
			//Long rtLong = redisOperationTemplate.luaIncr("test", -1200L);
			//System.out.println(rtLong);
			Long rtLong = redisOperationTemplate.executeLuaScript(LOCK_STOCK_LUA2, Long.class, Lists.newArrayList("test"), 31822);
			
			System.out.println(rtLong);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}
	
}
