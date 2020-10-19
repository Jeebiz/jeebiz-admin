package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.article.dao.IArticleVisitDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleVisitModel;
import net.jeebiz.admin.extras.article.service.IArticleVisitService;

@Service
public class ArticleVisitServiceImpl extends BaseServiceImpl<ArticleVisitModel, IArticleVisitDao> implements IArticleVisitService {

}
