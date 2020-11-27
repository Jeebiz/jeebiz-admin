package net.jeebiz.admin.extras.core.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.annotation.AllowableValues;

@ApiModel(value = "InformSendDTO", description = "消息通知发送对象")
public class InformSendDTO {

	/**
	 * 消息通知通知对象ID（钉钉UserId）
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "消息通知通知对象ID（钉钉UserId）")
	private String userId;
	/**
	 * 消息通知标题
	 */
	@ApiModelProperty(name = "title", required = true, dataType = "String", value = "消息通知标题")
	@NotBlank(message = "消息通知标题必填")
	
	private String title;
	/**
	 * 消息通知类型：（1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "String", value = "消息通知类型：（0：提交发布、1：提交审批、2：审批通过、3：审批拒绝、4：发布成功、5：发布失败）", allowableValues = "1,2,3,4")
	
	@AllowableValues(allows = "0,1,2,3,4,5",message = "消息通知类型错误")
	private String type;
	/**
	 * 通知信息关联数据载体,JOSN格式的数据
	 */
	@ApiModelProperty(name = "payload", dataType = "String", value = "通知信息关联数据载体,JOSN格式的数据")
	
	private String payload;
	/**
	 * 消息通知内容
	 */
	@ApiModelProperty(name = "detail", required = true, dataType = "String", value = "消息通知内容")
	@NotBlank(message = "消息通知内容必填")
	
	private String detail;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
