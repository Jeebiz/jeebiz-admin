/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzThirdpartyPaginationVo", description = "第三方登录分页查询参数Vo")
@Getter
@Setter
@ToString
public class AuthzThirdpartyPaginationVo extends AbstractPaginationVo {
	

}
