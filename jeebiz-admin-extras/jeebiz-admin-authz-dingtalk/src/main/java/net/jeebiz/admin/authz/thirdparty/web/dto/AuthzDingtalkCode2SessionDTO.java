package net.jeebiz.admin.authz.thirdparty.web.dto;

import com.dingtalk.api.response.OapiUserGetResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzDingtalkCode2SessionDTO", description = "钉钉小程序codeToSession返回传输对象实体")
public class AuthzDingtalkCode2SessionDTO {

	/**
	 * 第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）
	 */
	@ApiModelProperty(name = "unionid", dataType = "String", value = "第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）")
	private String unionid;
	/**
	 * 第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）
	 */
	@ApiModelProperty(name = "openid", dataType = "String", value = "第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）")
	private String openid;
	/** 
	 * 认证登录设备ID
	 */
	@ApiModelProperty(name = "deviceId", dataType = "String", value = "认证登录设备ID")
	private String deviceId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(name = "userid", dataType = "String", value = "用户ID")
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
