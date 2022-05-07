package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCommentNewDTO", description = "文章评新增参数DTO")
@Data
public class ArticleCommentNewDTO {
    
	/**
	 * 文章id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章id")
	private String cid;
	/**
	 * 上级文章评论id
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章评论id")
	private String pid;
	/**
	 * 文章评论类型
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "文章评论类型")
	private String type;
	/**
	 * 文章评论内容
	 */
	@ApiModelProperty(name = "text", dataType = "String", value = "文章评论内容")
	private String text;

}
