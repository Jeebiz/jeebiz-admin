package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTagNewDTO", description = "文章标签参数DTO")
@Data
public class ArticleTagNewDTO {
	
	/**
	 * 文章id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章id")
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