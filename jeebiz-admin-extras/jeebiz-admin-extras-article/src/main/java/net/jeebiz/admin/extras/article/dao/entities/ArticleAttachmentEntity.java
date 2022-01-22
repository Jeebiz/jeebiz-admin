package net.jeebiz.admin.extras.article.dao.entities;//

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@SuppressWarnings("serial")
@Alias(value = "ArticleAttachmentEntity")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_content_atts")
public class ArticleAttachmentEntity extends PaginationEntity<ArticleAttachmentEntity>
		implements Comparable<ArticleAttachmentEntity> {

	/**
	 * 文章附件id
	 */
	@TableId(value = "a_id", type = IdType.AUTO)
	private String id;   
	/**
	 * 文章id
	 */
	@TableField(value = "a_cid")
	private String cid;
	/**
	 * 文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）
	 */
	@TableField(value = "a_type")
	private Integer type;
	/**
	 * 文章附件名称
	 */
	@TableField(value = "a_name")
	private String name;
	/**
	 * 文章附件存储路径（相对地址）
	 */
	@TableField(value = "a_path")
	private String path;
	/**
	 * 文章附件排序
	 */
	@TableField(value = "a_order")
	private Integer orderBy;

	@Override
	public int compareTo(ArticleAttachmentEntity o) {
		if (o == null || o.getOrderBy() == null) {
			return 1;
		}
		return o.getOrderBy().compareTo(this.orderBy);
	}

}
