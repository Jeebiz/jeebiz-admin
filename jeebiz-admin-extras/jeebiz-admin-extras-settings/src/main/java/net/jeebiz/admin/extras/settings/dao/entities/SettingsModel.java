/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

/**
 * 系统参数信息Model
 */
@Alias("SettingsModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class SettingsModel extends BaseModel<SettingsModel> {

	/**
	 * 参数id
	 */
	private String id;
	/**
	 * 参数分组
	 */
	private String gkey;
	/**
	 * 参数标签
	 */
	private String label;
	/**
	 * 参数键
	 */
	private String key;
	/**
	 * 参数值
	 */
	private String value;
	/**
	 * 参数单位:如 KB
	 */
	private String unit;
	/**
	 * 参数展示类型：（text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）
	 */
	private String type;
	/**
	 * 参数验证规则：如（required|range:[0,100] (多个用|隔开)）
	 */
	private String rules;
	/**
	 * 参数备注信息
	 */
	private String remark;
	/**
	 * 参数提示信息
	 */
	private String placeholder;
	/**
	 * 参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]
	 */
	private String source;
	/**
	 * 参数状态：0:不可用、1：可用
	 */
	private String status;
	/**
	 * 参数排序:组内排序
	 */
	private int order;
	
}
