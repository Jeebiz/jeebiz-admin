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

@Alias(value = "ArticleTagModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_content_tags")
public class ArticleTagEntity extends PaginationEntity<ArticleTagEntity> implements Comparable<ArticleTagEntity> {

	/**
	 * 文章标签id
	 */
	@TableId(value="t_id", type= IdType.AUTO)
	private String id;
	/**
	 * 文章id
	 */
	@TableField(value = "t_cid")
	private String cid;
	/**
	 * 文章标签名称
	 */
	@TableField(value = "t_name")
	private String name;
	/**
	 * 文章标签创建时间
	 */
	@TableField(value = "t_time24")
	private LocalDateTime time24;
	
	@Override
	public int compareTo(ArticleTagEntity o) {
		if (o == null || o.getTime24() == null) {
			return 1;
		}
		return o.getTime24().compareTo(this.time24);
	}
	
}
