package net.jeebiz.admin.extras.core.setup.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.core.setup.redis.RedissonOperationTemplate;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.annotation.ApiIdempotent;
import net.jeebiz.boot.api.annotation.ApiIdempotentType;
import net.jeebiz.boot.api.exception.IdempotentException;
import net.jeebiz.boot.api.sequence.Sequence;
import springfox.documentation.annotations.ApiIgnore;

// https://blog.csdn.net/hanchao5272/article/details/92073405
@Slf4j
@Component
@Aspect
public class ApiIdempotentAspect {

	private final ExpressionParser expressionParser = new SpelExpressionParser();

	@Autowired
	private RedissonOperationTemplate redissonOperationTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Sequence sequence;
	
	/**
	 * redis缓存key的模板
	 */
	private static final String KEY_TOKEN_TEMPLATE = "idempotent:token:%s";
	private static final String KEY_ARGS_TEMPLATE = "idempotent:args:%s_%s";

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
			String idempotentKey = this.idempotentKey(joinPoint, idempotent);
			// 4.2、根据 key前缀 + @ApiIdempotent.value() + 方法签名 + 参数 构建缓存键值；确保幂等处理的操作对象是：同样的 @ApiIdempotent.value() + 方法签名 + 参数
			String uid = SubjectUtils.isAuthenticated() ? SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid() : "guest";
			String lockKey = String.format(KEY_ARGS_TEMPLATE, uid, idempotentKey + "_" + this.generateKey(method, joinPoint.getArgs()));
			String lockValue = sequence.nextId().toString();
			RLock fairLock = null;
			try {
				// 4.3、通过RLock确保只有一个接口能够正常访问
				fairLock = redissonOperationTemplate.tryLock(lockKey, lockValue, idempotent.expireMillis(), idempotent.retryTimes(), idempotent.retryInterval());
				if (!fairLock.isLocked()) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				redissonOperationTemplate.unlock(fairLock);
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
			String lockKey = String.format(KEY_TOKEN_TEMPLATE, token);
			String lockValue = sequence.nextId().toString();
			RLock fairLock = null;
			try {
				// 5.4、通过RLock确保只有一个接口能够正常访问
				fairLock = redissonOperationTemplate.tryLock(lockKey, lockValue, idempotent.expireMillis(), idempotent.retryTimes(), idempotent.retryInterval());
				if (!fairLock.isLocked()) {
					return joinPoint.proceed();
				} else {
					log.debug("Idempotent hits, key=" + lockKey);
					throw new IdempotentException(ApiCode.SC_FAIL, "request.method.idempotent.hits");
				}
			} finally {
				redissonOperationTemplate.unlock(fairLock);
			}
		}
		return joinPoint.proceed();
		
	}

	protected String idempotentKey(ProceedingJoinPoint joinPoint, ApiIdempotent idempotent) {
		// 1、获取方法
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String[] parameterNames = methodSignature.getParameterNames(); 
		Object[] parameters = joinPoint.getArgs();
		String idempotentKey = idempotent.value();
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
		else {
			StringJoiner joiner = new StringJoiner("");
			RequestMapping requestMapping = AnnotationUtils.findAnnotation(method.getDeclaringClass(), RequestMapping.class);
			if(!Objects.isNull(requestMapping)) {
				log.debug("requestMapping: {}", JSONObject.toJSONString(requestMapping));
				Stream.of(Objects.isNull(requestMapping.value()) || ArrayUtils.isEmpty(requestMapping.value())
						? requestMapping.path()
						: requestMapping.value()).findFirst().ifPresent(path -> {
							joiner.add(path);
						});
			}
			if(!StringUtils.hasText(idempotentKey)) {
				PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
				if(!Objects.isNull(postMapping)) {
					log.debug("postMapping: {}", JSONObject.toJSONString(postMapping));
					Stream.of(Objects.isNull(postMapping.value()) || ArrayUtils.isEmpty(postMapping.value())
							? postMapping.path()
							: postMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
				if(!Objects.isNull(getMapping)) {
					Stream.of(Objects.isNull(getMapping.value()) || ArrayUtils.isEmpty(getMapping.value())
							? getMapping.path()
							: getMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
					log.debug("getMapping: {}", JSONObject.toJSONString(getMapping));
					joiner.add(getMapping.path()[0]);
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
				if(!Objects.isNull(methodRequestMapping)) {
					log.debug("requestMapping: {}", JSONObject.toJSONString(methodRequestMapping));
					Stream.of(Objects.isNull(methodRequestMapping.value()) || ArrayUtils.isEmpty(methodRequestMapping.value())
							? methodRequestMapping.path()
							: methodRequestMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
				if(!Objects.isNull(deleteMapping)) {
					log.debug("deleteMapping: {}", JSONObject.toJSONString(deleteMapping));
					Stream.of(Objects.isNull(deleteMapping.value()) || ArrayUtils.isEmpty(deleteMapping.value())
							? deleteMapping.path()
							: deleteMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				PatchMapping patchMapping = AnnotationUtils.findAnnotation(method, PatchMapping.class);
				if(!Objects.isNull(patchMapping)) {
					log.debug("patchMapping: {}", JSONObject.toJSONString(patchMapping));
					Stream.of(Objects.isNull(patchMapping.value()) || ArrayUtils.isEmpty(patchMapping.value())
							? patchMapping.path()
							: patchMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
				}
			}
			if(!StringUtils.hasText(idempotentKey)) {
				PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
				if(!Objects.isNull(putMapping)) {
					log.debug("putMapping: {}", JSONObject.toJSONString(putMapping));
					Stream.of(Objects.isNull(putMapping.value()) || ArrayUtils.isEmpty(putMapping.value())
							? putMapping.path()
							: putMapping.value()).findFirst().ifPresent(path -> {
								joiner.add(path);
							});
				}
			}
			idempotentKey = joiner.toString();
		}
		return idempotentKey;
	}
	
	/**
	 * 根据{方法名 + 参数列表}和md5转换生成key
	 * @throws JsonProcessingException 
	 */
	protected String generateKey(Method method, Object... args) throws JsonProcessingException {
		StringBuilder sb = new StringBuilder(method.toString());
		Annotation[][]  paramAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < args.length; i++) {
			if(Stream.of(paramAnnotations[i]).anyMatch(annt -> annt instanceof ApiIgnore)) {
				continue;
			}
			if(args[i] instanceof ServletRequest || args[i] instanceof ServletResponse) {
				continue;
			}
			sb.append(objectMapper.writeValueAsString(args[i]));
		}
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}

}
