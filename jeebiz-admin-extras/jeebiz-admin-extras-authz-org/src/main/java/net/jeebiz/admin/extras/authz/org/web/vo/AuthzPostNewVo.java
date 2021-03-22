package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzPostNewVo", description = "新增岗位信息参数Vo")
public class AuthzPostNewVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", required = true, dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 岗位编码
	 */
	@ApiModelProperty(name = "code", required = true, dataType = "String", value = "岗位编码")
	private String code;
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "岗位名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 岗位简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "岗位简介")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String intro;
 

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
