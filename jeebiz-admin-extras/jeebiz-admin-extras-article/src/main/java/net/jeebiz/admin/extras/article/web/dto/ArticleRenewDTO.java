package net.jeebiz.admin.extras.article.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("文章编辑DTO")
@Data
public class ArticleRenewDTO {
	
	/**
	 * 文章D
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "文章ID")
	private String id;
	/**
	 * 文章栏目ID
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章栏目ID")
	private String tid;
	/**
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "cid", required = true, dataType = "String", value = "文章分类ID")
	private String cid;
	/**
	 * 文章所属单位ID/编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "文章所属单位ID/编号")
	private String orgId;
	/**
	 * 文章标题
	 */
	@ApiModelProperty(name = "title", required = true, dataType = "String", value = "文章标题")
	private String title;
	/**
	 * 文章摘要
	 */
	@ApiModelProperty(name = "digest", required = true, dataType = "String", value = "文章摘要")
	private String digest;
	/**
	 * 文章内容
	 */
	@ApiModelProperty(name = "content", required = true, dataType = "String", value = "文章内容")
	private String content;
	/**
	 * 文章是否推荐（0:未推荐|1:推荐）
	 */
	@ApiModelProperty(name = "recommend", required = true, dataType = "Integer", value = "文章是否推荐（0:未推荐|1:推荐）")
	private Integer recommend;
	/**
	 * 文章状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "Integer", value = "文章状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "Integer", value = " 文章排序")
	private Integer order;
	/**
	 * 文章发布对象
	 */
	@ApiModelProperty(name = "targets", dataType = "java.util.List<ArticleTargetRenewDTO>", value = "文章发布对象")
	private List<ArticleTargetRenewDTO> targets;
	/**
	 * 文章封面
	 */
	@ApiModelProperty(name = "cover", dataType = "ArticleAttachmentNewDTO", value = "文章封面")
	private ArticleAttachmentNewDTO cover;
	/**
	 * 文章附件
	 */
	@ApiModelProperty(name = "atts", dataType = "java.util.List<ArticleAttachmentNewDTO>", value = "文章附件")
	private List<ArticleAttachmentNewDTO> atts;
	
}
