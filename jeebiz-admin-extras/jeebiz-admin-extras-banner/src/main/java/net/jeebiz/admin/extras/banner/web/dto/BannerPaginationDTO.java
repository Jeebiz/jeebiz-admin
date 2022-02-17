package net.jeebiz.admin.extras.banner.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

/**
 * 我的应用信息
 */
@ApiModel(value = "BannerPaginationDTO", description = "我的应用信息分页查询DTO")
public class BannerPaginationDTO extends AbstractPaginationDTO {

	/** 应用名称 */
	@ApiModelProperty(value = "name", required = true, dataType = "String", notes = "应用名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
