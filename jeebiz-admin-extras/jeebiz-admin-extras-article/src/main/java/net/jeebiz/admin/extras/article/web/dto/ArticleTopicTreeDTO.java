package net.jeebiz.admin.extras.article.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleTopicTreeDTO", description = "文章栏目树DTO")
@Data
public class ArticleTopicTreeDTO implements Comparable<ArticleTopicTreeDTO>{
	
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
	 * 子文章栏目集合
	 */
	@ApiModelProperty(name = "children", dataType = "java.util.List<ArticleTopicTreeDTO>", value = "子文章栏目集合")
	private List<ArticleTopicTreeDTO> children;
	
	/**
	 * 创建ztree的根节点
	 */
	public static ArticleTopicTreeDTO createRoot() {
		ArticleTopicTreeDTO myTreeNode = new ArticleTopicTreeDTO();
		myTreeNode.setId("0");
		myTreeNode.setName("顶级");
		myTreeNode.setPid("0");
		return myTreeNode;
	}

	@Override
	public int compareTo(ArticleTopicTreeDTO o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}

}