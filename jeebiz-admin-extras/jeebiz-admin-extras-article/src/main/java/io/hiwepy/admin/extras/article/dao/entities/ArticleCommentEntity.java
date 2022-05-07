package io.hiwepy.admin.extras.article.dao.entities;//



import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

@SuppressWarnings("serial")
@Alias(value = "ArticleCommentEntity")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_content_comments")
public class ArticleCommentEntity extends PaginationEntity<ArticleCommentEntity> implements Comparable<ArticleCommentEntity> {

	/**
	 * 文章评论id
	 */
	@TableId(value="c_cid",type= IdType.AUTO)
	private String id;
	/**
	 * 文章id
	 */
	@TableField(value = "c_cid")
	private String cid;
	/**
	 * 上级文章评论id
	 */
	@TableField(value = "c_pid")
	private String pid;
	/**
	 * 文章评论者id
	 */
	@TableField(value = "c_uid")
	private String uid;
	/**
	 * 文章评论者姓名
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 文章评论类型
	 */
	@TableField(value = "c_type")
	private String type;
	/**
	 * 文章评论内容
	 */
	@TableField(value = "c_text")
	private String text;
	/**
	 * 文章评论审核状态（0:未通过|1:通过）
	 */
	@TableField(value = "c_review")
	private Integer review;
	/**
	 * 文章评论状态（0:删除|1:正常）
	 */
	@TableField(value = "c_status")
	private Integer status;
	/**
	 * 文章评论推荐（0:未推荐|1:推荐）
	 */
	@TableField(value = "c_recommend")
	private Integer recommend;
	/**
	 * 文章评论关键字
	 */
	@TableField(value = "c_keywords")
	private String keywords;
	/**
	 * 文章评论排序
	 */
	@TableField(value = "c_order")
	private Integer orderBy;
	/**
	 * 文章评论时间
	 */
	@TableField(value = "c_time24")
	private LocalDateTime time24;

	@Override
	public int compareTo(ArticleCommentEntity o) {
		if (o == null || o.getOrderBy() == null) {
			return 1;
		}
		return o.getOrderBy().compareTo(this.orderBy);
	}

}
