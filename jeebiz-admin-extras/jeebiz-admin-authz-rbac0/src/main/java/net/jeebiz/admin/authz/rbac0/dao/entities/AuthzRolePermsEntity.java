/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Alias(value = "AuthzRolePermsModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_role_perms")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzRolePermsEntity extends Model<AuthzRolePermsEntity> {

	/**
	 * 	主键
	 */
	@TableId(value="r_id",type= IdType.AUTO)
	private String id;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 角色授权的标记集合
	 */
	private List<String> perms = Lists.newArrayList();

}
