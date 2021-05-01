/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import net.jeebiz.admin.extras.core.setup.redis.RedisConstant;
import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.boot.api.dao.entities.PairModel;

/**
 * 消息通知对象
 */
public enum InformTarget {
	
	/**
	 * 所有用户
	 */
	ALL("所有用户", (userId)->{
        return RedisKeyGenerator.getKeyStr(RedisConstant.USER_INFO_PREFIX, "*");
    }),
	/**
	 * 指定用户
	 */
	SPECIFIC("指定用户", (userId)->{
        return RedisKeyGenerator.getUserInfoPrefix(userId);
    });

	private String target;
    private Function<String,String> function;
    
	private InformTarget(String target, Function<String,String> function) {
		this.target = target;
		this.function = function;
	}

	public String getTarget() {
		return target;
	}
	
	public Function<String, String> getFunction() {
        return function;
    }

	public boolean equals(InformTarget target) {
		return this.compareTo(target) == 0;
	}

	public boolean equals(String target) {
		return this.compareTo(InformTarget.valueOfIgnoreCase(target)) == 0;
	}

	public static InformTarget valueOfIgnoreCase(String key) {
		for (InformTarget targetEnum : InformTarget.values()) {
			if (targetEnum.name().equals(key)) {
				return targetEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.name());
		pair.setValue(this.getTarget());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformTarget targetEnum : InformTarget.values()) {
			pairList.add(targetEnum.toPair());
		}
		return pairList;
	}
	 
}