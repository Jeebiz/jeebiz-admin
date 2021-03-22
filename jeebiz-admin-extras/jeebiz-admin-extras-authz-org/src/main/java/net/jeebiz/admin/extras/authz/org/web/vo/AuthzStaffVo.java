package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzStaffVo", description = "员工信息Vo")
public class AuthzStaffVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "orgName", dataType = "String", value = "机构名称")
	private String orgName;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "deptName", dataType = "String", value = "部门名称")
	private String deptName;
	/**
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", dataType = "String", value = "岗位ID编号")
	private String postId;
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(name = "postName", dataType = "String", value = "岗位名称")
	private String postName;
	/**
	 * 员工ID编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "员工ID编号")
	private String id;
	/**
	 * 员工简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "员工简介")
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "员工状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 员工入职时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "员工入职时间")
	private String time24;

	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", dataType = "String", value = "用户别名（昵称）")
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", dataType = "String", value = "用户名")
	private String username;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", dataType = "String", value = "手机号码")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", dataType = "String", value = "电子邮箱")
	private String email;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
