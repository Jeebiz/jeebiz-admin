package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTopicVo", description = "文章栏目参数Vo")
@Data
public class ArticleTopicVo {
	
	/**
	 * 文章栏目ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "文章栏目ID")
	private String id;
	/**
	 * 上级文章栏目ID
	 */
	@ApiModelProperty(name = "pid", dataType = "String", value = "上级文章栏目ID")
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
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章分类ID")
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
	 * 文章栏目创建者ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章栏目创建者ID")
	private String uid;
	/**
	 * 文章栏目创建者姓名
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "文章栏目创建者姓名")
	private String uname;
	
}