package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleVisitModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleVisitModel extends PaginationModel<ArticleVisitModel> implements Comparable<ArticleVisitModel> {

	/**
	 * 文章访问ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 文章访问者ID
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
	public int compareTo(ArticleVisitModel o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
