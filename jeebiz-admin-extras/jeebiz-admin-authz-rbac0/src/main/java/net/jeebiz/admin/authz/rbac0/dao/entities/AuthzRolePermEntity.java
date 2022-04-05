/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

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

@Alias(value = "AuthzRolePermEntity")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_role_perms")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzRolePermEntity extends Model<AuthzRolePermEntity> {

	/**
	 * 	主键
	 */
	@TableId(value="r_id",type= IdType.AUTO)
	private String id;
	/**
	 * 角色授权的标记
	 */
	@TableField(value = "perms")
	private String perms;

}
