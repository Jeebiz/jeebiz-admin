package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetDTO", description = "文章发布对象参数DTO")
@Data
public class ArticleTargetDTO {
	
	/**
	 * 文章发布对象记录id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章发布对象记录id")
	private String id;
	/**
	 * 文章id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章id")
	private String cid;
	/**
	 * 文章发布对象id（学院id|专业id|班级id|账户id）
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "文章发布对象id（学院id|专业id|班级id|账户id）")
	private String tid;
	/**
	 * 文章发布对象名称（学院|专业|班级|账户）
	 */
	@ApiModelProperty(name = "tname", dataType = "String", value = "文章发布对象名称（学院|专业|班级|账户）")
	private String tname;
	
}