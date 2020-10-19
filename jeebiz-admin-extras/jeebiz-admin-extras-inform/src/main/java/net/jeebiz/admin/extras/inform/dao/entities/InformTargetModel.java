/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@SuppressWarnings("serial")
@Alias(value = "InformTargetModel")
@Getter
@Setter
@ToString
public class InformTargetModel extends BaseModel<InformTargetModel> {

	/**
	 * 消息通知模板ID
	 */
	private String tid;
	/**
	 * 消息通知接收人ID
	 */
	private String uid;
	/**
	 * 消息通知发送状态：（0:待发送、1:已发送）
	 */
	private String status;
	/**
	 * 消息通知发送时间
	 */
	private String time24;
	
}
