package io.hiwepy.admin.extras.article.web.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCategoryVo", description = "文章分类Vo")
@Data
public class ArticleCategoryVo {

	/**
	 * 文章分类id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章分类id")
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
	 * 文章分类状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "Integer", value = "文章分类状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章分类排序
	 */
	@ApiModelProperty(name = "orderBy", dataType = "Integer", value = "文章分类排序")
	private Integer orderBy;
	/**
	 * 文章分类创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章分类创建时间")
	@JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
	private LocalDateTime time24;
	/**
	 * 文章分类创建者id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章栏目创建者id")
	private String uid;
	/**
	 * 文章分类创建者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章栏目创建者姓名")
	private String uname;

}