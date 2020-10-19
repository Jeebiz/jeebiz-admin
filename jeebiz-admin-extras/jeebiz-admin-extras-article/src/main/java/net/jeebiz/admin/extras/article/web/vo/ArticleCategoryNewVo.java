package net.jeebiz.admin.extras.article.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCategoryRenewVo", description = "文章分类新增参数Vo")
@Data
public class ArticleCategoryNewVo {

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
