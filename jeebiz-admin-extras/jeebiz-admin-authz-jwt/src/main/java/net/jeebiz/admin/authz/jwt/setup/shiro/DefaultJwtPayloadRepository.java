/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.jwt.setup.shiro;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.apache.shiro.spring.boot.jwt.token.JwtAccessToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.hiwepy.jwt.JwtPayload;
import com.github.hiwepy.jwt.token.SignedWithSecretKeyJWTRepository;
import com.github.hiwepy.jwt.utils.SecretKeyUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import hitool.core.collections.CollectionUtils;

public class DefaultJwtPayloadRepository extends JwtPayloadRepository implements InitializingBean {

    private SignedWithSecretKeyJWTRepository secretKeyJWTRepository;
	private byte[] base64Secret = Base64.getDecoder().decode("6Tb9jCzN1jXppMrsLYfXbERiGiGp4nNtXuVdTGV9qN0=");
	private SecretKey secretKey = null;
	private String algorithm = "HS256";
	private String issuer = "www.jeebiz.net";
	private long period = 24 * 30 * 60 * 1000;
	
	public DefaultJwtPayloadRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository) {
		super();
		this.secretKeyJWTRepository = secretKeyJWTRepository;
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
	public String issueJwt(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		// 利用登陆用户信息构造jwt
		Map<String, Object> claims = Maps.newHashMap();
		
		claims.put("roles", JSONObject.toJSONString(CollectionUtils.isEmpty(principal.getRoles()) ? Lists.newArrayList() : principal.getRoles()));
		claims.put("perms", StringUtils.collectionToCommaDelimitedString(principal.getPerms()));
		claims.put("userid", principal.getUserid());
		
		return getSecretKeyJWTRepository().issueJwt(secretKey, UUID.randomUUID().toString(), principal.getUserid(),
				issuer, principal.getUserid(), claims, algorithm, -1);
	}
	

	@Override
	public boolean verify(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response,
			boolean checkExpiry) throws AuthenticationException {
		
		JwtAccessToken jwtToken = (JwtAccessToken) token;
		
		//JwtPayloadPrincipal model = (JwtPayloadPrincipal) subject.getPrincipal();
		
		return getSecretKeyJWTRepository().verify(secretKey, jwtToken.getToken(), checkExpiry);
		
	}
	
	@Override
	public JwtPayload getPayload(JwtAccessToken jwtToken, boolean checkExpiry) {
		return getSecretKeyJWTRepository().getPlayload(secretKey, jwtToken.getToken(), checkExpiry);
	}

	public SignedWithSecretKeyJWTRepository getSecretKeyJWTRepository() {
		return secretKeyJWTRepository;
	}

	public void setSecretKeyJWTRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository) {
		this.secretKeyJWTRepository = secretKeyJWTRepository;
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
