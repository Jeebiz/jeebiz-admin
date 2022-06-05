package io.hiwepy.admin.authz.org.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "StaffDTO", description = "员工信息DTO")
@Getter
@Setter
@ToString
public class StaffDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构id编号")
	private String orgId;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "orgName", dataType = "String", value = "机构名称")
	private String orgName;
	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门id编号")
	private String deptId;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "deptName", dataType = "String", value = "部门名称")
	private String deptName;
	/**
	 * 岗位id编号
	 */
	@ApiModelProperty(name = "postId", dataType = "String", value = "岗位id编号")
	private String postId;
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(name = "postName", dataType = "String", value = "岗位名称")
	private String postName;
	/**
	 * 员工id编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "员工id编号")
	private String id;
	/**
	 * 员工简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "员工简介")
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "员工状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 员工入职时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "员工入职时间")
	private String time24;

	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", dataType = "String", value = "用户别名（昵称）")
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "account", dataType = "String", value = "用户名")
	private String account;
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

}
