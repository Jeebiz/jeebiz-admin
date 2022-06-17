/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;
import io.hiwepy.boot.api.dao.entities.PairModel;
import org.springframework.data.redis.core.RedisKey;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * 消息通知对象类型
 */
public enum InformToType {

	/**
	 * 组织机构
	 */
	ORG("org", "组织机构", (eventId)->{
		return BizRedisKey.INFORM_TARGET.getKey("org", eventId);
	}),
	/**
	 * 角色
	 */
	ROLE("role", "角色", (eventId)->{
		return BizRedisKey.INFORM_TARGET.getKey("role", eventId);
	}),
	/**
	 * 岗位
	 */
	POST("post","岗位",  (eventId)->{
		return BizRedisKey.INFORM_TARGET.getKey("post", eventId);
	}),
	/**
	 * 人员
	 */
	USER("user","人员",  (eventId)->{
		return BizRedisKey.INFORM_TARGET.getKey("user", eventId);
	});

	private String key;
	private String desc;
	private Function<Object, String> function;

	InformToType(String key, String desc, Function<Object, String> function) {
		this.key = key;
		this.desc = desc;
		this.function = function;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public boolean equals(InformToType target) {
		return this.compareTo(target) == 0;
	}

	public boolean equals(String target) {
		return this.compareTo(InformToType.valueOfIgnoreCase(target)) == 0;
	}

	public static InformToType valueOfIgnoreCase(String key) {
		for (InformToType targetEnum : InformToType.values()) {
			if (targetEnum.name().equals(key)) {
				return targetEnum;
			}
		}
		throw new NoSuchElementException("Cannot found InformToType with key '" + key + "'.");
	}

}

