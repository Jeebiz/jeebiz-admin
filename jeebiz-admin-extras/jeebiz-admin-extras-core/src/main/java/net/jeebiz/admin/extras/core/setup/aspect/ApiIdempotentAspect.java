package net.jeebiz.admin.extras.core.setup.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.annotation.ApiIdempotent;
import net.jeebiz.boot.api.annotation.ApiIdempotentType;
import net.jeebiz.boot.api.exception.IdempotentException;

// https://blog.csdn.net/hanchao5272/article/details/92073405
@Slf4j
@Component
@Aspect
public class ApiIdempotentAspect {

	private final ExpressionParser expressionParser = new SpelExpressionParser();

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * redis缓存key的模板
	 */
	private static final String KEY_TEMPLATE = "idempotent_%s";

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
		if(Objects.isNull(idempotent)) {
			idempotent = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiIdempotent.class);
		}
		// 3、不进行幂等
		if(Objects.isNull(idempotent)) {
			return joinPoint.proceed();
		}
		// 4、通过参数值构造唯一标记实现幂等
		String idempotentKey = idempotent.value();
		if(ApiIdempotentType.ARGS.equals(idempotent.type())) {
			String[] parameterNames = methodSignature.getParameterNames(); 
			Object[] parameters = joinPoint.getArgs();
			// 4.1、在指定幂等值的情况下，判断是否需要进行 Spring Expression Language(SpEL) 表达式解析，如果需要，则进行SpEL解析
			if(StringUtils.hasText(idempotentKey) && idempotent.spel()) {
				// 解析表达式需要的上下文，解析时有一个默认的上下文
		        EvaluationContext context = new StandardEvaluationContext();
		        for (int i = 0; i < parameterNames.length; i++) {
			        context.setVariable(parameterNames[i], parameters[i]);
				}
		        idempotentKey = String.valueOf(expressionParser.parseExpression(idempotentKey).getValue(context));
			}
			// 4.2、没指定幂等值的情况下，尝试获取 @RequestMapping、@PostMapping、@GetMapping、@PutMapping、@DeleteMapping、@PatchMapping 的 value 值作为幂等值的一部分
			if(!StringUtils.hasText(idempotentKey)) {
				PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
				if(!Objects.isNull(postMapping)) {
					idempotentKey = postMapping.value()[0];
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
				if(!Objects.isNull(getMapping)) {
					idempotentKey = getMapping.value()[0];
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
				if(!Objects.isNull(requestMapping)) {
					idempotentKey = requestMapping.value()[0];
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
				if(!Objects.isNull(deleteMapping)) {
					idempotentKey = deleteMapping.value()[0];
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				PatchMapping patchMapping = AnnotationUtils.findAnnotation(method, PatchMapping.class);
				if(!Objects.isNull(patchMapping)) {
					idempotentKey = patchMapping.value()[0];
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
				if(!Objects.isNull(putMapping)) {
					idempotentKey = putMapping.value()[0];
				}
			}
			// 4.3、根据 key前缀 + @ApiIdempotent.value() + 方法签名 + 参数 构建缓存键值；确保幂等处理的操作对象是：同样的 @ApiIdempotent.value() + 方法签名 + 参数
			String lockKey = String.format(KEY_TEMPLATE, idempotentKey + "_" + this.generate(method, joinPoint.getArgs()));
			// 4.4、通过setnx确保只有一个接口能够正常访问
			if (this.tryLock(lockKey, idempotent.expireMillis())) {
				return joinPoint.proceed();
			} else {
				log.debug("Idempotent hits, key=" + lockKey);
				throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
			}
		}
		
		// 5、通过请求参数中的token值实现幂等
		else if(ApiIdempotentType.TOKEN.equals(idempotent.type())) {
			// 5.1、获取Request对象
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes)ra;
			HttpServletRequest request = sra.getRequest();
			// 5.2、从请求中获取token值
			idempotentKey = request.getHeader(idempotent.value());
			if(!StringUtils.hasText(idempotentKey)) {
				idempotentKey = request.getParameter(idempotent.value());
			}
			// 5.3、根据 key前缀 + token
			String lockKey = String.format(KEY_TEMPLATE, idempotentKey);
			// 5.4、通过setnx确保只有一个接口能够正常访问
			if (this.tryLock(lockKey, idempotent.expireMillis())) {
				return joinPoint.proceed();
			} else {
				log.debug("Idempotent hits, key=" + lockKey);
				throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
			}
		} 
		
		return joinPoint.proceed();
	}
	
	public boolean tryLock(String lockKey, long expireMillis) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
        	byte[] serLockKey = redisTemplate.getStringSerializer().serialize(lockKey);
            // 1、获取时间毫秒值
            long expireAt = System.currentTimeMillis() + expireMillis + 1;
            // 2、获取锁
            Boolean acquire = connection.setNX(serLockKey, String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] bytes = connection.get(serLockKey);
                // 3、非空判断
                if (Objects.nonNull(bytes) && bytes.length > 0) {
                    long expireTime = Long.parseLong(new String(bytes));
                    // 4、如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 5、重新加锁，防止死锁
                        byte[] set = connection.getSet(serLockKey, String.valueOf(System.currentTimeMillis() + expireMillis + 1).getBytes());
                        return Long.parseLong(new String(set)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

	/**
	 * 根据{方法名 + 参数列表}和md5转换生成key
	 * @throws JsonProcessingException 
	 */
	public String generate(Method method, Object... args) throws JsonProcessingException {
		StringBuilder sb = new StringBuilder(method.toString());
		for (Object arg : args) {
			sb.append(objectMapper.writeValueAsString(arg));
		}
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}

}
