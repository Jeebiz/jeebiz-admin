/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "AuthzThirdpartyPaginationDTO", description = "第三方登录分页查询参数DTO")
@Getter
@Setter
@ToString
public class AuthzThirdpartyPaginationDTO extends AbstractPaginationDTO {
	

}
