package net.jeebiz.admin.extras.article.dao.entities;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "ArticleMessageEntity")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_messages")
public class ArticleMessageEntity extends PaginationEntity<ArticleMessageEntity> implements Comparable<ArticleMessageEntity> {
	
	/**
	 * 文章消息id
	 */
	@TableId(value="m_id", type= IdType.AUTO)
	private String id;
	/**
	 * 文章id
	 */
	@TableField(value = "m_cid")
	private String cid;
	/**
	 * 文章消息接收者id
	 */
	@TableField(value = "m_uid")
	private String uid;
	/**
	 * 文章消息接收者姓名
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 *文章消息内容
	 */
	@TableField(value = "m_msg")
	private String msg;
	/**
	 * 文章消息状态（0:未读|1:已读）
	 */
	@TableField(value = "m_status")
	private Integer status;
	/**
	 *文章消息发送时间
	 */
	@TableField(value = "m_time24")
	private LocalDateTime time24;
	
	@Override
	public int compareTo(ArticleMessageEntity o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
