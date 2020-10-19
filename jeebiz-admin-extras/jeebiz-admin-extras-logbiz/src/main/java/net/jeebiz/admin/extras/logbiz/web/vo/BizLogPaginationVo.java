/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

/**
 * 功能操作数据筛选条件Vo
 */
@ApiModel(value = "BizLogPaginationVo", description = "功能操作数据筛选条件Vo")
@Getter
@Setter
@ToString
public class BizLogPaginationVo extends AbstractPaginationVo {

	/**
	 * 功能模块
	 */
	@ApiModelProperty(name = "module", dataType = "String", value = "功能模块")
	private String module;
	/**
	 * 业务名称
	 */
	@ApiModelProperty(name = "business", dataType = "String", value = "业务名称")
	private String business;
	/**
	 * 操作类型
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "操作类型", allowableValues = "login,logout,insert,delete,update,select,upload,download")
	
	private String opt;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）" , allowableValues = "debug,info,warn,error,fetal")
	private String level;
	/**
	 * 功能操作请求来源IP地址
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "功能操作请求来源IP地址")
	private String addr;
	/**
	 * 功能操作发生起始时间
	 */
	@ApiModelProperty(name = "begintime", dataType = "String", value = "功能操作发生起始时间")
	private String begintime;
	/**
	 * 功能操作发生结束时间
	 */
	@ApiModelProperty(name = "endtime", dataType = "String", value = "功能操作发生结束时间")
	private String endtime;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "模糊搜索关键字")
	private String keywords;

}
