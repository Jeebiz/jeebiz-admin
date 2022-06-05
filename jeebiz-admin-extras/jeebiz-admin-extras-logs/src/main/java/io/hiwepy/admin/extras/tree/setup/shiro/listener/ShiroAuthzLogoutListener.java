package io.hiwepy.admin.extras.tree.setup.shiro.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spring.boot.utils.RemoteAddrUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authc.listener.LogoutListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.sequence.Sequence;

/**
 * 会话注销日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
@Slf4j
public class ShiroAuthzLogoutListener implements LogoutListener {

	@Autowired
	protected Sequence sequence;
	
	@Override
	public void beforeLogout(Subject subject, ServletRequest request, ServletResponse response) {
		
	}
	
	@Override
	public void onFailure(Subject subject, Exception ex) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		MDC.put("opt", BusinessType.LOGOUT.getKey());
		MDC.put("userId", principal.getUserid());
		MDC.put("userName", principal.getUsername());
		MDC.put("status", Constants.RT_SUCCESS);

		log.error(Constants.authzMarker, String.format("用户:%s账户注销成功.", principal.getUsername()));
		
	}

	@Override
	public void onSuccess(Subject subject, ServletRequest request, ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		MDC.put("opt", BusinessType.LOGOUT.getKey());
		MDC.put("userId", principal.getUserid());
		MDC.put("userName", principal.getUsername());
		MDC.put("status", Constants.RT_FAIL);
		
		recordRequest(WebUtils.toHttp(request));

		log.error(Constants.authzMarker, String.format("用户:%s账户注销失败.", principal.getUsername()));
		
	}
	
	protected void recordRequest(HttpServletRequest request) {
		
		MDC.put("requestId", sequence.nextId().toString()); 
		MDC.put("requestURL", request.getRequestURL().toString());
		MDC.put("requestURI", request.getRequestURI());
		MDC.put("queryString", request.getQueryString());
		MDC.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(request));
		MDC.put("remoteHost", request.getRemoteHost());
		MDC.put("remotePort", String.valueOf(request.getRemotePort()));
		MDC.put("localAddr", request.getLocalAddr());
		MDC.put("localName", request.getLocalName());
		
	}

	public HttpServletRequest getRequest() {

		//两个方法在没有使用JSF的项目中是没有区别的
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		//				                      RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
		//HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
		
		return request;
	}
	
}
