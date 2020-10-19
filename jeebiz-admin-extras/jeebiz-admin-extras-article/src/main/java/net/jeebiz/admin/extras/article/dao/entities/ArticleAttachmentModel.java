package net.jeebiz.admin.extras.article.dao.entities;//

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@SuppressWarnings("serial")
@Alias(value = "ArticleAttachmentModel")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleAttachmentModel extends PaginationModel<ArticleAttachmentModel>
		implements Comparable<ArticleAttachmentModel> {

	/**
	 * 文章附件ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）
	 */
	private Integer type;
	/**
	 * 文章附件名称
	 */
	private String name;
	/**
	 * 文章附件存储路径（相对地址）
	 */
	private String path;
	/**
	 * 文章附件排序
	 */
	private Integer order;
	/**
	 * 文章附件上传时间
	 */
	private String time24;

	@Override
	public int compareTo(ArticleAttachmentModel o) {
		if (o == null || o.getOrder() == null) {
			return 1;
		}
		return o.getOrder().compareTo(this.order);
	}

}
