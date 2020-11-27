package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTagDTO", description = "文章标签参数DTO")
@Data
public class ArticleTagDTO {
	
	/**
	 * 文章标签ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章标签ID")
	private String id;
	/**
	 * 文章ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章ID")
	private String cid;
	/**
	 * 文章标签名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章标签名称")
	private String name;
	/**
	 * 文章标签创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章标签创建时间")
	private String time24;
	
}