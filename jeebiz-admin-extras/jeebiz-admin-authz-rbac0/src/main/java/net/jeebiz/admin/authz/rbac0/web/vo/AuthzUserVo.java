/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzUserVo", description = "用户信息参数Vo")
@Getter
@Setter
@ToString
public class AuthzUserVo {

	/**
	 * 用户ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户ID")
	private String id;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "用户唯一ID（员工信息表ID）")
	private String uid;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "ucode", dataType = "String", value = "用户唯一编号（工号）")
	private String ucode;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户名")
	private String username;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户昵称")
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", required = true, dataType = "String", value = "电子邮箱")
	private String email;
	/**
	 * 用户备注
	 */
	@ApiModelProperty(name = "remark", required = true, dataType = "String", value = "用户备注")
	private String remark;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "用户状态（0:禁用|1:可用|2:锁定）")
	private String status;
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
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	@ApiModelProperty(name = "roleName", required = true, dataType = "String", value = "角色名称（可能多个组合，如：角色1,角色2）")
	private String roleName;
	/**
	 * 初始化时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "初始化时间")
	private String time24;

}
