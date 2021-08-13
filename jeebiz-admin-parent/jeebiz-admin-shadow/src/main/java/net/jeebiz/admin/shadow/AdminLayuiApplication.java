/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.jeebiz.boot.autoconfigure.EnableJeebiz;

/**
 * 应用启动入口
 */
@EnableCaching(proxyTargetClass = true)
@EnableScheduling
@EnableJeebiz
@EnableTransactionManagement
@SpringBootApplication
public class AdminLayuiApplication implements CommandLineRunner {

   /* @Bean("servJwtRepository")
	public SignedWithSecretResolverJWTRepository servJwtRepository(SigningKeyResolver signingKeyResolver) {
		return new SignedWithSecretResolverJWTRepository(signingKeyResolver);
	}
    
    @Bean("backendJwtRepository")
   	public SignedWithSecretBase64JWTRepository backendJwtRepository() {
   		return new SignedWithSecretBase64JWTRepository();
   	}*/

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AdminLayuiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Spring Boot Application（Jeebiz-Admin-Layui（iframe）） Started !");
	}
	
}
