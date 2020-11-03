package net.jeebiz.admin.shadow.web.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.boot.api.annotation.Phone;

@ApiModel(value = "SmsSendDTO", description = "短信发送DTO")
@Data
public class SmsSendDTO {
	
	/**
	 * 国家编码
	 */
	@ApiModelProperty(name = "countryCode", required = true, dataType = "Integer", value = "国家编码")
	@NotNull(message = "{sms.send.countryCode.required}")
	@Min(value = 1, message = "{sms.send.countryCode.required}")
	private Integer countryCode;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	@NotBlank(message = "{sms.send.phone.required}")
	@Phone(message = "{sms.send.phone.invalid}")
	private String phone;
	/**
	 * 业务类型：（0：注册、1：绑定、2：找回密码、3：忘记密码、4：手机号码登录）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "Integer", value = " 业务类型：（0：注册、1：绑定、2：修改密码、3：忘记密码、4：手机号码登录）", allowableValues = "0,1,2,3,4")
	@NotNull(message = "{sms.send.type.required}")
	private Integer type;

}
