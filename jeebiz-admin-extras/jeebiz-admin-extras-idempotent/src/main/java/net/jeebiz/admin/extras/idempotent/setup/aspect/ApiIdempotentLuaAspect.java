package net.jeebiz.admin.extras.idempotent.setup.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.annotation.ApiIdempotent;
import net.jeebiz.boot.api.annotation.ApiIdempotentType;
import net.jeebiz.boot.api.exception.IdempotentException;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

/**
 * 1、基于Lua脚本实现分布式锁的方法
 * 2、参考：
 * https://blog.csdn.net/qq_24598601/article/details/105876432
 */
@Slf4j
@Component
@Aspect
public class ApiIdempotentLuaAspect extends AbstractIdempotentAspect {

	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private Sequence sequence;

	@Pointcut("@annotation(net.jeebiz.boot.api.annotation.ApiIdempotent)")
	public void aspect() {
		// do nothing
	}

	@Around("aspect()")
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		// 1、获取方法
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		// 2、获取幂等注解
		ApiIdempotent idempotent = AnnotationUtils.findAnnotation(method, ApiIdempotent.class);
		if (Objects.isNull(idempotent)) {
			idempotent = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiIdempotent.class);
		}
		// 3、不进行幂等
		if (Objects.isNull(idempotent)) {
			return joinPoint.proceed();
		}
		// 4、通过参数值构造唯一标记实现幂等
		if (ApiIdempotentType.ARGS.equals(idempotent.type())) {
			// 4.1、解析幂等唯一key
			String idempotentKey = this.getIdempotentKey(joinPoint, idempotent);
			// 4.2、根据 key前缀 + @ApiIdempotent.value() + 方法签名 + 参数 构建缓存键值；确保幂等处理的操作对象是：同样的 @ApiIdempotent.value() + 方法签名 + 参数
			String uid = SubjectUtils.isAuthenticated() ? SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid() : "guest";
			String lockKey = String.format(KEY_ARGS_TEMPlatE, uid, idempotentKey);
			String lockValue = sequence.nextId().toString();
			try {
				// 4.3、通过setnx确保只有一个接口能够正常访问
				if (redisOperationTemplate.tryLock(lockKey, lockValue, idempotent.expireMillis(), idempotent.retryTimes(), idempotent.retryInterval())) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				if(idempotent.unlock()) {
					redisOperationTemplate.unlock(lockKey, lockValue);
				}
			}
		}

		// 5、通过请求参数中的token值实现幂等
		else if (ApiIdempotentType.TOKEN.equals(idempotent.type())) {
			// 5.1、获取Request对象
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			// 5.2、从请求中获取token值
			String token = request.getHeader(idempotent.value());
			if (!StringUtils.hasText(token)) {
				token = request.getParameter(idempotent.value());
			}
			// 5.3、根据 key前缀 + token
			String lockKey = String.format(KEY_TOKEN_TEMPlatE, token);
			String lockValue = sequence.nextId().toString();
			try {
				// 5.4、通过setnx确保只有一个接口能够正常访问
				if (redisOperationTemplate.tryLock(lockKey, lockValue, idempotent.expireMillis(), idempotent.retryTimes(), idempotent.retryInterval())) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				if(idempotent.unlock()) {
					redisOperationTemplate.unlock(lockKey, lockValue);
				}
			}
		}
		return joinPoint.proceed();
	}

}
