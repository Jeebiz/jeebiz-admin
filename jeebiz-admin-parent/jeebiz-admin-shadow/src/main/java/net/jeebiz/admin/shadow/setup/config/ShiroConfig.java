package net.jeebiz.admin.shadow.setup.config;

import java.util.List;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.biz.authz.principal.ShiroPrincipalRepository;
import org.apache.shiro.biz.realm.AuthorizingRealmListener;
import org.apache.shiro.biz.web.filter.authc.AuthenticatingFailureSessionCounter;
import org.apache.shiro.biz.web.filter.authc.TrustableFormAuthenticatingFilter;
import org.apache.shiro.biz.web.filter.authc.captcha.CaptchaResolver;
import org.apache.shiro.biz.web.filter.authc.listener.LoginListener;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.boot.ShiroBizProperties;
import org.apache.shiro.spring.boot.captcha.ShiroKaptchaProperties;
import org.apache.shiro.spring.boot.captcha.ShiroKaptchaSessionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.jeebiz.admin.shadow.setup.shiro.LoginAuthorizingRealm;

@Configuration
@AutoConfigureBefore( name = {
	"com.google.code.kaptcha.spring.boot.KaptchaAutoConfiguration",
	"org.apache.shiro.spring.boot.ShiroJwtWebAutoConfiguration"
})
@ConditionalOnProperty(prefix = ShiroBizProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ ShiroBizProperties.class, ShiroKaptchaProperties.class })
public class ShiroConfig {

    @Autowired(required = false)
    protected CacheManager cacheManager;
    @Autowired(required = false)
    protected RolePermissionResolver rolePermissionResolver;
    @Autowired(required = false)
    protected PermissionResolver permissionResolver;
    
	@Bean  
	public ShiroDialect shiroDialect() {  
	    return new ShiroDialect();  
	}
	
	@Bean
	public Realm defRealm(ShiroPrincipalRepository defRepository,
			@Autowired(required = false) List<AuthorizingRealmListener> realmsListeners,
			CredentialsMatcher credentialsMatcher,
			ShiroBizProperties properties) {

		LoginAuthorizingRealm authzRealm = new LoginAuthorizingRealm();
		
		
		// 认证缓存配置:无状态情况不缓存认证信息
		authzRealm.setAuthenticationCachingEnabled(properties.isAuthenticationCachingEnabled());
		authzRealm.setAuthenticationCacheName(properties.getAuthenticationCacheName());
		// 授权缓存配置:无状态情况不缓存认证信息
		authzRealm.setAuthorizationCachingEnabled(properties.isAuthorizationCachingEnabled());
		authzRealm.setAuthorizationCacheName(properties.getAuthorizationCacheName());
		
		authzRealm.setCacheManager(cacheManager);
		//缓存相关的配置：采用提供的默认配置即可
		authzRealm.setCachingEnabled(properties.isCachingEnabled());
		// 凭证匹配器：该对象主要做密码校验
		authzRealm.setCredentialsMatcher(credentialsMatcher);
		
		// Realm 执行监听：实现该接口可监听认证失败和成功的状态，从而做业务系统自己的事情，比如记录日志
		authzRealm.setRealmsListeners(realmsListeners);
		// 认证账号信息提供实现：认证信息、角色信息、权限信息；业务系统需要自己实现该接口
		authzRealm.setRepository(defRepository);
		
		authzRealm.setPermissionResolver(permissionResolver);
		authzRealm.setRolePermissionResolver(rolePermissionResolver);
		
		return authzRealm;
	}
 
	
 

	@Bean
	public ShiroKaptchaSessionResolver captchaResolver(ShiroKaptchaProperties properties) {
		
		ShiroKaptchaSessionResolver kaptchaResolver = new ShiroKaptchaSessionResolver(); 
		// 初始化参数
		kaptchaResolver.init(properties.getStoreKey(), 
				properties.getDateStoreKey(), properties.getTimeout());
		
		return kaptchaResolver;
	}
	
	/**
	 * 默认的登录验证过滤器
	 */
	@Bean("authc")
	public FilterRegistrationBean<TrustableFormAuthenticatingFilter> authenticationFilter(
			@Autowired(required = false) List<LoginListener> loginListeners,
			CaptchaResolver captchaResolver,
			ShiroBizProperties properties,
			ShiroKaptchaProperties kaptchaProperties) {
		
		TrustableFormAuthenticatingFilter authcFilter = new TrustableFormAuthenticatingFilter();
		
		// 登陆失败重试次数，超出限制需要输入验证码
		authcFilter.setRetryTimesWhenAccessDenied(kaptchaProperties.getRetryTimesWhenAccessDenied());
		// 是否验证验证码
		authcFilter.setCaptchaEnabled(properties.isEnabled());
		// 验证码解析器
		authcFilter.setCaptchaResolver(captchaResolver);
		// 登录监听：实现该接口可监听账号登录失败和成功的状态，从而做业务系统自己的事情，比如记录日志
		authcFilter.setLoginListeners(loginListeners);
		
		//authcFilter.setFailureCounter(new AuthenticatingFailureRequestCounter());
		authcFilter.setFailureCounter(new AuthenticatingFailureSessionCounter());
		//authcFilter.setSessionStateless(properties.isSessionStateless());
		
		/*
		 * * 自定义Filter通过@Bean注解后，被Spring Boot自动注册到了容器的Filter
		 * chain中，这样导致的结果是，所有URL都会被自定义Filter过滤， 而不是Shiro中配置的一部分URL。下面方式可以解决该问题
		 */

		FilterRegistrationBean<TrustableFormAuthenticatingFilter> registration = new FilterRegistrationBean<TrustableFormAuthenticatingFilter>(
				authcFilter);
		registration.setEnabled(false);
		return registration;
	}
	
}
