/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SettingsGroupRenewVo", description = "系统参数集合传输对象")
public class SettingsGroupRenewVo {

	/**
	 * 系统参数分组
	 */
	@ApiModelProperty(name = "gkey", required = true, dataType = "String", value = "系统参数分组")
	@NotBlank(message = "系统参数分组必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String gkey;
	
	@ApiModelProperty(name = "datas", required = true, dataType = "java.util.List<SettingsRenewVo>", value = "批量更新的系统参数列表")
	private List<SettingsRenewVo> datas;

	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}

	public List<SettingsRenewVo> getDatas() {
		return datas;
	}

	public void setDatas(List<SettingsRenewVo> datas) {
		this.datas = datas;
	}
	
}
