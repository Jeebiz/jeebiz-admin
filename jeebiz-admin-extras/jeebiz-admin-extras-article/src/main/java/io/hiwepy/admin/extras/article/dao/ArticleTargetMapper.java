package io.hiwepy.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.article.dao.entities.ArticleTargetEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

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
