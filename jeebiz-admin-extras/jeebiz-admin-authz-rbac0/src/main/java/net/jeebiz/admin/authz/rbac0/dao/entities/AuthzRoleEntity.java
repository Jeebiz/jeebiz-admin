/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;

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

@Alias(value = "AuthzRoleModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_role_list")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzRoleEntity extends PaginationEntity<AuthzRoleEntity> {

	/**
	 * 角色id
	 */
	@TableId(value="r_id",type= IdType.AUTO)
	private String id;
	/**
	 * 角色编码
	 */
	@TableField(value = "r_key")
	private String key;
	/**
	 * 角色名称
	 */
	@TableField(value = "r_name")
	private String name;
	/**
	 * 角色简介
	 */
	@TableField(value = "r_intro")
	private String intro;
	/**
	 * 角色类型（1:原生|2:继承|3:复制|4:自定义）
	 */
	@TableField(value = "r_type")
	private String type;
	/**
	 * 角色状态（0:禁用|1:可用）
	 */
	@TableField(value = "r_status")
	private String status;
	/**
	 * 角色授权的标记集合
	 */
	@TableField(exist = false)
	private List<String> perms;
	/**
	 * 角色已分配用户量
	 */
	@TableField(exist = false)
	private int users;

}
