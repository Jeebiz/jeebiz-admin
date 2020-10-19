/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.CollectionUtils;

@Alias(value = "AuthzUserModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzUserModel extends PaginationModel<AuthzUserModel> {
	
	/**
	 * 用户Id
	 */
	private String id;
	/**
	 * 用户唯一ID（员工信息表ID）
	 */
	private String uid;
	/**
	 * 用户唯一编码（工号）
	 */
	protected String ucode;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	private String secret;
	/**
	 * 用户别名（昵称）
	 */
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	private String avatar;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 性别：（male：男，female：女）
	 */
	private String gender;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 身份证号码
	 */
	private String idcard;
	/**
	 * 用户备注
	 */
	private String remark;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	private String status;
	/**
	 * 初始化时间
	 */
	private String time24;

	/**
	 * 角色ID（可能多个组合，如：1,2）
	 */
	private String roleId;
	private List<String> roles = Lists.newArrayList();
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	private String roleName;
	/**
	 * 关键词搜索
	 */
	private String keywords;
	/**
	 * 用户关联角色:这里这么写是为了提高前端渲染效率
	 */
	private List<PairModel> roleList = Lists.newArrayList();
	

	public String getRoleId() {
		return StringUtils.defaultString(roleId, CollectionUtils.isEmpty(roleList) ? "" : roleList.stream().map(mapper -> mapper.getKey()).collect(Collectors.joining(",")));
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return StringUtils.defaultString(roleName, CollectionUtils.isEmpty(roleList) ? "" : roleList.stream().map(mapper -> mapper.getValue()).collect(Collectors.joining(",")));
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
