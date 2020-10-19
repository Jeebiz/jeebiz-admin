package net.jeebiz.admin.extras.dbmeta.web.vo;

public class TableVo {

	/**
	 * 表名
	 */
	private String name;
	/**
	 * 备注，说明
	 */
	private String remark;
	
	public TableVo(String name, String remark) {
		this.name = name;
		this.remark = remark;
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


}
