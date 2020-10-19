package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCommentNewVo", description = "文章评新增参数Vo")
@Data
public class ArticleCommentNewVo {
    
	/**
	 * 文章ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章ID")
	private String cid;
	/**
	 * 上级文章评论ID
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章评论ID")
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
