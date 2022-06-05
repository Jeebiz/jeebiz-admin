/**
 * Copyright (C) 2022 杭州天音计算机系统工程有限公司
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.org.setup.strategy;


import java.util.*;
import java.util.function.BiFunction;

/**
 *  树节点类型
 */
public enum TreeChannel {

	TREE_XD("tree_xd", "学段", (token, p2) -> false),
	TREE_XX("tree_xx", "学校", (token, p2) -> false),
	TREE_XQ("tree_xq", "校区", (token, p2) -> false)
;

	private String key;
	private String desc;
    private BiFunction<Object, Object, Boolean> function;

	private TreeChannel(String key, String desc, BiFunction<Object, Object, Boolean> function) {
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
	
	public boolean equals(TreeChannel channel){
		return this.compareTo(channel) == 0;
	}

	public boolean equals(String key){
		return this.compareTo(TreeChannel.valueOfIgnoreCase(key)) == 0;
	}

	public static TreeChannel valueOfIgnoreCase(String key) {
		for (TreeChannel channel : TreeChannel.values()) {
			if(channel.getKey().equalsIgnoreCase(key)) {
				return channel;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthChannel with key '" + key + "'.");
    }

	public static List<Map<String, String>> toList() {
		List<Map<String, String>> channelList = new LinkedList<Map<String, String>>();
		for (TreeChannel channel : TreeChannel.values()) {
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
