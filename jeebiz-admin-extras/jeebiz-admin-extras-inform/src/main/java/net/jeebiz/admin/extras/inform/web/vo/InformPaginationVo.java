/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "InformPaginationVo", description = "消息通知分页查询参数Vo")
public class InformPaginationVo extends AbstractPaginationVo {

	/**
	 * 消息通知类型
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "消息通知类型")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
