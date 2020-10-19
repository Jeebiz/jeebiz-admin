package net.jeebiz.admin.extras.article.dao.entities;//



import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@SuppressWarnings("serial")
@Alias(value = "ArticleCommentModel")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCommentModel extends PaginationModel<ArticleCommentModel> implements Comparable<ArticleCommentModel> {
	 
	/**
	 * 文章评论ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 上级文章评论ID
	 */
	private String pid;
	/**
	 * 文章评论者ID
	 */
	private String uid;
	/**
	 * 文章评论者姓名
	 */
	private String uname;
	/**
	 * 文章评论类型
	 */
	private String type;
	/**
	 * 文章评论内容
	 */
	private String text;
	/**
	 * 文章评论审核状态（0:未通过|1:通过）
	 */
	private Integer review;
	/**
	 * 文章评论状态（0:删除|1:正常）
	 */
	private Integer status;
	/**
	 * 文章评论推荐（0:未推荐|1:推荐）
	 */
	private Integer recommend;
	/**
	 * 文章评论关键字
	 */
	private String keywords;
	/**
	 * 文章评论排序
	 */
	private Integer order;
	/**
	 * 文章评论时间
	 */
	private String time24;

	@Override
	public int compareTo(ArticleCommentModel o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}
	
}
