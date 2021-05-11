/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.jeebiz.boot.api.dao.entities.BaseEntity;

/**
 * 系统参数信息Model
 */
@Alias("SettingsModel")
@TableName(value = "sys_data_settings")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@SuppressWarnings("serial")
public class SettingsModel extends BaseEntity<SettingsModel> {

	/**
	 * 参数id
	 */
	@TableId(value="d_id",type= IdType.AUTO)
	private String id;
	/**
	 * 参数分组
	 */
	@TableField(value = "d_group")
	private String gkey;
	/**
	 * 参数标签
	 */
	@TableField(value = "d_label")
	private String label;
	/**
	 * 参数键
	 */
	@TableField(value = "d_key")
	private String key;
	/**
	 * 参数值
	 */
	@TableField(value = "d_text")
	private String value;
	/**
	 * 参数单位:如 KB
	 */
	@TableField(value = "d_unit")
	private String unit;
	/**
	 * 参数展示类型：（text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）
	 */
	@TableField(value = "d_type")
	private String type;
	/**
	 * 参数验证规则：如（required|range:[0,100] (多个用|隔开)）
	 */
	@TableField(value = "d_rules")
	private String rules;
	/**
	 * 参数提示信息
	 */
	@TableField(value = "d_placeholder")
	private String placeholder;
	/**
	 * 参数备注信息
	 */
	@TableField(value = "d_remark")
	private String remark;
	/**
	 * 参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]
	 */
	@TableField(value = "d_source")
	private String source;
	/**
	 * 参数状态：0:不可用、1：可用
	 */
	@TableField(value = "d_status")
	private String status;
	/**
	 * 参数排序:组内排序
	 */
	@TableField(value = "d_order")
	private int orderBy;
	
}
