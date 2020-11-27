package net.jeebiz.admin.extras.article.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleDTO", description = "文章内容DTO")
@Data
public class ArticleDTO {

	/**
	 * 文章D
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章ID")
	private String id;
	/**
	 * 文章栏目ID
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "文章栏目ID")
	private String tid;
	/**
	 * 文章栏目名称
	 */
	@ApiModelProperty(name = "tname", dataType = "String", value = "文章栏目名称")
	private String tname;
	/**
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章分类ID")
	private String cid;
	/**
	 * 文章分类名称
	 */
	@ApiModelProperty(name = "cname", dataType = "String", value = "文章分类名称")
	private String cname;
	/**
	 * 文章发布者ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章发布者ID")
	private String uid;
	/**
	 * 文章发布者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章发布者姓名")
	private String uname;
	/**
	 * 文章标题
	 */
	@ApiModelProperty(name = "title", dataType = "String", value = "文章标题")
	private String title;
	/**
	 * 文章摘要
	 */
	@ApiModelProperty(name = "digest", dataType = "String", value = "文章摘要")
	private String digest;
	/**
	 * 文章内容
	 */
	@ApiModelProperty(name = "content", dataType = "String", value = "文章内容")
	private String content;
	/**
	 * 文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）
	 */
	@ApiModelProperty(name = "review", dataType = "Integer", value = "文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）")
	private Integer review;
	/**
	 * 文章状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "Integer", value = "文章状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章是否推荐（0:未推荐|1:推荐）
	 */
	@ApiModelProperty(name = "recommend", dataType = "Integer", value = "文章是否推荐（0:未推荐|1:推荐）")
	private Integer recommend;
	/**
	 * 文章排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章排序")
	private Integer order;
	/**
	 * 文章浏览数
	 */
	@ApiModelProperty(name = "browse", dataType = "Integer", value = "文章浏览数")
	private Integer browse;
	/**
	 * 文章收藏数
	 */
	@ApiModelProperty(name = "collect", dataType = "Integer", value = "文章收藏数")
	private Integer collect;
	/**
	 * 文章点赞数
	 */
	@ApiModelProperty(name = "liked", dataType = "Integer", value = "文章点赞数")
	private Integer liked;
	/**
	 * 文章发布时间
	 */
	@ApiModelProperty(name = "ptime24", dataType = "String", value = "文章发布时间")
	private String ptime24;
	/**
	 * 文章创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章创建时间")
	private String time24;
	/**
	 * 文章发布对象
	 */
	@ApiModelProperty(name = "targets", dataType = "java.util.List<ArticleTargetDTO>", value = "文章发布对象")
	private List<ArticleTargetDTO> targets;
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
