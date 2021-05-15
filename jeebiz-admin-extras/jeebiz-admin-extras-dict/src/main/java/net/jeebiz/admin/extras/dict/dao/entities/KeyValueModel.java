/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao.entities;

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
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

/**
 * 基础数据信息Model
 */
@Alias("KeyValueModel")
@SuppressWarnings("serial")
@TableName(value = "sys_data_pairvalue")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class KeyValueModel extends PaginationEntity<KeyValueModel> {

	/**
	 * 基础数据分组Key
	 */
	@TableField(value = "d_group")
	private String gkey;
	
	@TableField(exist = false)
	private String[] gkeys;
	/**
	 * 基础数据分组
	 */
	@TableField(exist = false)
	private String gtext;
	/**
	 * 基础数据id
	 */
	@TableId(value="d_id",type= IdType.AUTO)
	private String id;
	/**
	 * 基础数据标签
	 */
	@TableField(value = "d_label")
	private String label;
	/**
	 * 基础数据键
	 */
	@TableField(value = "d_key")
	private String key;
	/**
	 * 基础数据值
	 */
	@TableField(value = "d_value")
	private String value;
	/**
	 * 数据描述
	 */
	@TableField(value = "d_text")
	private String text;
	/**
	 * 数据状态：0:不可用、1：可用
	 */
	@TableField(value = "d_status")
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@TableField(value = "d_order")
	private int orderBy;

}
