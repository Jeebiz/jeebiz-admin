package io.hiwepy.admin.extras.article.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTopicRenewDTO", description = "文章栏目编辑参数DTO")
@Data
public class ArticleTopicRenewDTO {

	/**
	 * 文章栏目id
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "文章栏目id")
	 @NotBlank(message = "文章编辑时文章id不能为空")
	private String id;
	/**
	 * 上级文章栏目id
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章栏目id")
	private String pid;
	/**
	 * 文章栏目名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "文章栏目名称")
	@NotBlank(message = "文章栏目名称不能为空")
	private String name;
	/**
	 * 文章分类id
	 */
	@ApiModelProperty(name = "cid", required = true, dataType = "String", value = "文章分类id")
	@NotBlank(message = "文章栏目分类不能为空")
	private String cid;
	/**
	 * 文章栏目备注
	 */
	@ApiModelProperty(name = "remark", required = true, dataType = "String", value = "文章栏目备注")
	private String remark;
	/**
	 * 文章栏目状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "Integer", value = "文章栏目状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章栏目排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "Integer", value = "文章栏目排序")
	private Integer order;
	
}
