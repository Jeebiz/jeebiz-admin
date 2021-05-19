/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.dao.entities;

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

@SuppressWarnings("serial")
@Alias("AuthzFeatureOptModel")
@TableName(value = "sys_authz_feature_opts")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzFeatureOptModel extends Model<AuthzFeatureOptModel> {

	/**
	 * 功能菜单id
	 */
	@TableField(value = "f_id")
	private String featureId;
	/**
	 * 功能操作id
	 */
	@TableId(value="opt_id",type= IdType.AUTO)
	private String id;
	/**
	 * 功能操作名称
	 */
	@TableField(value = "opt_name")
	private String name;
	/**
	 * 功能操作图标样式
	 */
	@TableField(value = "opt_icon")
	private String icon;
	/**
	 * 功能操作排序
	 */
	@TableField(value = "opt_order")
	private String orderBy;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	@TableField(value = "opt_visible")
	private String visible;
	/**
	 * 功能操作是否授权(1:已授权|0:未授权)
	 */
	@TableField(exist = false)
	private String checked;
	/**
	 * 功能操作权限标记
	 */
	@TableField(value = "opt_perms")
	private String perms;
	
}
