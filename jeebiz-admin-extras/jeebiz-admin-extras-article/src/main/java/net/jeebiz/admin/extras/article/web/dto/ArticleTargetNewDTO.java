package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetNewDTO", description = "文章发布对象新增参数DTO")
@Data
public class ArticleTargetNewDTO {
	
	/**
	 * 文章发布对象id（学院id|专业id|班级id|账户id）
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章发布对象id（学院id|专业id|班级id|账户id）")
	private String tid;
	
}