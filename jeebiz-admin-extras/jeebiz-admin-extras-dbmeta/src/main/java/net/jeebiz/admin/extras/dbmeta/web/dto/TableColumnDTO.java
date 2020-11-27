package net.jeebiz.admin.extras.dbmeta.web.dto;

public class TableColumnDTO {

	/**
	 * 列名
	 */
	private String name;
	/**
	 * 备注，说明
	 */
	private String remark;
	/**
	 * 表中列的顺序位置，从1开始
	 */
	private int ordinal = 1;
	/**
	 * 数据类型
	 */
	private String type;
	/**
	 * 长度限制
	 */
	private int size;
	/**
	 * 默认值
	 */
	private String defaultValue;

	public TableColumnDTO(String name, String remark, int ordinal, String type, int size, String defaultValue) {
		this.name = name;
		this.remark = remark;
		this.ordinal = ordinal;
		this.type = type;
		this.size = size;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
