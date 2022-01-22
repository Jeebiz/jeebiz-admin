package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "ArticleCategoryEntity")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_category")
public class ArticleCategoryEntity extends PaginationEntity<ArticleCategoryEntity> implements Comparable<ArticleCategoryEntity> {

	/**
	 * 文章分类id
	 */
	@TableId(value="c_id",type= IdType.AUTO)
	private String id;
	/**
	 * 文章分类创建者姓名
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 文章分类名称
	 */
	@TableField(value = "c_name")
	private String name;
	/**
	 * 文章分类等级
	 */
	@TableField(value = "c_grade")
	private Integer grade;
	/**
	 * 文章分类简介
	 */
	@TableField(value = "c_intro")
	private String intro;
	/**
	 * 文章分类关键字
	 */
	@TableField(value = "c_keywords")
	private String keywords;
	/**
	 * 文章分类排序
	 */
	@TableField(value = "c_order")
	private Integer orderBy;
	/**
	 * 文章分类状态（0:禁用|1:可用）
	 */
	@TableField(value = "c_status")
	private Integer status;

	@Override
	public int compareTo(ArticleCategoryEntity o) {
		if (o == null || o.getOrderBy() == null) {
			return 1;
		}
		return o.getOrderBy().compareTo(this.orderBy);
	}
	
}
