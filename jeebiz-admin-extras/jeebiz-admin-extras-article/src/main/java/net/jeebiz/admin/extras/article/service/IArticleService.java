package net.jeebiz.admin.extras.article.service;

import net.jeebiz.boot.api.service.IBaseService;
import net.jeebiz.admin.extras.article.dao.entities.ArticleModel;
import net.jeebiz.admin.extras.article.web.dto.ArticleDetailDTO;

public interface IArticleService extends IBaseService<ArticleModel> {

	/**
	 * 
	 * @param id 文章ID
	 * @param status 文章推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(String id, String status);
	
	/**
	 * 
	 * @param id 文章ID
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