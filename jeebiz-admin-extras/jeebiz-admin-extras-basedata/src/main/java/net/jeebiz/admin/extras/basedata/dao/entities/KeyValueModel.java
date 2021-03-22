/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 基础数据信息Model
 */
@Alias("KeyValueModel")
@SuppressWarnings("serial")
public class KeyValueModel extends PaginationModel {

	/**
	 * 基础数据分组Key
	 */
	private String gkey;
	/**
	 * 基础数据分组
	 */
	private String gtext;
	/**
	 * 基础数据标签
	 */
	private String label;
	/**
	 * 基础数据键
	 */
	private String key;
	/**
	 * 基础数据值
	 */
	private String value;
	/**
	 * 数据状态：0:不可用、1：可用
	 */
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	private int order;
	
	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}

	public String getGtext() {
		return gtext;
	}

	public void setGtext(String gtext) {
		this.gtext = gtext;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
