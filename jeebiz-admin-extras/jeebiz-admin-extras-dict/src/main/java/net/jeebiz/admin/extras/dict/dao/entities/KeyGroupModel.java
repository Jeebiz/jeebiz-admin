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
 * 基础数据分组信息Model
 */
@Alias("KeyGroupModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class KeyGroupModel extends PaginationModel<KeyGroupModel> {

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

}
