package io.hiwepy.admin.authz.org.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "PostNewDTO", description = "新增岗位信息参数DTO")
@Getter
@Setter
@ToString
public class PostNewDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构id编号")
	private String orgId;
	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "deptId", required = true, dataType = "String", value = "部门id编号")
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
	
	private String name;
	/**
	 * 岗位简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "岗位简介")
	
	private String intro;
 
}
