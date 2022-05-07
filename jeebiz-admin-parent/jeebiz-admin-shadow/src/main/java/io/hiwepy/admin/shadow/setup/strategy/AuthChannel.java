/**
 * Copyright (C) 2022 杭州天音计算机系统工程有限公司
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.setup.strategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.cas.token.CasAssertionAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkMaAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkTmpCodeAuthenticationToken;
import org.apache.shiro.spring.boot.jwt.token.JwtAuthenticationToken;
import org.apache.shiro.spring.boot.qrcode.token.QrcodeAuthenticationToken;
import org.apache.shiro.spring.boot.weixin.token.WxMaAuthenticationToken;
import org.apache.shiro.spring.boot.weixin.token.WxMpAuthenticationToken;

import io.buji.pac4j.token.Pac4jToken;

/**
 *  认证方式
 */
public enum AuthChannel {

	ACCOUNT("account", "Account登录", (token, p2) -> false),
	USER_ID("userId", "UserId登录", (token, p2) -> false),
	CAS("cas", "Cas登录", (token, p2) -> CasAssertionAuthenticationToken.class.isAssignableFrom(token.getClass())),
	PASSWORD("password", "密码登录", (token, p2) -> JwtAuthenticationToken.class.isAssignableFrom(token.getClass())),
	DINGTALK_MA("dingtalk-ma", "钉钉小程序登录", (token, p2) -> DingTalkMaAuthenticationToken.class.isAssignableFrom(token.getClass())),
	DINGTALK_SCAN_CODE("dingtalk-scancode", "钉钉扫码登录", (token, p2) -> DingTalkScanCodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
	DINGTALK_TMPCODE("dingtalk-tmpcode", "钉钉内部应用免登", (token, p2) -> DingTalkTmpCodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
	WEIXIN_MP("wx-mp", "微信（公共号、服务号）登录", (token, p2) -> WxMpAuthenticationToken.class.isAssignableFrom(token.getClass())),
	WEIXIN_MA("wx-ma", "微信（小程序）登录", (token, p2) -> WxMaAuthenticationToken.class.isAssignableFrom(token.getClass())),
	QRCODE_SCAN("qrcode", "扫码登录", (token, p2) -> QrcodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
	SMS("sms", "短信登录", (token, p2) -> QrcodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
	PAC4J("pac4j", "Pac4j三方登录", (token, p2) -> Pac4jToken.class.isAssignableFrom(token.getClass()));

	private String key;
	private String desc;
    private BiFunction<AuthenticationToken, Object, Boolean> function;

	private AuthChannel(String key, String desc, BiFunction<AuthenticationToken, Object, Boolean> function) {
		this.key = key;
		this.desc = desc;
        this.function = function;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean equals(AuthChannel channel){
		return this.compareTo(channel) == 0;
	}

	public boolean equals(String key){
		return this.compareTo(AuthChannel.valueOfIgnoreCase(key)) == 0;
	}

	public static AuthChannel valueOf(AuthenticationToken token) {
		for (AuthChannel channel : AuthChannel.values()) {
			if(channel.function.apply(token, null)) {
				return channel;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthChannel with token '" + token.getClass() + "'.");
    }
	
	public static AuthChannel valueOfIgnoreCase(String key) {
		for (AuthChannel channel : AuthChannel.values()) {
			if(channel.getKey().equalsIgnoreCase(key)) {
				return channel;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthChannel with key '" + key + "'.");
    }

	public static List<Map<String, String>> toList() {
		List<Map<String, String>> channelList = new LinkedList<Map<String, String>>();
		for (AuthChannel channel : AuthChannel.values()) {
			channelList.add(channel.toMap());
		}
		return channelList;
	}

	public  Map<String, String> toMap() {
		Map<String, String> optMap = new HashMap<String, String>();
		optMap.put("key", this.getKey());
		optMap.put("desc", this.getDesc());
		return optMap;
	}

}
