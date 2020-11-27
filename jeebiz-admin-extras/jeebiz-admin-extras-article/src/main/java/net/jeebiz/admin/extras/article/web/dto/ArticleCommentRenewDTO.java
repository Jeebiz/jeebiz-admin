package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCommentRenewDTO", description = "文章评论编辑参数DTO")
@Data
public class ArticleCommentRenewDTO {
    
	/**
	 * 文章评论ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章评论ID")
	private String id;
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
	 * 文章评论者ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章评论者ID")
	private String uid;
	/**
	 * 文章评论者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章评论者姓名")
	private String uname;
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
	/**
	 * 文章评论审核状态（0:未通过|1:通过）
	 */
	@ApiModelProperty(name = "review", dataType = "Integer", value = "文章评论审核状态（0:未通过|1:通过）")
	private Integer review;
	/**
	 * 文章评论状态（0:删除|1:正常）
	 */
	@ApiModelProperty(name = "status", dataType = "Integer", value = "文章评论审核状态（0:未通过|1:通过）")
	private Integer status;
	/**
	 * 文章评论推荐（0:未推荐|1:推荐）
	 */
	@ApiModelProperty(name = "recommend", dataType = "Integer", value = "文章评论推荐（0:未推荐|1:推荐）")
	private Integer recommend;
	/**
	 * 文章评论排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章评论排序")
	private Integer order;
	/**
	 * 文章评论时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章评论时间")
	private String time24;

}
