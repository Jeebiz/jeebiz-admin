package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.ArticleCommentMapper;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCommentEntity;
import net.jeebiz.admin.extras.article.service.IArticleCommentService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleCommentMapper, ArticleCommentEntity> implements IArticleCommentService {

	@Override
	public int setRecommend(String id, String recommend) {
		return getBaseMapper().setRecommend(id, recommend);
	}

	@Override
	public int setReview(String id, String review) {
		return getBaseMapper().setReview(id, review);
	}

}