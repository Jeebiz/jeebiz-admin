package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleVisitDTO", description = "文章访问记录DTO")
@Data
public class ArticleVisitDTO {

	/**
	 * 文章访问id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章访问id")
	private String id;
	/**
	 * 文章id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章id")
	private String cid;
	/**
	 * 文章访问者id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章访问者id")
	private String uid;
	/**
	 * 文章访问者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章访问者姓名")
	private String uname;
	/**
	 * 访问来源IP
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "访问来源IP")
	private String addr;
	/**
	 * 访问来源地址
	 */
	@ApiModelProperty(name = "location", dataType = "String", value = "访问来源地址")
	private String location;
	/**
	 * 访问来源User-Agent
	 */
	@ApiModelProperty(name = "agent", dataType = "String", value = "访问来源User-Agent")
	private String agent;
	/**
	 * 文章访问时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章访问时间")
	private String time24;
	
}
