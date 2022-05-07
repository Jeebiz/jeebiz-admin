/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.setup.shiro.jwt;

import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.apache.shiro.spring.boot.jwt.token.JwtAuthorizationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.hiwepy.jwt.JwtClaims;
import com.github.hiwepy.jwt.JwtPayload;
import com.github.hiwepy.jwt.exception.ExpiredJwtException;
import com.github.hiwepy.jwt.exception.IncorrectJwtException;
import com.github.hiwepy.jwt.exception.InvalidJwtToken;
import com.github.hiwepy.jwt.exception.JwtException;
import com.github.hiwepy.jwt.token.SignedWithSecretKeyJWTRepository;
import com.github.hiwepy.jwt.utils.SecretKeyUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import hitool.core.collections.CollectionUtils;
import io.hiwepy.admin.api.UserProfiles;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;

public class DefaultJwtPayloadRepository implements  JwtPayloadRepository, InitializingBean {

	// 过期时间（7天），单位毫秒
	private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
	private byte[] base64Secret = Base64.getDecoder().decode("6Tb9jCzN1jXppMrsLYfXbERiGiGp4nNtXuVdTGV9qN0=");
	private SecretKey secretKey = null;
	private String algorithm = "HS256";
	private String issuer = "www.jeebiz.net";
	private long period = EXPIRE_TIME;
    private SignedWithSecretKeyJWTRepository secretKeyJWTRepository;
    private RedisOperationTemplate redisOperationTemplate;

    public DefaultJwtPayloadRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository, RedisOperationTemplate redisOperationTemplate) {
		super();
		this.secretKeyJWTRepository = secretKeyJWTRepository;
		this.redisOperationTemplate = redisOperationTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(secretKey == null) {
			try {
				secretKey = SecretKeyUtils.genSecretKey(base64Secret, "HmacSHA256");
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public String issueJwt(AuthenticationToken token, Subject subject) {

		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();

		// 利用登陆用户信息构造jwt
		Map<String, Object> claims = Maps.newHashMap();

		claims.put(JwtClaims.UID, principal.getUserid());
		claims.put(JwtClaims.UUID, principal.getUsercode());
		claims.put(JwtClaims.RID, principal.getRoleid());
		claims.put(JwtClaims.RKEY, principal.getRole());
		claims.put(JwtClaims.ROLES, JSONObject.toJSONString(CollectionUtils.isEmpty(principal.getRoles()) ? Lists.newArrayList() : principal.getRoles()));
		claims.put(JwtClaims.PERMS, StringUtils.collectionToCommaDelimitedString(principal.getPerms()));

		// 4、查询是否缓存（在线用户缓存数据）
		if (MapUtils.isNotEmpty(principal.getProfile())) {

			Map<String, Object> profile = new HashMap<String, Object>();
			profile.put(UserProfiles.GENDER, MapUtils.getString(principal.getProfile(), UserProfiles.GENDER));
			profile.put(JwtClaims.UNAME, MapUtils.getString(principal.getProfile(), UserProfiles.NICKNAME));
			claims.put(JwtClaims.PROFILE, profile);
		}

		return this.issueJwt(principal.getUserid(), claims);

	}

	@Override
	public String issueJwt(String userId, Map<String, Object> claims) {
		try {

			// 1、签发Token并进行Redis缓存
			String jwtId = BizRedisKey.USER_TOKEN.getKey(userId);

			String jwtString = getSecretKeyJWTRepository().issueJwt(secretKey, jwtId, userId,
					issuer, userId, claims, algorithm, -1);

			getRedisOperationTemplate().set(jwtId, jwtString, Duration.ofDays(7));

			return jwtString;

		} catch (JwtException e) {
			throw new AuthenticationException("JWT issue error");
		}

	}

	@Override
	public boolean verify(AuthenticationToken token, Subject subject, boolean checkExpiry) throws AuthenticationException {
		JwtAuthorizationToken jwtToken = (JwtAuthorizationToken) token;
		String jwtString = String.valueOf(jwtToken.getCredentials());
		return this.verify(jwtString, checkExpiry);
	}

	@Override
	public boolean verify(String token, boolean checkExpiry) throws AuthenticationException {

		try {
			return getSecretKeyJWTRepository().verify(secretKey, token, checkExpiry);
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException("JWT has expired");
		} catch (InvalidJwtToken e) {
			throw new InvalidJwtToken("JWT has invalid");
		} catch (IncorrectJwtException e) {
			throw new IncorrectJwtException("JWT has incorrect");
		}
	}

	@Override
	public JwtPayload getPayload(JwtAuthorizationToken jwtToken, boolean checkExpiry) {
		return this.getPayload(jwtToken.getToken(), checkExpiry);
	}

	@Override
	public JwtPayload getPayload(String token, boolean checkExpiry) {
		try {

			// 1、解析并检查jwt
			JwtPayload payload = getSecretKeyJWTRepository().getPlayload(secretKey, token, false);
			// 2、检查jwt是否过期
			if(!getRedisOperationTemplate().hasKey(payload.getTokenId())) {
				throw new ExpiredJwtException("JWT has expired");
			}
			// 3、查询是否用户被禁用
			String userKey = BizRedisKey.USER_INFO.getKey(payload.getSubject());
			Object disabled = redisOperationTemplate.hGet(userKey, UserProfiles.DISABLED );
			if(Objects.nonNull(disabled) && Integer.parseInt(disabled.toString()) == 0) {
				throw new DisabledAccountException("账号已被禁用，请联系管理员！");
			}

			return payload;
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException("JWT has expired");
		} catch (InvalidJwtToken e) {
			throw new InvalidJwtToken("JWT has invalid");
		} catch (IncorrectJwtException e) {
			throw new IncorrectJwtException("JWT has incorrect");
		}
	}

	public SignedWithSecretKeyJWTRepository getSecretKeyJWTRepository() {
		return secretKeyJWTRepository;
	}

	public void setSecretKeyJWTRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository) {
		this.secretKeyJWTRepository = secretKeyJWTRepository;
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}

	public byte[] getBase64Secret() {
		return base64Secret;
	}

	public void setBase64Secret(byte[] base64Secret) {
		this.base64Secret = base64Secret;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

}
