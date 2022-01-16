/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "InformSendVo", description = "消息通知发送对象")
public class InformSendVo {

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
	 * 消息通知类型：（notice：通知、direct：私信）
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "消息通知类型：（notice：通知、letter：私信）", allowableValues = "notice,letter")
	private String type;
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
	
}