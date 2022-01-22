package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTargetEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleTargetMapper extends BaseMapper<ArticleTargetEntity> {

	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleTargetEntity> getTargetList(@Param("cid") String cid);

	/**
	 * @param cid 文章id
	 * @return
	 */
	int deleteTarget(@Param("cid") String cid);
	
}
