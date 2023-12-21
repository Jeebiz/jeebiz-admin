/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

@Alias(value = "UserRoleEntity")
@TableName(value = "t_user_roles")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class UserRoleEntity extends BaseEntity<UserRoleEntity> {

	/**
	 * 主键id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 用户id
	 */
	@TableField(value = "user_id")
	private String userId;
	/**
	 * 角色id
	 */
	@TableField(value = "role_id")
	private String roleId;
	/**
	 * 优先级：用于默认登录角色
	 */
	@TableField(value = "priority")
	private Integer priority;

}
