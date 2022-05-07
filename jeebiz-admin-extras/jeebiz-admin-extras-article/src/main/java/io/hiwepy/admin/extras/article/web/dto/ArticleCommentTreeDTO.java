package io.hiwepy.admin.extras.article.web.dto;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCommentTreeDTO", description = "文章评论树DTO")
@Data
public class ArticleCommentTreeDTO {

	/**
	 * 文章评论id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章评论id")
	private String id;
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
	 * 文章评论者id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章评论者id")
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
	 * 文章评论推荐（0:未推荐|1:推荐）
	 */
	@ApiModelProperty(name = "review", dataType = "Integer", value = "文章评论推荐（0:未推荐|1:推荐）")
	private Integer review;
	/**
	 * 文章评论审核状态（0:未通过|1:通过）
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
	/**
	 * 评论回复
	 */
	@ApiModelProperty(name = "children", dataType = "String", value = "评论回复")
	private List<ArticleCommentTreeDTO> children = Lists.newArrayList();

}