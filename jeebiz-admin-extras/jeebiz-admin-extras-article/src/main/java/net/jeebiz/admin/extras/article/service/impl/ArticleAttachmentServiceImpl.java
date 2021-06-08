package net.jeebiz.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.IArticleAttachmentDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentModel;
import net.jeebiz.admin.extras.article.service.IArticleAttachmentService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleAttachmentServiceImpl extends BaseServiceImpl<ArticleAttachmentModel, IArticleAttachmentDao> implements IArticleAttachmentService {

	@Override
	public List<ArticleAttachmentModel> getAttachmentList(String cid) {
		return getDao().getAttachmentList(cid);
	}
	
}