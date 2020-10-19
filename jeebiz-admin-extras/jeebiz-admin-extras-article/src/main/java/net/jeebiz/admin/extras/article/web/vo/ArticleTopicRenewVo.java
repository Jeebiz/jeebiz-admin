package net.jeebiz.admin.extras.article.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTopicRenewVo", description = "文章栏目编辑参数Vo")
@Data
public class ArticleTopicRenewVo {

	/**
	 * 文章栏目ID
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "文章栏目ID")
	 @NotBlank(message = "文章编辑时文章id不能为空")
	private String id;
	/**
	 * 上级文章栏目ID
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章栏目ID")
	private String pid;
	/**
	 * 文章栏目名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "文章栏目名称")
	@NotBlank(message = "文章栏目名称不能为空")
	private String name;
	/**
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "cid", required = true, dataType = "String", value = "文章分类ID")
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
