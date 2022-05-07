package io.hiwepy.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.article.dao.ArticleCommentMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleCommentEntity;
import io.hiwepy.admin.extras.article.service.IArticleCommentService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

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