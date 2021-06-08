package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IArticleAttachmentDao extends BaseDao<ArticleAttachmentModel> {
	
	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleAttachmentModel> getAttachmentList(@Param("cid") String cid);
	
	/**
	 * @param cid 文章id
	 * @return
	 */
	int deleteAtt(@Param("cid") String cid);
	
}