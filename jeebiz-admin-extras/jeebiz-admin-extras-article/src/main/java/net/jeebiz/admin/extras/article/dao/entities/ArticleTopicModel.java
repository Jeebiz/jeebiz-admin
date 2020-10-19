package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleTopicModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTopicModel extends PaginationModel<ArticleTopicModel> implements Comparable<ArticleTopicModel> {

	/**
	 * 文章栏目ID
	 */
	private String id;
	/**
	 * 文章栏目创建者ID
	 */
	private String uid;
	/**
	 * 文章栏目创建者姓名
	 */
	private String uname;
	/**
	 * 上级文章栏目ID
	 */
	private String pid;
	/**
	 * 上级文章栏目
	 */
	private String pname;
	/**
	 * 文章栏目名称
	 */
	private String name;
	/**
	 * 文章分类ID
	 */
	private String cid;
	/**
	 * 文章分类名称
	 */
	private String cname;
	/**
	 * 文章栏目备注
	 */
	private String remark;
	/**
	 * 文章栏目状态（0:禁用|1:可用）
	 */
	private Integer status;
	/**
	 * 文章栏目排序
	 */
	private Integer order;
	/**
	 * 文章栏目创建时间
	 */
	private String time24;
	/**
	 * 文章栏目关键字
	 */
	private String keywords;
	
	@Override
	public int compareTo(ArticleTopicModel o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}
	
}
