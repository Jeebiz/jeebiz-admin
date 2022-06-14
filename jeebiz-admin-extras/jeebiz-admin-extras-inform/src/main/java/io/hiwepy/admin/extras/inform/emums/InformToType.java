/**
 * Copyright (C) 2022 杭州天音计算机系统工程有限公司
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

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
		return RedisKey.getKeyStr(BizRedisKeyConstant.INFORM_TARGET_KEY, "org", eventId);
	}),
	/**
	 * 角色
	 */
	ROLE("role", "角色", (eventId)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.INFORM_TARGET_KEY, "role", eventId);
	}),
	/**
	 * 岗位
	 */
	POST("post","岗位",  (eventId)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.INFORM_TARGET_KEY, "post", eventId);
	}),
	/**
	 * 人员
	 */
	USER("user","人员",  (eventId)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.INFORM_TARGET_KEY, "user", eventId);
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

