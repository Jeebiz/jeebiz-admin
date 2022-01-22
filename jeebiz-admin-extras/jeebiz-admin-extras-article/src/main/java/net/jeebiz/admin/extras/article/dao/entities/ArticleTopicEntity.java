package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "ArticleTopicEntity")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_topic")
public class ArticleTopicEntity extends PaginationEntity<ArticleTopicEntity> implements Comparable<ArticleTopicEntity> {

	/**
	 * 文章栏目id
	 */
	@TableId(value="m_id", type= IdType.AUTO)
	private String id;
	/**
	 * 文章栏目创建者id
	 */
	private String uid;
	/**
	 * 文章栏目创建者姓名
	 */
	private String uname;
	/**
	 * 上级文章栏目id
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
	 * 文章分类id
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
	public int compareTo(ArticleTopicEntity o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}
	
}
