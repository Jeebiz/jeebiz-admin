package io.hiwepy.admin.extras.redis.aspect;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.annotation.ApiIdempotent;
import io.hiwepy.boot.api.annotation.ApiIdempotentType;
import io.hiwepy.boot.api.exception.IdempotentException;
import io.hiwepy.boot.api.subject.AuthPrincipal;
import io.hiwepy.boot.api.subject.SubjectUtils;
import io.hiwepy.boot.api.utils.IdempotentUtils;

/**
 * 1、基于Redis SETNX 命令实现的分布式锁（key不过期）
 * 2、参考：
 * https://blog.csdn.net/hanchao5272/article/details/92073405
 */
@Slf4j
public class ApiIdempotentSetnxAspect {

	@Autowired
	private RedisOperationTemplate redisOperationTemplate;

	@Pointcut("@annotation(io.hiwepy.boot.api.annotation.ApiIdempotent)")
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
			String idempotentKey = IdempotentUtils.getIdempotentKey(joinPoint, idempotent);
			// 4.2、根据 key前缀 + @ApiIdempotent.value() + 方法签名 + 参数 构建缓存键值；确保幂等处理的操作对象是：同样的 @ApiIdempotent.value() + 方法签名 + 参数
			String uid = SubjectUtils.isAuthenticated() ? SubjectUtils.getPrincipal(AuthPrincipal.class).getUid() : "guest";
			String lockKey = BizRedisKey.IDEMPOTENT_ARGS_KEY.getKey(new StringJoiner("_").add(uid).add(idempotentKey).toString());
			try {
				// 4.3、通过setnx确保只有一个接口能够正常访问
				if (redisOperationTemplate.tryLock(lockKey, idempotent.expireMillis())) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				if(idempotent.unlock()) {
					redisOperationTemplate.unlock(lockKey);
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
			String lockKey = BizRedisKey.IDEMPOTENT_TOKEN_KEY.getKey(token);
			try {
				// 5.4、通过setnx确保只有一个接口能够正常访问
				if (redisOperationTemplate.tryLock(lockKey, idempotent.expireMillis())) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				if(idempotent.unlock()) {
					redisOperationTemplate.unlock(lockKey);
				}
			}
		}
		return joinPoint.proceed();
	}

}
