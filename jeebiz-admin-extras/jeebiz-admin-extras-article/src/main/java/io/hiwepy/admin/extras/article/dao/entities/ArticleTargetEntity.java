package io.hiwepy.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

@Alias(value = "ArticleTargetEntity")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_article_content_targets")
public class ArticleTargetEntity extends BaseEntity<ArticleTargetEntity> {

	/**
	 * 文章发布对象记录id
	 */
	@TableId(value="m_id", type= IdType.AUTO)
	private String id;
	/**
	 * 文章id
	 */
	private String cid;
	/**
	 * 文章发布对象id（学院id|专业id|班级id|账户id）
	 */
	private String tid;
	/**
	 * 文章发布对象名称（学院|专业|班级|账户）
	 */
	private String tname;
	
}
