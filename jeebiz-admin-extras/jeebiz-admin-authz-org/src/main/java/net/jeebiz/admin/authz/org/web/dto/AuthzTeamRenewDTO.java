package net.jeebiz.admin.authz.org.web.dto;

import java.io.Serializable;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzTeamRenewDTO", description = "团队信息更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzTeamRenewDTO implements Serializable {
	
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
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "团队ID编号")
	private String id;
	/**
	 * 团队名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "团队名称")
	
	private String name;
	/**
	 * 团队简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "团队简介")
	
	private String intro;
	/**
	 * 团队状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "团队状态（0:禁用|1:可用）", allowableValues = "1,0")
	private String status;

}
