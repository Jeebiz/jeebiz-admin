package io.hiwepy.admin.extras.article.service;

import io.hiwepy.admin.extras.article.dao.entities.ArticleCommentEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IArticleCommentService extends IBaseService<ArticleCommentEntity> {

	/**
	 * 
	 * @param id 文章评论id
	 * @param status 文章评论推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(String id, String status);
	
	/**
	 * 
	 * @param id 文章评论id
	 * @param status 文章评论审核状态（0:未通过|1:通过）
	 * @return
	 */
	int setReview(String id,  String status);
	
}