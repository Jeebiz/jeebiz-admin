package net.jeebiz.admin.extras.filestore.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDeleteResult", description = "文件删除结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteResult {
	
	
	/**
	 * 业务ID
	 */
	@ApiModelProperty(name = "bizId", value = "业务ID")
	private String bizId;
	/**
	 * 请求ID
	 */
	@ApiModelProperty(name = "requestId", value = "请求ID")
	private String requestId;
	/**
	 * 发送短信渠道
	 */
	@ApiModelProperty(name = "channel", value = "发送短信渠道")
	private FilestoreChannel channel;
	/**
	 * 短信发送状态（ 0：发送失败、1：发送成功）
	 */
	@ApiModelProperty(name = "status", value = "短信发送状态（ 0：发送失败、1：发送成功）")
	private Integer status;
	/**
	 * 发送短信内容
	 */
	@ApiModelProperty(name = "content", value = "发送短信内容")
	private String message;
	 
}

