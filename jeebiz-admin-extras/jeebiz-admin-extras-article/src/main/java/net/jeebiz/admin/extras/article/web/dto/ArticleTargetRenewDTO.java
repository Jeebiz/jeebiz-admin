package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetRenewDTO", description = "文章发布对象编辑参数DTO")
@Data
public class ArticleTargetRenewDTO {
	
	/**
	 * 文章发布对象记录id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章发布对象记录id")
	private String id;
	/**
	 * 文章发布对象id（学院id|专业id|班级id|账户id）
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章发布对象id（学院id|专业id|班级id|账户id）")
	private String tid;
	
}