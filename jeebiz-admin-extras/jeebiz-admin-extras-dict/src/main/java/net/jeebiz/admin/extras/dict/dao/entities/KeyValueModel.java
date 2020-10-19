/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 基础数据信息Model
 */
@Alias("KeyValueModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class KeyValueModel extends PaginationModel<KeyValueModel> {

	/**
	 * 基础数据分组Key
	 */
	private String gkey;
	private String[] gkeys;
	/**
	 * 基础数据分组
	 */
	private String gtext;
	/**
	 * 基础数据ID
	 */
	private String id;
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
	 * 数据描述
	 */
	private String text;
	/**
	 * 数据状态：0:不可用、1：可用
	 */
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	private int order;

}
