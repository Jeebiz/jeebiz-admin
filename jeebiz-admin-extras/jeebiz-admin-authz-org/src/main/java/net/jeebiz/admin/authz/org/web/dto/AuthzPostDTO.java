package net.jeebiz.admin.authz.org.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzPostDTO", description = "岗位信息DTO")
@Getter
@Setter
@ToString
public class AuthzPostDTO implements Serializable {

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
	@ApiModelProperty(name = "id", dataType = "String", value = "岗位ID编号")
	private String id;
	/**
	 * 岗位编码
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = "岗位编码")
	private String code;
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "岗位名称")
	
	private String name;
	/**
	 * 岗位简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "岗位简介")
	private String intro;
	/**
	 * 岗位创建人ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "岗位创建人ID")
	private String uid;
	/**
	 * 岗位状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "岗位状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 岗位创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "岗位创建时间")
	private String time24;

}
