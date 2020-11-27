/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "InformRecordPaginationDTO", description = "消息通知分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformRecordPaginationDTO extends AbstractPaginationDTO {

	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "消息通知阅读状态：（0:未阅读、1:已阅读）", allowableValues = "0,1")
	@AllowableValues(allows = "0,1", message = "阅读状态：（0:未阅读、1:已阅读）", nullable = true)
	private String status;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "模糊搜索关键字")
	private String keywords;
	
}
