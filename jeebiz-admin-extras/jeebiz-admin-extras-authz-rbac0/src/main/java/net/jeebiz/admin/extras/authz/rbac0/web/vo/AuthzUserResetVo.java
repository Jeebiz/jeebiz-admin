/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzUserResetVo", description = "设置个人信息参数Vo")
public class AuthzUserResetVo {
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户名")
	private String username;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户昵称")
	@NotBlank(message = "昵称必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", required = true, dataType = "String", value = " 用户头像：图片路径或图标样式")
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
	@NotBlank(message = "性别必选")
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

}
