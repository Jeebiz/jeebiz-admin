package net.jeebiz.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 */
@Data
public class LoginByQrcodeParam {
	
	/**
	 * 二维码UUID
	 */
	@ApiModelProperty(required = true, value = "二维码UUID")
	protected String uuid;
 
}
