package net.jeebiz.admin.extras.article.service;

import java.util.List;

import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import net.jeebiz.boot.api.service.IBaseService;

public interface IArticleAttachmentService extends IBaseService<ArticleAttachmentEntity> {

	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleAttachmentEntity> getAttachmentList(String cid);
	
}