/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.jpush.spring.boot.EnableJPush;
import io.micrometer.core.instrument.MeterRegistry;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.autoconfigure.EnableJeebiz;

/**
 * 应用启动入口
 */
@EnableDubbo
@EnableJeebiz
@EnableJPush
@EnableScheduling
@EnableWebMvc
//其他路径可以单独添加扫码主键
//@MapperScan({"net.jeebiz.**.dao"})
//@ComponentScan({"net.jeebiz.**.setup", "net.jeebiz.**.service", "net.jeebiz.**.aspect", "net.jeebiz.**.task"})
@SpringBootApplication
public class JeebizShadowApplication implements CommandLineRunner {

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
		System.err.println("Spring Boot Application（Jeebiz-Admin-Shadow） Started !");
	}
	
}
