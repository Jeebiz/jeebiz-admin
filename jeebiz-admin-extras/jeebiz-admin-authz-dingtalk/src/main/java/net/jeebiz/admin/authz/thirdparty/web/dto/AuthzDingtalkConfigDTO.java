package net.jeebiz.admin.authz.thirdparty.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzDingtalkConfigDTO", description = "钉钉小程序配置对象DTO")
public class AuthzDingtalkConfigDTO {

	/**
	 * 应用的标识
	 */
	private String agentId;
	/**
	 * 当前网页的URL，不包含#及其后面部分
	 */
	private String url;
	/**
	 * 随机串，自己定义
	 */
	private String nonceStr;
	/**
	 * 时间戳：当前时间，但是前端和服务端进行校验时候的值要一致
	 */
	private long timestamp;
	/**
	 * 企业ID，在开发者后台中企业视图下开发者账号设置里面可以看到
	 */
	@ApiModelProperty(name = "corpId", dataType = "String", value = "企业ID")
	private String corpId;
	/**
	 * 签名信息
	 */
	private String signature;
	
}
