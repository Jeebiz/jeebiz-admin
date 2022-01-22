package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleAttachmentMapper extends BaseMapper<ArticleAttachmentEntity> {
	
	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleAttachmentEntity> getAttachmentList(@Param("cid") String cid);
	
	/**
	 * @param cid 文章id
	 * @return
	 */
	int deleteAtt(@Param("cid") String cid);
	
}
