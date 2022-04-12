/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.shadow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.micrometer.core.instrument.MeterRegistry;
import net.jeebiz.boot.autoconfigure.EnableJeebiz;

/**
 * 应用启动入口
 */
@EnableJeebiz
@EnableScheduling
@EnableWebMvc
//@EnableAdminServer
@MapperScan({"net.jeebiz.**.dao", "net.jeebiz.**repository"})
//@ComponentScan({"net.jeebiz.**.setup", "net.jeebiz.**.service", "net.jeebiz.**.aspect", "net.jeebiz.**.task"})
@SpringBootApplication
public class JeebizShadowApplication implements CommandLineRunner {

	@Autowired
	private RedisOperationTemplate redisOperation;

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
			Long rtLong1 = redisOperation.luaIncr("test", 5000);
			System.out.println(rtLong1);
			// 减库存
			Long rtLong2 = redisOperation.luaDecr("test", 500);
			System.out.println(rtLong2);
			// 加库存
			Long rtLong3 = redisOperation.luaHincr("test2", "coin", 3822);
			System.out.println(rtLong3);
			// 减库存
			Long rtLong4 = redisOperation.luaHdecr("test2", "coin", 451);
			System.out.println(rtLong4);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}

}
