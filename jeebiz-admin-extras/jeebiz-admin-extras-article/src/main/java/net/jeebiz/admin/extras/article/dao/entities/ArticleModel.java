package net.jeebiz.admin.extras.article.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "ArticleModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleModel extends PaginationModel<ArticleModel> implements Comparable<ArticleModel> {

	/**
	 * 文章D
	 */
	private String id;
	/**
	 * 文章栏目id
	 */
	private String tid;
	/**
	 * 文章栏目名称
	 */
	private String tname;
	/**
	 * 文章分类id
	 */
	private String cid;
	/**
	 * 文章分类名称
	 */
	private String cname;
	/**
	 * 文章发布者id
	 */
	private String uid;
	/**
	 * 文章发布者姓名
	 */
	private String uname;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章摘要
	 */
	private String digest;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）
	 */
	private Integer review;
	/**
	 * 文章状态（0:禁用|1:可用）
	 */
	private Integer status;
	/**
	 * 文章是否推荐（0:未推荐|1:推荐）
	 */
	private Integer recommend;
	/**
	 * 文章排序
	 */
	private Integer order;
	/**
	 * 文章浏览数
	 */
	private Integer browse;
	/**
	 * 文章收藏数
	 */
	private Integer collect;
	/**
	 * 文章点赞数
	 */
	private Integer liked;
	/**
	 * 文章发布时间
	 */
	private String ptime24;
	/**
	 * 文章创建时间
	 */
	private String time24;
	/**
	 * 文章发布对象
	 */
	private List<ArticleTargetModel> targets;
	/**
	 * 文章封面
	 */
	private ArticleAttachmentModel cover;
	/**
	 * 文章附件
	 */
	private List<ArticleAttachmentModel> atts;
	/**
	 * 文章标签
	 */
	private List<ArticleTagModel> tags;
	
	@Override
	public int compareTo(ArticleModel o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}

}
