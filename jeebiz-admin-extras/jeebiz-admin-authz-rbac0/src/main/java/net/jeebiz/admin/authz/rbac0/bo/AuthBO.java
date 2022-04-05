package net.jeebiz.admin.authz.rbac0.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.authz.rbac0.setup.strategy.AuthChannel;

@ApiModel(value = "AuthBO", description = "文件存储BO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuthBO {

	/**
	 * 支付调试（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
	 */
	@ApiModelProperty(name = "debug", hidden =  true, value = "支付调试")
	private boolean debug;
	/**
	 * 应用ID
	 */
	@ApiModelProperty(name = "appId", required = true, value = "应用ID")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@ApiModelProperty(name = "appChannel", required = true, value = "应用渠道编码")
	private String appChannel;
	/**
	 * 应用版本号
	 */
	@ApiModelProperty(name = "appVer", required = true, value = "应用版本号")
	private String appVer;
	/**
	 * 文件存储来源IP地址
	 */
	@ApiModelProperty(name = "ipAddress", required = true, value = "文件存储来IP地址")
	private String ipAddress;
	/**
	 * 文件存储渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "文件存储渠道")
	private AuthChannel channel;

}
