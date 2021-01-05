package net.jeebiz.admin.extras.dbmeta.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "TableColumnDTO", description = "表列基本信息DTO")
@Data
public class TableColumnDTO {

	/**
	 * 列名
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "列名")
	private String name;
	/**
	 * 备注，说明
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "备注，说明")
	private String remark;
	/**
	 * 表中列的顺序位置，从1开始
	 */
	@ApiModelProperty(name = "ordinal", dataType = "Integer", value = "表中列的顺序位置，从1开始")
	private int ordinal = 1;
	/**
	 * 数据类型
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "数据类型")
	private String type;
	/**
	 * 长度限制
	 */
	@ApiModelProperty(name = "size", dataType = "Integer", value = "长度限制")
	private int size;
	/**
	 * 默认值
	 */
	@ApiModelProperty(name = "defaultValue", dataType = "String", value = "默认值")
	private String defaultValue;

	public TableColumnDTO(String name, String remark, int ordinal, String type, int size, String defaultValue) {
		this.name = name;
		this.remark = remark;
		this.ordinal = ordinal;
		this.type = type;
		this.size = size;
		this.defaultValue = defaultValue;
	}

}
