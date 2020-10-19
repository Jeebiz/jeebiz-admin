/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.config;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.biz.authc.AuthenticationFailureHandler;
import org.apache.shiro.biz.authc.AuthenticationSuccessHandler;
import org.apache.shiro.biz.authz.principal.ShiroPrincipalRepository;
import org.apache.shiro.biz.realm.AuthorizingRealmListener;
import org.apache.shiro.biz.web.filter.authc.AuthenticatingFailureRequestCounter;
import org.apache.shiro.biz.web.filter.authc.captcha.CaptchaResolver;
import org.apache.shiro.biz.web.filter.authc.listener.LoginListener;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.boot.ShiroBizProperties;
import org.apache.shiro.spring.boot.ShiroJwtProperties;
import org.apache.shiro.spring.boot.ShiroWeiXinProperties;
import org.apache.shiro.spring.boot.ShiroWeiXinWebAutoConfiguration;
import org.apache.shiro.spring.boot.captcha.ShiroKaptchaProperties;
import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.apache.shiro.spring.boot.weixin.authc.WxMaAuthenticatingFilter;
import org.apache.shiro.spring.boot.weixin.realm.WxMaAuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;

@Configuration
@AutoConfigureAfter({ ShiroJwtConfiguration.class})
@AutoConfigureBefore({ ShiroWeiXinWebAutoConfiguration.class})
@ConditionalOnProperty(prefix = ShiroWeiXinProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ ShiroBizProperties.class, ShiroJwtProperties.class, ShiroKaptchaProperties.class })
public class ShiroWeixinConfiguration {

	@Autowired
	private CacheManager shiroCacheManager;
    @Autowired(required = false)
    protected RolePermissionResolver rolePermissionResolver;
    @Autowired(required = false)
    protected PermissionResolver permissionResolver;
 
	@Bean
	public Realm wxMaRealm(@Qualifier("defRepository") ShiroPrincipalRepository defRepository,
			List<AuthorizingRealmListener> realmsListeners, 
			WxMaService wxMaService,
			ShiroBizProperties properties) {

		WxMaAuthorizingRealm wxMaRealm = new WxMaAuthorizingRealm(wxMaService) {
			
			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
					throws AuthenticationException {
				// 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
				ThreadContext.put("protocol", "Weixin-Ma");
				// 负责此次认证的realm名称
				ThreadContext.put("realm", "WxMaAuthorizingRealm");
				return super.doGetAuthenticationInfo(token);
			}

			@Override
			protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
				// 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
				ThreadContext.put("protocol", "Weixin-Ma");
				// 负责此次认证的realm名称
				ThreadContext.put("realm", "WxMaAuthorizingRealm");
				return super.doGetAuthorizationInfo(principals);
			}

		};
		// 认证账号信息提供实现：认证信息、角色信息、权限信息；业务系统需要自己实现该接口
		wxMaRealm.setRepository(defRepository);
		// 凭证匹配器：该对象主要做密码校验
		wxMaRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
		// Realm 执行监听：实现该接口可监听认证失败和成功的状态，从而做业务系统自己的事情，比如记录日志
		wxMaRealm.setRealmsListeners(realmsListeners);
		wxMaRealm.setPermissionResolver(permissionResolver);
		wxMaRealm.setRolePermissionResolver(rolePermissionResolver);
		// 缓存相关的配置：采用提供的默认配置即可
		wxMaRealm.setCachingEnabled(properties.isCachingEnabled());
		wxMaRealm.setCacheManager(shiroCacheManager);
		// 缓存相关的配置：采用提供的默认配置即可
		wxMaRealm.setCachingEnabled(properties.isCachingEnabled());
		// 认证缓存配置:无状态情况不缓存认证信息
		wxMaRealm.setAuthenticationCachingEnabled(properties.isAuthenticationCachingEnabled());
		wxMaRealm.setAuthenticationCacheName(properties.getAuthenticationCacheName());
		// 授权缓存配置:无状态情况不缓存认证信息
		wxMaRealm.setAuthorizationCachingEnabled(properties.isAuthorizationCachingEnabled());
		wxMaRealm.setAuthorizationCacheName(properties.getAuthorizationCacheName());

		return wxMaRealm;
	}
	
	/**
	 * 微信登录验证过滤器
	 */
	@Bean("wxMa")
	public FilterRegistrationBean<WxMaAuthenticatingFilter> authenticationFilter(
			JwtPayloadRepository jwtPayloadRepository,
			ObjectProvider<LoginListener> loginListenerProvider,
			@Autowired(required = false) List<AuthenticationSuccessHandler> successHandlers,
			@Autowired(required = false) List<AuthenticationFailureHandler> failureHandlers,
			CaptchaResolver captchaResolver, 
			ShiroBizProperties properties, 
			ShiroKaptchaProperties kaptchaProperties,
			ShiroJwtProperties jwtProperties) {

		WxMaAuthenticatingFilter authcFilter = new WxMaAuthenticatingFilter() {
			/*
			 * 二期 protected String getUsername(ServletRequest request) { return
			 * WebUtils.getCleanParam(request, getUsernameParam()); }
			 * 
			 * protected String getPassword(ServletRequest request) { return
			 * WebUtils.getCleanParam(request, getPasswordParam()); }
			 */
		};

		// 登陆失败重试次数，超出限制需要输入验证码
		authcFilter.setRetryTimesWhenAccessDenied(kaptchaProperties.getRetryTimesWhenAccessDenied());
		// 是否验证验证码
		authcFilter.setCaptchaEnabled(properties.isEnabled());
		// 验证码解析器
		authcFilter.setCaptchaResolver(captchaResolver);

		// 监听器
		authcFilter.setLoginListeners(loginListenerProvider.stream().collect(Collectors.toList()));
		authcFilter.setFailureHandlers(failureHandlers);
		authcFilter.setSuccessHandlers(successHandlers);

		authcFilter.setFailureCounter(new AuthenticatingFailureRequestCounter());
		// authcFilter.setFailureCounter(new AuthenticatingFailureSessionCounter());
		authcFilter.setSessionStateless(properties.isSessionStateless());

		/*
		 * * 自定义Filter通过@Bean注解后，被Spring Boot自动注册到了容器的Filter
		 * chain中，这样导致的结果是，所有URL都会被自定义Filter过滤， 而不是Shiro中配置的一部分URL。下面方式可以解决该问题
		 */

		FilterRegistrationBean<WxMaAuthenticatingFilter> registration = new FilterRegistrationBean<WxMaAuthenticatingFilter>(
				authcFilter);
		registration.setEnabled(false);
		return registration;
	}

}