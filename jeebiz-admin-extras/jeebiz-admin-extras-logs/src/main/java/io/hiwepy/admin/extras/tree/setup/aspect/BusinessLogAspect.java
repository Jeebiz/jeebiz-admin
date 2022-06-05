/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.tree.setup.aspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;

@Aspect
@Component
@Slf4j
public class BusinessLogAspect {

	/**
	 * 代理对象正常调用返回后advice
	 * @param jp
	 * @throws IOException 
	 * @throws TemplateException 
	 */
    @AfterReturning(pointcut = "@annotation(io.hiwepy.boot.api.annotation.BusinessLog) and @annotation(bizLog)")
	public void afterReturing(JoinPoint jp, BusinessLog bizLog) throws IOException{
		
    	Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		
		String[] argNames = methodSignature.getParameterNames();
		Object[] args = jp.getArgs();
		Map<String,Object> data = new HashMap<String,Object>();
		
		for (int i = 0 , j = argNames.length ; i < j ; i++){
			data.put(argNames[i], args[i]);
		}

		MDC.put("module", bizLog.module());
		MDC.put("biz", bizLog.business());
		MDC.put("opt", bizLog.opt().getKey());
		
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		MDC.put("userId", principal.getUserid());
		
		// 记录请求日志
		log.info(Constants.bizMarker, "业务操作成功.");
		
	}
    
    @AfterThrowing(throwing="ex", pointcut = "@annotation(io.hiwepy.boot.api.annotation.BusinessLog) and @annotation(bizLog)")
	public void afterThrowing(Throwable ex, BusinessLog bizLog) throws IOException{

		MDC.put("module", bizLog.module());
		MDC.put("biz", bizLog.business());
		MDC.put("opt", bizLog.opt().getKey());
		// 登录报错,这里还拿不到ShiroPrincipal对象
		if(bizLog.opt().equals(BusinessType.LOGIN)) {
			// ServletRequest request = SubjectUtils.getWebSubject().getServletRequest();
			// MDC.put("userId", request.getParameter("account"));
		} else {
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			MDC.put("userId", principal.getUserid());
		}

		MDC.put("clazz", ex.getClass().getName());
		
		// 记录请求日志
		log.error("业务操作异常：" + ex.getMessage(), ex);

	}

}


