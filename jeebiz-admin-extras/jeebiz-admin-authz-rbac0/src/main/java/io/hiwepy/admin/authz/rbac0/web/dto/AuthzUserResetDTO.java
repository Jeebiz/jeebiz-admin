/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzUserResetDTO", description = "个人信息更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzUserResetDTO {

	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "nickname", dataType = "String", value = "用户昵称")
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 职位信息
	 */
	@ApiModelProperty(name = "post", dataType = "String", value = "职位信息")
	private String post;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", dataType = "String", value = "手机号码")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", dataType = "String", value = "电子邮箱")
	private String email;
	/**
	 * 用户备注
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "用户备注")
	private String remark;
	/**
	 * 性别：（1：男，2：女）
	 */
	@ApiModelProperty(name = "gender", dataType = "String", value = "性别：（1：男，2：女）")
	private Integer gender;
	/**
	 * 出生日期
	 */
	@ApiModelProperty(name = "birthday", dataType = "String", value = "出生日期")
	private String birthday;
	/**
	 * 身份证号码
	 */
	@ApiModelProperty(name = "idcard", dataType = "String", value = "身份证号码")
	private String idcard;

}
