/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import javax.validation.constraints.NotBlank;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzRoleAllotUserPaginationVo", description = "角色分配用户分页查询参数Vo")
@Getter
@Setter
@ToString
public class AuthzRoleAllotUserPaginationVo extends AbstractPaginationVo {

	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色ID")
	@NotBlank(message = "角色ID必填")
	private String id;
	/**
	 * 启用状态
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "启用状态", allowableValues = "0,1")
	private String status;

}
