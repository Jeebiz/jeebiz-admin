/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.shiro.jwt;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.concurrent.ExecutionException;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

/**
 * 	根据keyid获取对应的Key
 */
public class JwtSigningKeyRedisResolver extends SigningKeyResolverAdapter {

	private StringRedisTemplate stringRedisTemplate;
	
	public JwtSigningKeyRedisResolver(StringRedisTemplate stringRedisTemplate) {
		super();
		this.stringRedisTemplate = stringRedisTemplate;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        
        //inspect the header or claims, lookup and return the signing key
        String keyId = jwsHeader.getKeyId(); //or any other field that you need to inspect
        if(!StringUtils.hasText(keyId)) {
			throw new AuthenticationException("The header named 'kid' is required.");
		}
        try {
			return lookupVerificationKey(keyId); //implement me
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	protected Key lookupVerificationKey(String keyId) throws ExecutionException, GeneralSecurityException {
		
		// 获取应用基本信息Redis缓存
		//String appInfo = (String) getStringRedisTemplate().opsForHash().get(Constants.DSB_APPS, String.format("jwt-%s", keyId));
		// 根据应用uid获取应用信息：主要获取rsa公钥和rsa私钥
		//MyApplicationModel appModel = JSONObject.parseObject(appInfo, MyApplicationModel.class);
		
		// 第一种用法：公钥加密，私钥解密。---用于加解密
		// 第二种用法：私钥签名，公钥验签。---用于签名
		
		// 因为在管理端使用的是私钥签名，这里需要构建公钥对象以便进行jwt验证
		//RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtils.genPublicKey("RSA", Base64.decode(appModel.getAppKey()));
		
		//return publicKey;
		return null;
		
	}
	
	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}
	
}
