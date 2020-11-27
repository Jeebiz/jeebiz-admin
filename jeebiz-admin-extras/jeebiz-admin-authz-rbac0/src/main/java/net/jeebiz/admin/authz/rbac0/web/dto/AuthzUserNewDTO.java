/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzUserNewDTO", description = "新增用户详细信息参数DTO")
@Getter
@Setter
@ToString
public class AuthzUserNewDTO {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", dataType = "String", value = "岗位ID编号")
	private String postId;
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
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "ucode", required = true, dataType = "String", value = "用户唯一编号（工号）")
	@NotBlank(message = "用户唯一编号（工号）必填")
	private String ucode;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户昵称")
	@NotBlank(message = "用户昵称必填")
	private String alias;
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
	@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}",message = "无效的手机号")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", required = true, dataType = "String", value = "电子邮箱")
	@NotBlank(message = "电子邮箱必填")
	@Email(message = "无效的邮箱")
	private String email;
	/**
	 * 用户备注
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "用户备注")
	private String remark;
	/**
	 * 性别：（male：男，female：女）
	 */
	@ApiModelProperty(name = "gender", required = true, dataType = "String", value = "性别：（male：男，female：女）")
	@NotBlank(message = "性别必填")
	private String gender;
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
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色ID")
	@NotBlank(message = "角色必选")
	private String roleId;

}
