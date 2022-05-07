package io.hiwepy.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.article.dao.ArticleVisitMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleVisitEntity;
import io.hiwepy.admin.extras.article.service.IArticleVisitService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class ArticleVisitServiceImpl extends BaseServiceImpl<ArticleVisitMapper, ArticleVisitEntity> implements IArticleVisitService {

}
