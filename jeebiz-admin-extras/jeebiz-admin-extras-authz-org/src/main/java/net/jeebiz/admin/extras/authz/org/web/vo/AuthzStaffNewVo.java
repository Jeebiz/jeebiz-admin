package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzStaffNewVo", description = "新增员工信息参数Vo")
public class AuthzStaffNewVo implements Serializable {

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
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", required = true, dataType = "String", value = "岗位ID编号")
	private String postId;
	/**
	 * 员工ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "员工ID编号")
	private String id;
	/**
	 * 员工简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "员工简介")
	private String intro;
	/**
	 * 员工入职时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "员工入职时间")
	private String time24;

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

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

}
