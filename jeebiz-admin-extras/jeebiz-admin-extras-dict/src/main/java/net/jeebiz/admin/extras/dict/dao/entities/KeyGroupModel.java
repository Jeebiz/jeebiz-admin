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
 * 基础数据分组信息Model
 */
@Alias("KeyGroupModel")
@SuppressWarnings("serial")
@TableName(value = "sys_data_pairgroup")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class KeyGroupModel extends PaginationEntity<KeyGroupModel> {

	/**
	 * 数据分组id
	 */
	@TableId(value="g_id",type= IdType.AUTO)
	private String id;
	/**
	 * 数据分组键
	 */
	@TableField(value = "g_key")
	private String key;
	/**
	 * 数据分组值
	 */
	@TableField(value = "g_text")
	private String value;
	/**
	 * 数据分组状态：0:不可用、1：可用
	 */
	@TableField(value = "g_status")
	private String status;
	/**
	 * 数据分组排序
	 */
	@TableField(value = "g_order")
	private int orderBy;

}
