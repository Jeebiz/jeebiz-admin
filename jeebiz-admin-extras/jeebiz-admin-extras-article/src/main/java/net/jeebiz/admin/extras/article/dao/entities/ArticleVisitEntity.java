package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "ArticleVisitEntity")
@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("serial")
@TableName("sys_article_visits")
public class ArticleVisitEntity extends PaginationEntity<ArticleVisitEntity> implements Comparable<ArticleVisitEntity> {

	/**
	 * 文章访问id
	 */
	@TableId(value="m_id", type= IdType.AUTO)
	private String id;
	/**
	 * 文章id
	 */
	private String cid;
	/**
	 * 文章访问者id
	 */
	private String uid;
	/**
	 * 文章访问者姓名
	 */
	private String uname;
	/**
	 * 访问来源IP
	 */
	private String addr;
	/**
	 * 访问来源地址
	 */
	private String location;
	/**
	 * 访问来源User-Agent
	 */
	private String agent;
	/**
	 * 文章访问时间
	 */
	private String time24;
	
	@Override
	public int compareTo(ArticleVisitEntity o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
