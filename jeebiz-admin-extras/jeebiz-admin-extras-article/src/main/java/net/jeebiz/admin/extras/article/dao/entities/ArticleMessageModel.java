package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleMessageModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleMessageModel extends PaginationModel<ArticleMessageModel> implements Comparable<ArticleMessageModel> {
	
	/**
	 * 文章消息ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 文章消息接收者ID
	 */
	private String uid;
	/**
	 * 文章消息接收者姓名
	 */
	private String uname;
	/**
	 *文章消息内容
	 */
	private String msg;
	/**
	 * 文章消息状态（0:未读|1:已读）
	 */
	private Integer status;
	/**
	 *文章消息发送时间
	 */
	private String time24;
	
	@Override
	public int compareTo(ArticleMessageModel o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
