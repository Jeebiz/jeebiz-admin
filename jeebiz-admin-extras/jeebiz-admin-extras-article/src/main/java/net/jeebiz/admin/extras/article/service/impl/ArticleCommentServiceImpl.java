package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.article.dao.IArticleCommentDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCommentModel;
import net.jeebiz.admin.extras.article.service.IArticleCommentService;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleCommentModel, IArticleCommentDao> implements IArticleCommentService {

	@Override
	public int setRecommend(String id, String recommend) {
		return getDao().setRecommend(id, recommend);
	}

	@Override
	public int setReview(String id, String review) {
		return getDao().setReview(id, review);
	}

}