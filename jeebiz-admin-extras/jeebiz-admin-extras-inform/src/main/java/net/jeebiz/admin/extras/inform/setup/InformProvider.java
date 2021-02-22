/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.jeebiz.boot.api.dao.entities.PairModel;

/**
 * 消息通知提供者
 */
public enum InformProvider {

	/**
	 * 站内通知
	 */
	NOTICE("站内通知"),
	/**
	 * 站内私信
	 */
	LETTER("站内私信"),
	/**
	 * 阿里云短信
	 */
	SMS_ALIYUN("阿里云短信"),
	/**
	 * 百度云短信
	 */
	SMS_BAIDU("百度云短信"),
	/**
	 * 腾讯云短信
	 */
	SMS_TENCENT("腾讯云短信"),
	/**
	 * 钉钉消息
	 */
	DINGTALK("钉钉消息"),
	/**
	 * 微信小程序
	 */
	WXMA("微信小程序"),
	/**
	 * 微信公众号
	 */
	WXMP("微信公众号"),
	/**
	 * 腾讯QQ
	 */
	QQ("腾讯QQ"),
	/**
	 * 微信小程序
	 */
	WEIBO("新浪微博"),
	/**
	 * 易班
	 */
	YIBAN("易班"),
	/**
	 * 极光推送
	 */
	JPUSH("极光推送");

	private String provider;

	private InformProvider(String provider) {
		this.provider = provider;
	}

	public String getProvider() {
		return provider;
	}

	public boolean equals(InformProvider provider) {
		return this.compareTo(provider) == 0;
	}

	public boolean equals(String provider) {
		return this.compareTo(InformProvider.valueOfIgnoreCase(provider)) == 0;
	}

	public static InformProvider valueOfIgnoreCase(String provider) {
		for (InformProvider providerEnum : InformProvider.values()) {
			if (providerEnum.name().equals(provider)) {
				return providerEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with provider '" + provider + "'.");
	}

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.name());
		pair.setValue(this.getProvider());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> providerList = new LinkedList<PairModel>();
		for (InformProvider provider : InformProvider.values()) {
			providerList.add(provider.toPair());
		}
		return providerList;
	}
	 
}