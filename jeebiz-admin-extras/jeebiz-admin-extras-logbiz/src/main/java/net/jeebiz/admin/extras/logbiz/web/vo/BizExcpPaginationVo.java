/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

/**
 * 系统异常数据筛选条件Vo
 */
@ApiModel(value = "BizExcpPaginationVo", description = "系统异常数据筛选条件Vo")
public class BizExcpPaginationVo extends AbstractPaginationVo {

	/**
	 * 系统异常类型
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "异常类型")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String type;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）" , allowableValues = "debug,info,warn,error,fetal")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String level;
	/**
	 * 系统异常发生起始时间
	 */
	@ApiModelProperty(name = "begintime", dataType = "String", value = "系统异常发生起始时间")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String begintime;
	/**
	 * 系统异常发生结束时间
	 */
	@ApiModelProperty(name = "endtime", dataType = "String", value = "系统异常发生结束时间")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String endtime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
