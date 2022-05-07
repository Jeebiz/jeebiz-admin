package io.hiwepy.admin.extras.article.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCategoryRenewDTO", description = "文章分类新增参数DTO")
@Data
public class ArticleCategoryNewDTO {

	/**
	 * 文章分类名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "文章分类名称")
	@NotBlank(message = "文章分类名称不能为空")
	private String name;
	/**
	 * 文章分类等级
	 */
	@ApiModelProperty(name = "grade", required = true, dataType = "Integer", value = "文章分类等级")
	private Integer grade;
	/**
	 * 文章分类简介
	 */
	@ApiModelProperty(name = "intro", required = true, dataType = "String", value = "文章分类简介")
	@NotBlank(message = "文章分类简介不能为空")
	private String intro;
	/**
	 * 文章分类关键字
	 */
	@ApiModelProperty(name = "keywords", required = true, dataType = "String", value = "文章分类关键字")
	@NotBlank(message = "文章分类关键字不能为空")
	private String keywords;
	/**
	 * 文章分类排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章分类排序")
	private Integer order;

}
