/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.BaseModel;

/**
 * 基础数据分组信息Model
 */
@Alias("KeyGroupModel")
@SuppressWarnings("serial")
public class KeyGroupModel extends BaseModel {

	/**
	 * 数据分组ID
	 */
	private String id;
	/**
	 * 数据分组键
	 */
	private String key;
	/**
	 * 数据分组值
	 */
	private String value;
	/**
	 * 数据分组状态：0:不可用、1：可用
	 */
	private String status;
	/**
	 * 数据分组排序
	 */
	private int order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
