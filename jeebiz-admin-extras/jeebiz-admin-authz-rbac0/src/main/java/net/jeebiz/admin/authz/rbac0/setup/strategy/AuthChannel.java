/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.setup.strategy;

import java.util.*;

/**
 *  存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
public enum AuthChannel {

	LOCAL("local", "本地目录"),
	FDFS("fdfs", "FastDFS"),
	OSS_ALIYUN("oss-aliyun", "对象存储（阿里云）"),
	OSS_TENCENT("oss-tencent", "对象存储（腾讯云）"),
	OSS_BAIDU("oss-baidu", "对象存储（百度云）"),
	OSS_HUAWEI("oss-huawei", "对象存储（华为云）"),
	OSS_MINIO("oss-minio", "对象存储（Minio）");

	private String key;
	private String desc;

	private AuthChannel(String key, String desc) {
		this.key = key;
		this.desc = desc;
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

	public boolean equals(AuthChannel type){
		return this.compareTo(type) == 0;
	}

	public boolean equals(String key){
		return this.compareTo(AuthChannel.valueOfIgnoreCase(key)) == 0;
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
