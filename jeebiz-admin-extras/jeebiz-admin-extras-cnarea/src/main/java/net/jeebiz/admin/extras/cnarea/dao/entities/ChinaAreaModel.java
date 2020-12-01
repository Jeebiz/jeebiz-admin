/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.cnarea.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

/**
 * 中国行政地区信息Model
 * 
 */
@Alias("ChinaAreaModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class ChinaAreaModel extends BaseModel<ChinaAreaModel> {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 层级
	 */
	private String level;
	/**
	 * 父级行政代码
	 */
	private String parent_code;
	/**
	 * 行政代码
	 */
	private String area_code;
	/**
	 * 邮政编码
	 */
	private String zip_code;
	/**
	 * 区号
	 */
	private String city_code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String sname;
	/**
	 * 组合名
	 */
	private String mname;
	/**
	 * 拼音
	 */
	private String pinyin;
	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 纬度
	 */
	private String lat;

}
