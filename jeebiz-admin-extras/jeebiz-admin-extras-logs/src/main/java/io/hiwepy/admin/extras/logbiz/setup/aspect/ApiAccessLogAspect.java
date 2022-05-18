package io.hiwepy.admin.extras.logbiz.setup.aspect;

import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.annotation.ApiModule;
import io.hiwepy.boot.api.utils.WebUtils;
import io.hiwepy.boot.extras.external.region.NestedRegionTemplate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class ApiAccessLogAspect {

	@Autowired
	private NestedRegionTemplate regionTemplate;

	@Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
	public void aspect() {
		// do nothing
	}

	@Around("aspect()")
	public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable {

		// 1、获取AOP信息
		long startTime = System.currentTimeMillis();

		Signature signature = pjd.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;

		// 2、获取方法及参数信息
		Method method = methodSignature.getMethod();

		// 5、获取 BusinessLog 注解

		ApiModule apiModule = AnnotationUtils.findAnnotation(method, ApiModule.class);
		if(Objects.isNull(apiModule)){
			apiModule = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiModule.class);
		}
		if(Objects.nonNull(apiModule)) {
			MDC.put("module", apiModule.module());
		}

		// 3、获取 Request对象，解析请求来源
		HttpServletRequest request = WebUtils.getHttpServletRequest();
		String uri = "";
		String ipAddress = "";
		//RegionEnum regionEnum = RegionEnum.UK;
		if(Objects.nonNull(request)) {
			uri = request.getRequestURI();
			ipAddress = WebUtils.getRemoteAddr(request);
			//regionEnum = regionTemplate.getRegionByIp(ipAddress);
			if(Objects.nonNull(apiModule)) {
				MDC.put("uri", uri);
				MDC.put("ipv4", ipAddress);
				//MDC.put("regionCode", regionEnum.getCode2());
				//MDC.put("regionName", regionEnum.getCname());
			}
		}

		// 4、获取 ApiOperation 注解
		ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
		if(Objects.nonNull(apiOperation) && Objects.nonNull(apiModule)) {
			MDC.put("opt", apiOperation.value()  + " : " +  apiOperation.notes());
		}

		String methodName = methodSignature.getName();
		String methodVisiter = this.getVisiter();
		if(Objects.nonNull(apiModule)) {
			MDC.put("userId", methodVisiter);
		}

		Object result = null;
		try {
			if (log.isInfoEnabled() && Objects.nonNull(apiModule)) {
				List<Object> methodArgs =  Stream.of(pjd.getArgs())
						.filter(arg -> !(arg instanceof ServletRequest && arg instanceof ServletResponse))
						.collect(Collectors.toList());
				log.info(Constants.accessMarker, "{} >> LogAspect start request URI {} IP {} method {} with args {}", methodVisiter, uri, ipAddress, methodName, methodArgs);
			}
			result = pjd.proceed();
			if (log.isInfoEnabled() && Objects.nonNull(apiModule)) {
				log.info(Constants.accessMarker, "{} >> LogAspect end request URI {} IP {} method {} time {}", methodVisiter, uri, ipAddress, methodName, System.currentTimeMillis() - startTime);
			}
		} catch (Throwable e) {
			StringJoiner joiner = new StringJoiner(",");
			if(Objects.nonNull(request)) {
				Enumeration<String> headNames = request.getHeaderNames();
				while(headNames.hasMoreElements()){
					String headName = headNames.nextElement();
					joiner.add(headName + " : " + request.getHeader(headName));
				}
			}
			log.error(Constants.accessMarker, "{} >> LogAspect fail request URI {} IP {} method {} headers {} fail {} time {}", methodVisiter, uri, ipAddress, methodName, joiner.toString(), e.getMessage(), System.currentTimeMillis() - startTime);
			throw e;
		} finally {
			MDC.clear();
		}
		return result;
	}

	public String getVisiter() {
		String userId = "anonymous";
		try {
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			userId = principal.getUserid();
		} catch (Throwable e) {
		}
		return userId;
	}

}
