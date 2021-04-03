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

import io.micrometer.core.instrument.MeterRegistry;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.autoconfigure.EnableJeebiz;

/**
 * 应用启动入口
 */
@EnableJeebiz
@EnableScheduling
@EnableWebMvc
//其他路径可以单独添加扫码主键
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
	
    @Bean
	public Sequence sequence() {
		return new Sequence(0L);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(JeebizShadowApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		try {
			//redisOperationTemplate.unlock("xax", "xas");
			Long rtLong = redisOperationTemplate.luaIncr("test", 1111L);
			System.out.println(rtLong);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}
	
}
