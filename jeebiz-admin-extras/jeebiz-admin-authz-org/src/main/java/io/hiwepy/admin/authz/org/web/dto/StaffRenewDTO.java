package io.hiwepy.admin.authz.org.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "StaffRenewDTO", description = "员工信息更新参数DTO")
@Getter
@Setter
@ToString
public class StaffRenewDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构id编号")
	private String orgId;
	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "deptId", required = true, dataType = "String", value = "部门id编号")
	private String deptId;
	/**
	 * 团队id编号
	 */
	@ApiModelProperty(name = "teamId", required = true,dataType = "String", value = "团队id编号")
	private String teamId;
	/**
	 * 岗位id编号
	 */
	@ApiModelProperty(name = "postId", required = true, dataType = "String", value = "岗位id编号")
	private String postId;
	/**
	 * 员工id编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "员工id编号")
	private String id;
	/**
	 * 员工简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "员工简介")
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "员工状态（0:禁用|1:可用）", allowableValues = "1,0")
	private String status;
	/**
	 * 员工入职时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "员工入职时间")
	private String time24;

}
