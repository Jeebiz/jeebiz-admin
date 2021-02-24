package net.jeebiz.admin.extras.logbiz.setup.shiro.listener;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.RemoteAddrUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authc.listener.LogoutListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.Constants;

/**
 * 会话注销日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class ShiroAuthzLogoutListener implements LogoutListener {

	protected static final String STATUS_SUCCESS = "success";
	protected static final String STATUS_FAIL = "fail";
	
	@Override
	public void beforeLogout(Subject subject, ServletRequest request, ServletResponse response) {
		
	}
	
	@Override
	public void onFailure(Subject subject, Exception ex) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		ThreadContext.put("opt", BusinessType.LOGOUT.getKey());
		ThreadContext.put("userId", principal.getUserid());
		ThreadContext.put("userName", principal.getUsername());
		ThreadContext.put("status", STATUS_SUCCESS);
		LoggerFactory.getLogger("Authz-Log").error(Constants.authzMarker, String.format("用户:%s账户注销成功.", principal.getUsername()));
		
	}

	@Override
	public void onSuccess(Subject subject, ServletRequest request, ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		ThreadContext.put("opt", BusinessType.LOGOUT.getKey());
		ThreadContext.put("userId", principal.getUserid());
		ThreadContext.put("userName", principal.getUsername());
		ThreadContext.put("status", STATUS_FAIL);
		
		recordRequest(WebUtils.toHttp(request));
		
		LoggerFactory.getLogger("Authz-Log").error(Constants.authzMarker, String.format("用户:%s账户注销失败.", principal.getUsername()));
		
	}
	
	protected void recordRequest(HttpServletRequest request) {
		
		ThreadContext.put("uuid", UUID.randomUUID().toString()); // Add the fishtag;
		ThreadContext.put("requestURL", request.getRequestURL().toString());
		ThreadContext.put("requestURI", request.getRequestURI());
		ThreadContext.put("queryString", request.getQueryString());
		ThreadContext.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(request));
		ThreadContext.put("remoteHost", request.getRemoteHost());
		ThreadContext.put("remotePort", String.valueOf(request.getRemotePort()));
		ThreadContext.put("localAddr", request.getLocalAddr());
		ThreadContext.put("localName", request.getLocalName());
		
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
