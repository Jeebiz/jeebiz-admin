package net.jeebiz.admin.authz.org.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzStaffNewDTO", description = "新增员工信息参数DTO")
@Getter
@Setter
@ToString
public class AuthzStaffNewDTO implements Serializable {

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
	 * 团队ID编号
	 */
	@ApiModelProperty(name = "teamId", required = true,dataType = "String", value = "团队ID编号")
	private String teamId;
	/**
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", required = true, dataType = "String", value = "岗位ID编号")
	private String postId;
	/**
	 * 员工ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "员工ID编号")
	private String id;
	/**
	 * 员工简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "员工简介")
	private String intro;
	/**
	 * 员工入职时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "员工入职时间")
	private String time24;

}