package net.jeebiz.admin.extras.article.dao.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "ArticleEntity")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_contents")
public class ArticleEntity extends PaginationEntity<ArticleEntity> implements Comparable<ArticleEntity> {

	/**
	 * 文章D
	 */
	@TableId(value="c_id",type= IdType.AUTO)
	private String id;
	/**
	 * 文章栏目id
	 */
	@TableField(value = "c_tid")
	private String tid;
	/**
	 * 文章栏目名称
	 */
	@TableField(exist = false)
	private String tname;
	/**
	 * 文章分类id
	 */
	@TableField(value = "c_cid")
	private String cid;
	/**
	 * 文章分类名称
	 */
	@TableField(exist = false)
	private String cname;
	/**
	 * 文章发布者id
	 */
	@TableField(value = "c_uid")
	private String uid;
	/**
	 * 文章发布者姓名
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 文章标题
	 */
	@TableField(value = "c_title")
	private String title;
	/**
	 * 文章关键字
	 */
	@TableField(value = "c_keywords")
	private String keywords;
	/**
	 * 文章摘要
	 */
	@TableField(value = "c_digest")
	private String digest;
	/**
	 * 文章内容
	 */
	@TableField(value = "c_content")
	private String content;
	/**
	 * 文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）
	 */
	@TableField(value = "c_review")
	private Integer review;
	/**
	 * 文章状态（0:禁用|1:可用）
	 */
	@TableField(value = "c_status")
	private Integer status;
	/**
	 * 文章是否推荐（0:未推荐|1:推荐）
	 */
	@TableField(value = "c_recommend")
	private Integer recommend;
	/**
	 * 文章排序
	 */
	@TableField(value = "c_order")
	private Integer orderBy;
	/**
	 * 文章浏览数
	 */
	@TableField(value = "c_browse")
	private Long browse;
	/**
	 * 文章收藏数
	 */
	@TableField(value = "c_collect")
	private Long collect;
	/**
	 * 文章点赞数
	 */
	@TableField(value = "c_liked")
	private Long liked;
	/**
	 * 文章发布时间
	 */
	@TableField(value = "c_ptime24")
	private LocalDateTime ptime24;
	/**
	 * 文章发布对象
	 */
	@TableField(exist = false)
	private List<ArticleTargetEntity> targets;
	/**
	 * 文章封面
	 */
	@TableField(exist = false)
	private ArticleAttachmentEntity cover;
	/**
	 * 文章附件
	 */
	@TableField(exist = false)
	private List<ArticleAttachmentEntity> atts;
	/**
	 * 文章标签
	 */
	@TableField(exist = false)
	private List<ArticleTagEntity> tags;
	
	@Override
	public int compareTo(ArticleEntity o) {
		if (o == null || o.getCreateTime() == null) {
			return 1;
		}
		return o.getCreateTime().compareTo(this.getCreateTime());
	}

}
