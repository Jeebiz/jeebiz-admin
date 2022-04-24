package net.jeebiz.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Token自动登录参数
 */
@Data
public class LoginByAutoParam {

	@ApiModelProperty(name = "userId", required = true, value = "自动登录账号ID")
    private Long userId;
	
}
