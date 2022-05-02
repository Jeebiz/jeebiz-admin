package net.jeebiz.admin.shadow.web.param;

import org.jasig.cas.client.validation.Assertion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Cas登录参数
 */
@Data
public class LoginByCasParam {
	
	/**
	 * assertion
	 */
	@ApiModelProperty(name = "assertion", required = true, value = "Cas 携带的信息")
	private Assertion assertion;

}
