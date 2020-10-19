package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTargetVo", description = "文章发布对象参数Vo")
@Data
public class ArticleTargetVo {
	
	/**
	 * 文章发布对象记录ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章发布对象记录ID")
	private String id;
	/**
	 * 文章ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章ID")
	private String cid;
	/**
	 * 文章发布对象ID（学院ID|专业ID|班级ID|账户ID）
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "文章发布对象ID（学院ID|专业ID|班级ID|账户ID）")
	private String tid;
	/**
	 * 文章发布对象名称（学院|专业|班级|账户）
	 */
	@ApiModelProperty(name = "tname", dataType = "String", value = "文章发布对象名称（学院|专业|班级|账户）")
	private String tname;
	
}