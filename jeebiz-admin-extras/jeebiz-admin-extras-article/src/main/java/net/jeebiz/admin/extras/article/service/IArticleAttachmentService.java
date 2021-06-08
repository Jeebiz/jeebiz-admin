package net.jeebiz.admin.extras.article.service;

import java.util.List;

import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IArticleAttachmentService extends IBaseService<ArticleAttachmentModel> {

	/**
	 * @param cid 文章id
	 * @return
	 */
	List<ArticleAttachmentModel> getAttachmentList(String cid);
	
}