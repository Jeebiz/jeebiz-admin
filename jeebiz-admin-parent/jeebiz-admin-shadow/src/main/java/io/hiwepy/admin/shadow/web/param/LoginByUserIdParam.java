package io.hiwepy.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户ID登录参数
 */
@Data
public class LoginByUserIdParam {
	/**
	 * 用户ID
	 */
	@ApiModelProperty(name = "userId", required = true, value = "用户ID")
	private Long userId;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", required = true, value = "角色ID")
	private String roleId;
	
}
