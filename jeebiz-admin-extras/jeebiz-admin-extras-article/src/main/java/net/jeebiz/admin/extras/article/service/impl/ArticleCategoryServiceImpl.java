package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.IArticleCategoryDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCategoryModel;
import net.jeebiz.admin.extras.article.service.IArticleCategoryService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class ArticleCategoryServiceImpl extends BaseMapperServiceImpl<ArticleCategoryModel, IArticleCategoryDao> implements IArticleCategoryService {
	
}