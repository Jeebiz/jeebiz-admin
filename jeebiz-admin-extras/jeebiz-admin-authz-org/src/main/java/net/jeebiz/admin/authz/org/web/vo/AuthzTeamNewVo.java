package net.jeebiz.admin.authz.org.web.vo;

import java.io.Serializable;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzTeamNewVo", description = "新增团队信息参数Vo")
@Getter
@Setter
@ToString
public class AuthzTeamNewVo implements Serializable {

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
	 * 团队名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "团队名称")
	
	private String name;
	/**
	 * 团队简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "团队简介")
	
	private String intro;

}
