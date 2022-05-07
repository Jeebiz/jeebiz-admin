package io.hiwepy.admin.extras.article.service;

import io.hiwepy.admin.extras.article.dao.entities.ArticleEntity;
import io.hiwepy.admin.extras.article.web.dto.ArticleDetailDTO;
import io.hiwepy.boot.api.service.IBaseService;

public interface IArticleService extends IBaseService<ArticleEntity> {

	/**
	 * 
	 * @param id 文章id
	 * @param status 文章推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(String id, String status);
	
	/**
	 * 
	 * @param id 文章id
	 * @param status 文章审核状态（0:未通过|1:通过）
	 * @return
	 */
	int setReview(String id,  String status);
	
	/**
	 * 查询文章详情
	 * @param id
	 * @return
	 */
	ArticleDetailDTO getDetail(String id);
	
}