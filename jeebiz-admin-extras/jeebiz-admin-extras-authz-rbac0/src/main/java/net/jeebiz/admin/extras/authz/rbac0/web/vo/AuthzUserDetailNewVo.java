/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzUserDetailNewVo", description = "新增用户详细信息参数Vo")
public class AuthzUserDetailNewVo {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", required = true, dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", required = true, dataType = "String", value = "岗位ID编号")
	private String postId;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户昵称")
	private String alias;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户名")
	@NotBlank(message = "用户名必填")
	private String username;
	/**
	 * 用户密码
	 */
	@ApiModelProperty(name = "password", dataType = "String", value = "用户密码")
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	@ApiModelProperty(name = "salt", dataType = "String", value = "用户密码盐：用于密码加解密")
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	@ApiModelProperty(name = "secret", dataType = "String", value = "用户秘钥：用于用户JWT加解密")
	private String secret;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	@NotBlank(message = "手机号码必填")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", required = true, dataType = "String", value = "电子邮箱")
	@NotBlank(message = "电子邮箱必填")
	private String email;
	/**
	 * 用户备注
	 */
	@ApiModelProperty(name = "remark", required = true, dataType = "String", value = "用户备注")
	private String remark;
	/**
	 * 性别：（male：男，female：女）
	 */
	@ApiModelProperty(name = "gender", required = true, dataType = "String", value = "性别：（male：男，female：女）")
	private String gender;
	/**
	 * 出生日期
	 */
	@ApiModelProperty(name = "birthday", required = true, dataType = "String", value = "出生日期")
	private String birthday;
	/**
	 * 身份证号码
	 */
	@ApiModelProperty(name = "idcard", required = true, dataType = "String", value = "身份证号码")
	private String idcard;
	/**
	 * 角色ID（可能多个组合，如：1,2）
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色ID（可能多个组合，如：1,2）")
	private String roleId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
