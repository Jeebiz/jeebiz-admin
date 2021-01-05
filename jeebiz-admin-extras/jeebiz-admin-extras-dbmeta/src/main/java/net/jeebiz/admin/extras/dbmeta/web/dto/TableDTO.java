package net.jeebiz.admin.extras.dbmeta.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "TableDTO", description = "表基本信息DTO")
@Data
public class TableDTO {

	/**
	 * 表名
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "表名")
	private String name;
	/**
	 * 备注，说明
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "备注，说明备注，说明")
	private String remark;
	
	public TableDTO(String name, String remark) {
		this.name = name;
		this.remark = remark;
	}

}
