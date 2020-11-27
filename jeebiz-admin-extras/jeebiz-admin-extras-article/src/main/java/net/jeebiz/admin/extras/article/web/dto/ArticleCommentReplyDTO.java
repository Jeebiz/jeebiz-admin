package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCommentReplyDTO", description = "文章评回复参数DTO")
@Data
public class ArticleCommentReplyDTO {
    
	/**
	 * 文章ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章ID")
	private String cid;
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
