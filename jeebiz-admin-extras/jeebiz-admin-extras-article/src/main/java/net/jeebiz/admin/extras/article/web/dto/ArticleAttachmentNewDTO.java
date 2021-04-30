package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleAttachmentNewDTO", description = "文章附件新增参数DTO")
@Data
public class ArticleAttachmentNewDTO {
	
	/**
	 * 文章id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章id")
	private String cid;
	/**
	 * 文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）
	 */
	@ApiModelProperty(name = "type", dataType = "Integer", value = "文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）")
	private Integer type;
	/**
	 * 文章附件名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章附件名称")
	private String name;
	/**
	 * 文章附件存储路径（相对地址）
	 */
	@ApiModelProperty(name = "path", dataType = "String", value = "文章附件存储路径（相对地址）")
	private String path;
	/**
	 * 文章附件排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章附件排序")
	private Integer order;
	
}
