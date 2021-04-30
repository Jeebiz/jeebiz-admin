package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTargetModel;

@Mapper
public interface IArticleTargetDao extends BaseDao<ArticleTargetModel> {

	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleTargetModel> getTargetList(@Param("cid") String cid);

	/**
	 * @param cid 文章id
	 * @return
	 */
	int deleteTarget(@Param("cid") String cid);
	
}