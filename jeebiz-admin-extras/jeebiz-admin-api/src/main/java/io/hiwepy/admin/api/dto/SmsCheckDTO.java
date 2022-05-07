package io.hiwepy.admin.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.hiwepy.boot.api.annotation.Phone;

@ApiModel(value = "SmsCheckDTO", description = "短信验证DTO")
@Data
public class SmsCheckDTO {
	
	/**
	 * 城市编码
	 */
	@ApiModelProperty(name = "countryCode", required = true, dataType = "Integer", value = "城市编码")
	@NotBlank(message = "{sms.check.countryCode.required}")
	private Integer countryCode;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	@NotBlank(message = "{sms.check.phone.required}")
	@Phone(message = "{sms.check.phone.invalid}")
	private String phone;
	/**
	 * 业务类型：（0：注册、1：绑定、2：找回密码、3：忘记密码、4：手机号码登录）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "Integer", value = " 业务类型：（0：注册、1：绑定、2：找回密码、3：忘记密码、4：手机号码登录）", allowableValues = "0,1,2,3,4")
	@NotNull(message = "{sms.check.type.required}")
	private Integer type;
	/**
	 * 验证码
	 */
	@ApiModelProperty(name = "vcode", required = true, dataType = "String", value = "验证码")
	@NotBlank(message = "{sms.check.vcode.required}")
	private String vcode;
	
}
