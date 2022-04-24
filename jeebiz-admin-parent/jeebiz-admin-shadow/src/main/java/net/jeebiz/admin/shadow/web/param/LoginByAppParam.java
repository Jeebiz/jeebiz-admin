package net.jeebiz.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 */
@Data
public class LoginByAppParam {
	
	/**
	 * 登陆渠道 “google” “line” “facebook” “wechat”
	 */
	@ApiModelProperty(name = "channel", required = true, value = "登陆渠道 “google” “line” “facebook” “wechat”")
	private String channel;

	/**
	 * 3方令牌
	 */
	@ApiModelProperty(name = "accessToken", required = true, value = "3方令牌")
	private String accessToken;

	/**
	 * 3方账号
	 */
	@ApiModelProperty(value = "3方账号", required = false)
	private String unionid;
}
