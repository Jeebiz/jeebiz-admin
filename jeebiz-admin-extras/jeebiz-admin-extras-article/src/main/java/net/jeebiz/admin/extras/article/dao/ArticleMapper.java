package net.jeebiz.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.article.dao.entities.ArticleEntity;
import net.jeebiz.admin.extras.article.web.dto.ArticleDetailDTO;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
	
	/**
	 * 
	 * @param id 文章id
	 * @param status 文章推荐（0:未推荐|1:推荐）
	 * @return
	 */
	int setRecommend(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 
	 * @param id 文章id
	 * @param status 文章审核状态（0:未通过|1:通过）
	 * @return
	 */
	int setReview(@Param("id") String id, @Param("status") String status);

	/**
	 * 查询文章详情
	 * @param id
	 * @return
	 */
	ArticleDetailDTO getDetail(@Param("id") String id);
	
}
