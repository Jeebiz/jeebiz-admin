/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao.entities;

import org.apache.ibatis.type.Alias;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(name = "id", dataType = "String", value = "ID")
	private String id;
	/**
	 * 层级
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "层级")
	private String level;
	/**
	 * 父级行政代码
	 */
	@ApiModelProperty(name = "parent_code", dataType = "String", value = "父级行政代码")
	private String parent_code;
	/**
	 * 行政代码
	 */
	@ApiModelProperty(name = "area_code", dataType = "String", value = "行政代码")
	private String area_code;
	/**
	 * 邮政编码
	 */
	@ApiModelProperty(name = "zip_code", dataType = "String", value = "邮政编码")
	private String zip_code;
	/**
	 * 区号
	 */
	@ApiModelProperty(name = "city_code", dataType = "String", value = "区号")
	private String city_code;
	/**
	 * 名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "名称")
	private String name;
	/**
	 * 简称
	 */
	@ApiModelProperty(name = "sname", dataType = "String", value = "简称")
	private String sname;
	/**
	 * 组合名
	 */
	@ApiModelProperty(name = "mname", dataType = "String", value = "组合名")
	private String mname;
	/**
	 * 拼音
	 */
	@ApiModelProperty(name = "pinyin", dataType = "String", value = "拼音")
	private String pinyin;
	/**
	 * 经度
	 */
	@ApiModelProperty(name = "lng", dataType = "String", value = "经度")
	private String lng;
	/**
	 * 纬度
	 */
	@ApiModelProperty(name = "lat", dataType = "String", value = "纬度")
	private String lat;

}
