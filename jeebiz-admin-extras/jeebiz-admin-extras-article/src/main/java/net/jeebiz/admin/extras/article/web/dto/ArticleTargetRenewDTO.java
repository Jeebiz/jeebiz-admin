package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetRenewDTO", description = "文章发布对象编辑参数DTO")
@Data
public class ArticleTargetRenewDTO {
	
	/**
	 * 文章发布对象记录ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章发布对象记录ID")
	private String id;
	/**
	 * 文章发布对象ID（学院ID|专业ID|班级ID|账户ID）
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章发布对象ID（学院ID|专业ID|班级ID|账户ID）")
	private String tid;
	
}