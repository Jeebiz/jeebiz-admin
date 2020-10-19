package net.jeebiz.admin.extras.article.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "ArticleTargetModel")
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTargetModel extends BaseModel<ArticleTargetModel> {

	/**
	 * 文章发布对象记录ID
	 */
	private String id;
	/**
	 * 文章ID
	 */
	private String cid;
	/**
	 * 文章发布对象ID（学院ID|专业ID|班级ID|账户ID）
	 */
	private String tid;
	/**
	 * 文章发布对象名称（学院|专业|班级|账户）
	 */
	private String tname;
	
}
