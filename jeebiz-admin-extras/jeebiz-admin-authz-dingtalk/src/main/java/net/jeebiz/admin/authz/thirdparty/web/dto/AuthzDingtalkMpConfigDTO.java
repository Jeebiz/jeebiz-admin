package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzDingtalkMpConfigDTO", description = "钉钉微应用配置对象DTO")
public class AuthzDingtalkMpConfigDTO {

	/**
	 * 应用的标识
	 */
	@ApiModelProperty(name = "agentId", dataType = "String", value = "应用的标识")
	private String agentId;
	/**
	 * 当前网页的URL，不包含#及其后面部分
	 */
	@ApiModelProperty(name = "url", dataType = "String", value = "当前网页的URL，不包含#及其后面部分")
	private String url;
	/**
	 * 随机串，自己定义
	 */
	@ApiModelProperty(name = "nonceStr", dataType = "String", value = "随机串")
	private String nonceStr;
	/**
	 * 时间戳：当前时间，但是前端和服务端进行校验时候的值要一致
	 */
	@ApiModelProperty(name = "timestamp", dataType = "String", value = "时间戳")
	private long timestamp;
	/**
	 * 企业id，在开发者后台中企业视图下开发者账号设置里面可以看到
	 */
	@ApiModelProperty(name = "corpId", dataType = "String", value = "企业id")
	private String corpId;
	/**
	 * 签名信息
	 */
	@ApiModelProperty(name = "signature", dataType = "String", value = "签名信息")
	private String signature;
	
}
