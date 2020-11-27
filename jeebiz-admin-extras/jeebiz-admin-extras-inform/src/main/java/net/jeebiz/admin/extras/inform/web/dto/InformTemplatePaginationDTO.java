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
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.InformTarget;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "InformTemplatePaginationDTO", description = "消息通知模板分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformTemplatePaginationDTO extends AbstractPaginationDTO {

	/**
	 * 消息通知推送对象
	 */
	@ApiModelProperty(name = "target", dataType = "InformTarget", value = "消息通知推送对象")
	private InformTarget target;
	/**
	 * 发送该模板消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", dataType = "InformProvider", value = "消息通知类型")
	private InformProvider provider;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "模糊搜索关键字")
	private String keywords;
	
}
