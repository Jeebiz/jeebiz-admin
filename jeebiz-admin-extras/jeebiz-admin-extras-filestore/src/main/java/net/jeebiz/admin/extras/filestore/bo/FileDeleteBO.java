package net.jeebiz.admin.extras.filestore.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDeleteBO", description = "文件删除BO")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteBO extends FileStoreBO {
	
	/**
	 * 要删除文件的路径
	 */
	@ApiModelProperty(name = "paths", value = "要删除文件的路径")
	private List<String> paths;
	/**
	 * 要删除文件的uuid
	 */
	@ApiModelProperty(name = "uuids", value = "要删除文件的uuid")
	private List<String> uuids;
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
	 * 发送短信来源IP地址
	 */
	@ApiModelProperty(name = "ipAddress", required = true, value = "发送短信来IP地址")
	private String ipAddress;
	/**
	 * 短信流水号
	 */
	@ApiModelProperty(name = "ipAddress", hidden = true, value = "短信流水号")
	private String smsNo;
	/**
	 * 唤起发送短信的用户uid
	 */
	@ApiModelProperty(name = "userId", required = true, value = "唤起发送短信的用户uid")
	private String userId;
	/**
	 * 业务ID
	 */
	@ApiModelProperty(name = "bizId", required = true, value = "业务ID")
	private String bizId;
	/**
	 * 国家编码，例：86
	 */
	@ApiModelProperty(name = "countryCode", required = true, value = "国家编码，例：86")
	private Integer countryCode;
	/**
	 * 短信接收号码
	 */
	@ApiModelProperty(name = "phone", required = true, value = "短信接收号码")
	private String phone; 
	/**
	 * 短信验证码
	 */
	@ApiModelProperty(name = "vcode", required = true, value = "短信验证码")
	private String vcode;
	/**
	 * 发送短信渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "发送短信渠道")
	private FilestoreChannel channel;

}
