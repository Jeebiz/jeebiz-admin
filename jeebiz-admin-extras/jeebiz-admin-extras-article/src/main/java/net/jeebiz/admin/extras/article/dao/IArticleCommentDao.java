package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCommentModel;

@Mapper
public interface IArticleCommentDao extends BaseDao<ArticleCommentModel> {

	/**
	 * 
	 * @param id 文章评论ID
	 * @param status 文章评论推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 
	 * @param id 文章评论ID
	 * @param status 文章评论审核状态（0:未通过|1:通过）
	 * @return
	 */
	int setReview(@Param("id") String id, @Param("status") String status);
	
	List<ArticleCommentModel> getRowList(ArticleCommentModel model);
	
	List<ArticleCommentModel> getTreeList(ArticleCommentModel model);
	
}