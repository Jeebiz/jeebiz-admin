package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCategoryVo", description = "文章分类参数Vo")
@Data
public class ArticleCategoryVo {

	/**
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章分类ID")
	private String id;
	/**
	 * 文章分类名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章分类名称")
	private String name;
	/**
	 * 文章分类等级
	 */
	@ApiModelProperty(name = "grade", dataType = "Integer", value = "文章分类等级")
	private Integer grade;
	/**
	 * 文章分类简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "文章分类简介")
	private String intro;
	/**
	 * 文章分类关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "文章分类关键字")
	private String keywords;
	/**
	 * 文章分类排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章分类排序")
	private Integer order;
	/**
	 * 文章分类创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章分类创建时间")
	private String time24;
	/**
	 * 文章分类创建者ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章栏目创建者ID")
	private String uid;
	/**
	 * 文章分类创建者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章栏目创建者姓名")
	private String uname;

}