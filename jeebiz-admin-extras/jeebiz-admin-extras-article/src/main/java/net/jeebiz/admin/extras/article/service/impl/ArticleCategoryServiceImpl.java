package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.article.dao.IArticleCategoryDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCategoryModel;
import net.jeebiz.admin.extras.article.service.IArticleCategoryService;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryModel, IArticleCategoryDao> implements IArticleCategoryService {
	
}