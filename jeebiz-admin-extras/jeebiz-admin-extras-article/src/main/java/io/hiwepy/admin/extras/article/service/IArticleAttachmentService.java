package io.hiwepy.admin.extras.article.service;

import java.util.List;

import io.hiwepy.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IArticleAttachmentService extends IBaseService<ArticleAttachmentEntity> {

	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleAttachmentEntity> getAttachmentList(String cid);
	
}