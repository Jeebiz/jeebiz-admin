/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 第三方账号类型
 */
public enum ThirdpartyType {
	
	/**
	 * 微信小程序
	 */
	WXMA("微信小程序"),
	/**
	 * 微信小程序
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
	YIBAN("易班");

	private String vendor;

	private ThirdpartyType(String vendor) {
		this.vendor = vendor;
	}

	public String getVendor() {
		return vendor;
	}

	public boolean equals(ThirdpartyType type) {
		return this.compareTo(type) == 0;
	}

	public boolean equals(String type) {
		return this.compareTo(ThirdpartyType.valueOfIgnoreCase(type)) == 0;
	}

	public static ThirdpartyType valueOfIgnoreCase(String key) {
		for (ThirdpartyType thirdpartyEnum : ThirdpartyType.values()) {
			if (thirdpartyEnum.name().equals(key)) {
				return thirdpartyEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}

	public Map<String, String> toMap() {
		Map<String, String> driverMap = new HashMap<String, String>();
		driverMap.put("name", this.name());
		driverMap.put("vendor", this.getVendor());
		return driverMap;
	}

	public static List<Map<String, String>> driverList() {
		List<Map<String, String>> driverList = new LinkedList<Map<String, String>>();
		for (ThirdpartyType dbType : ThirdpartyType.values()) {
			driverList.add(dbType.toMap());
		}
		return driverList;
	}
	 
}