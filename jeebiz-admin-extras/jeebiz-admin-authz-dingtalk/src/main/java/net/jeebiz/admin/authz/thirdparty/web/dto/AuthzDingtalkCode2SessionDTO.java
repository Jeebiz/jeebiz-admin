package net.jeebiz.admin.authz.login.web.dto;

import com.dingtalk.api.response.OapiUserGetResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzDingtalkCode2SessionDTO", description = "钉钉小程序codeToSession返回传输对象实体")
public class AuthzDingtalkCode2SessionDTO {

	/**
	 * 第三方平台Unionid（通常指第三方账号体系下用户的唯一id）
	 */
	@ApiModelProperty(name = "unionid", dataType = "String", value = "第三方平台Unionid（通常指第三方账号体系下用户的唯一id）")
	private String unionid;
	/**
	 * 第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）
	 */
	@ApiModelProperty(name = "openid", dataType = "String", value = "第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）")
	private String openid;
	/** 
	 * 认证登录设备id
	 */
	@ApiModelProperty(name = "deviceId", dataType = "String", value = "认证登录设备id")
	private String deviceId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "userid", dataType = "String", value = "用户id")
	private String userid;
	/**
	 * 用户信息
	 */
	@ApiModelProperty(name = "userInfo", dataType = "String", value = "用户信息")
	private OapiUserGetResponse userInfo;
	/**
	 * 第三方账号绑定状态
	 */
	@ApiModelProperty(name = "bind", dataType = "Boolean", value = "第三方账号绑定状态")
	private boolean bind;
	
}
