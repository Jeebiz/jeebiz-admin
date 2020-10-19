package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleTagModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTagModel extends PaginationModel<ArticleTagModel> implements Comparable<ArticleTagModel> {

	/**
	 * 文章标签ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 文章标签名称
	 */
	private String name;
	/**
	 * 文章标签创建时间
	 */
	private String time24;
	
	@Override
	public int compareTo(ArticleTagModel o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
