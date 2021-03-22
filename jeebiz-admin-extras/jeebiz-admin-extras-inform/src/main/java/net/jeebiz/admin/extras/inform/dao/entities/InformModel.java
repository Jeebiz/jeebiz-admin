/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

@SuppressWarnings("serial")
@Alias(value = "InformModel")
public class InformModel extends PaginationModel {

	/**
	 * 消息通知ID
	 */
	private List<String> ids;
	/**
	 * 消息通知对象ID
	 */
	private String userId;
	/**
	 * 消息通知类型：（notice：通知、direct：私信）
	 */
	private String type;
	/**
	 * 通知信息标题
	 */
	private String title;
	/**
	 * 通知信息内容
	 */
	private String detail;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	private String status;
	/**
	 * 通知信息送达时间
	 */
	private String time24;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

}
