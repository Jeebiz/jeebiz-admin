package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetNewVo", description = "文章发布对象新增参数Vo")
@Data
public class ArticleTargetNewVo {
	
	/**
	 * 文章发布对象ID（学院ID|专业ID|班级ID|账户ID）
	 */
	@ApiModelProperty(name = "tid", required = true, dataType = "String", value = "文章发布对象ID（学院ID|专业ID|班级ID|账户ID）")
	private String tid;
	
}