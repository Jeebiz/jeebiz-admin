/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.micrometer.core.instrument.MeterRegistry;
import io.hiwepy.boot.autoconfigure.EnableExtrasConfiguration;

/**
 * 应用启动入口
 */
@EnableAdminServer
@EnableCaching(proxyTargetClass = true)
@EnableExtrasConfiguration
@EnableScheduling
//其他路径可以单独添加注解
@MapperScan({"io.hiwepy.**.dao", "io.hiwepy.**repository"})
//@ComponentScan({"io.hiwepy.**.setup", "io.hiwepy.**.service", "io.hiwepy.**.aspect", "io.hiwepy.**.task"})
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
			Long rtLong1 = redisOperationTemplate.luaIncr("test", 5000);
			System.out.println(rtLong1);
			// 减库存
			Long rtLong2 = redisOperationTemplate.luaDecr("test", 500);
			System.out.println(rtLong2);
			// 加库存
			Long rtLong3 = redisOperationTemplate.luaHincr("test2", "coin", 3822);
			System.out.println(rtLong3);
			// 减库存
			Long rtLong4 = redisOperationTemplate.luaHdecr("test2", "coin", 451);
			System.out.println(rtLong4);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}

}
