package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.article.dao.entities.ArticleCommentEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleCommentEntity> {

	/**
	 * 
	 * @param id 文章评论id
	 * @param status 文章评论推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 
	 * @param id 文章评论id
	 * @param status 文章评论审核状态（0:未通过|1:通过）
	 * @return
	 */
	int setReview(@Param("id") String id, @Param("status") String status);
	
	List<ArticleCommentEntity> getRowList(ArticleCommentEntity model);
	
	List<ArticleCommentEntity> getTreeList(ArticleCommentEntity model);
	
}
