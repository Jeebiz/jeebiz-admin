/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.cnarea.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 中国行政地区信息Model
 * 
 */
@Alias("ChinaAreaModel")
@SuppressWarnings("serial")
@TableName(value = "cnarea_2019")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ChinaAreaEntity extends Model<ChinaAreaEntity> implements Cloneable {

	/**
	 * id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 层级
	 */
	@TableField(value = "level")
	private String level;
	/**
	 * 父级行政代码
	 */
	@TableField(value = "parent_code")
	private String parent_code;
	/**
	 * 行政代码
	 */
	@TableField(value = "area_code")
	private String area_code;
	/**
	 * 邮政编码
	 */
	@TableField(value = "zip_code")
	private String zip_code;
	/**
	 * 区号
	 */
	@TableField(value = "city_code")
	private String city_code;
	/**
	 * 名称
	 */
	@TableField(value = "name")
	private String name;
	/**
	 * 简称
	 */
	@TableField(value = "short_name")
	private String sname;
	/**
	 * 组合名
	 */
	@TableField(value = "merger_name")
	private String mname;
	/**
	 * 拼音
	 */
	@TableField(value = "pinyin")
	private String pinyin;
	/**
	 * 经度
	 */
	@TableField(value = "lng")
	private String lng;
	/**
	 * 纬度
	 */
	@TableField(value = "lat")
	private String lat;

}
