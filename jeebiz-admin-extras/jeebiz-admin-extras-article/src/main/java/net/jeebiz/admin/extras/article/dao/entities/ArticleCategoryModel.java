package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleCategoryModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCategoryModel extends PaginationModel<ArticleCategoryModel> implements Comparable<ArticleCategoryModel> {

	/**
	 * 文章分类ID
	 */
	private String id;
	/**
	 * 文章分类创建者ID
	 */
	private String uid;
	/**
	 * 文章分类创建者姓名
	 */
	private String uname;
	/**
	 * 文章分类名称
	 */
	private String name;
	/**
	 * 文章分类等级
	 */
	private Integer grade;
	/**
	 * 文章分类简介
	 */
	private String intro;
	/**
	 * 文章分类关键字
	 */
	private String keywords;
	/**
	 * 文章分类排序
	 */
	private Integer order;
	/**
	 * 文章分类状态（0:禁用|1:可用）
	 */
	private Integer status;
	/**
	 * 文章分类创建时间
	 */
	private String time24;

	@Override
	public int compareTo(ArticleCategoryModel o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}
	
}
