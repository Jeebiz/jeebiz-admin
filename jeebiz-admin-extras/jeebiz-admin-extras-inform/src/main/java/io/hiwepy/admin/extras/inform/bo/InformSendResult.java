package io.hiwepy.admin.extras.inform.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

@ApiModel(value = "InformSendResult", description = "推送结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class InformSendResult {
	
	/**
	 * 唤起推送的用户uid
	 */
	@ApiModelProperty(name = "userId", value = "唤起推送的用户uid", hidden = true)
	private String userId;
	/**
	 * 推送类型（1：正常推送，2：失败重试）
	 */
	@ApiModelProperty(name = "sendType", value = "推送类型（1：正常推送，2：失败重试）")
	private Integer sendType;
	/**
	 * 推送渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "推送渠道")
	private InformSendChannel channel;
	/**
	 * 推送状态（ 0：任务创建、1：推送成功、2、推送失败、 3：推送撤销）
	 */
	@ApiModelProperty(name = "status", value = "推送状态（ 0：任务创建、1：推送成功、2、推送失败、 3：推送撤销）")
	private Integer status;
	/**
	 * 推送描述
	 */
	@ApiModelProperty(name = "desc", value = "推送描述")
	private String desc;
	
}
