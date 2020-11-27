package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetNewDTO", description = "文章发布对象新增参数DTO")
@Data
public class ArticleTargetNewDTO {
	
	/**
	 * 文章发布对象ID（学院ID|专业ID|班级ID|账户ID）
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章发布对象ID（学院ID|专业ID|班级ID|账户ID）")
	private String tid;
	
}