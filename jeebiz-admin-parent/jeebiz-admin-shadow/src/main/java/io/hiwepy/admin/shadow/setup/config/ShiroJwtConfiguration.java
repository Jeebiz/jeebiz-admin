/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.setup.config;

import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.hiwepy.jwt.time.JwtTimeProvider;
import com.github.hiwepy.jwt.token.SignedWithSecretKeyJWTRepository;
import com.github.hiwepy.jwt.token.SignedWithSecretResolverJWTRepository;

import io.jsonwebtoken.JwtClock;
import io.jsonwebtoken.SigningKeyResolver;
import io.hiwepy.admin.shadow.setup.shiro.jwt.DefaultJwtPayloadRepository;
import io.hiwepy.admin.shadow.setup.shiro.jwt.JJwtAuthenticationFailureHandler;
import io.hiwepy.admin.shadow.setup.shiro.jwt.JwtSigningKeyRedisResolver;
import io.hiwepy.admin.shadow.setup.shiro.jwt.JwtTimeRedisProvider;

@Configuration
@AutoConfigureBefore(name = {
	"com.google.code.kaptcha.spring.boot.KaptchaAutoConfiguration",
	"org.apache.shiro.spring.boot.ShiroJwtWebAutoConfiguration"
})
public class ShiroJwtConfiguration {

	@Bean
	protected JJwtAuthenticationFailureHandler jjwtAuthenticationFailureHandler() {
		return new JJwtAuthenticationFailureHandler();
	}

    @Bean
	@ConditionalOnMissingBean
    public SigningKeyResolver signingKeyResolver(StringRedisTemplate stringRedisTemplate) {
    	return new JwtSigningKeyRedisResolver(stringRedisTemplate);
    }

	@Bean
	@ConditionalOnMissingBean
	public SignedWithSecretResolverJWTRepository secretResolverJWTRepository(SigningKeyResolver signingKeyResolver) {
		return new SignedWithSecretResolverJWTRepository(signingKeyResolver);
	}

	/**
	@Bean
	@ConditionalOnMissingBean
	public JwtTimeProvider jwtTimeProvider(ICommonMapper commonMapper) {
		return new JwtTimeDatabaseProvider(commonMapper);
	}**/

	@Bean
	@ConditionalOnMissingBean
	public JwtTimeProvider jwtTimeProvider(RedisOperationTemplate redisOperationTemplate) {
		return new JwtTimeRedisProvider(redisOperationTemplate);
	}

	@Bean
   	public JwtClock jwtClock(JwtTimeProvider timeProvider) {
		JwtClock clock = new JwtClock();
		clock.setTimeProvider(timeProvider);
   		return clock;
   	}

    @Bean
    @ConditionalOnMissingBean
   	public SignedWithSecretKeyJWTRepository secretKeyJWTRepository(JwtClock jwtClock) {
    	SignedWithSecretKeyJWTRepository jWTRepository = new SignedWithSecretKeyJWTRepository();
    	jWTRepository.setClock(jwtClock);
   		return jWTRepository;
   	}


	@Bean
	@ConditionalOnMissingBean
   	public JwtPayloadRepository jwtPayloadRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository, RedisOperationTemplate redisOperationTemplate) {
		return new DefaultJwtPayloadRepository(secretKeyJWTRepository, redisOperationTemplate);
	}


	/*
	 * @Bean protected SubjectFactory subjectFactory(ShiroBizProperties properties)
	 * { return new JwtSubjectFactory(new DefaultSessionStorageEvaluator(),
	 * properties.isStateless()); }
	 */


}
