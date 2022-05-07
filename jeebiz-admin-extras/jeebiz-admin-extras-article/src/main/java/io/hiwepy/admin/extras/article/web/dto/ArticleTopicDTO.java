package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTopicDTO", description = "文章栏目参数DTO")
@Data
public class ArticleTopicDTO {
	
	/**
	 * 文章栏目id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章栏目id")
	private String id;
	/**
	 * 上级文章栏目id
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章栏目id")
	private String pid;
	/**
	 * 上级文章栏目
	 */
	@ApiModelProperty(name = "pname", dataType = "String", value = "上级文章栏目")
	private String pname;
	/**
	 * 文章栏目名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章栏目名称")
	private String name;
	/**
	 * 文章分类id
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章分类id")
	private String cid;
	/**
	 * 文章分类名称
	 */
	@ApiModelProperty(name = "cname", dataType = "String", value = "文章分类名称")
	private String cname;
	/**
	 * 文章栏目备注
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "文章栏目备注")
	private String remark;
	/**
	 * 文章栏目状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "Integer", value = "文章栏目状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章栏目排序
	 */
	@ApiModelProperty(name = "order", dataType = "Integer", value = "文章栏目排序")
	private Integer order;
	/**
	 * 文章栏目创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "文章栏目创建时间")
	private String time24;
	/**
	 * 文章栏目创建者id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章栏目创建者id")
	private String uid;
	/**
	 * 文章栏目创建者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章栏目创建者姓名")
	private String uname;
	
}