package net.jeebiz.admin.authz.org.web.dto;

import java.io.Serializable;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzTeamDTO", description = "团队信息DTO")
@Getter
@Setter
@ToString
public class AuthzTeamDTO implements Serializable {

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
	 * 团队id编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "团队id编号")
	private String id;
	/**
	 * 团队名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "团队名称")
	
	private String name;
	/**
	 * 团队简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "团队简介")
	private String intro;
	/**
	 * 团队创建人id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "团队创建人id")
	private String uid;
	/**
	 * 团队状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "团队状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 团队创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "团队创建时间")
	private String time24;

}
