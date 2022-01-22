package net.jeebiz.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.ArticleCategoryMapper;
import net.jeebiz.admin.extras.article.dao.entities.ArticleCategoryEntity;
import net.jeebiz.admin.extras.article.service.IArticleCategoryService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryMapper, ArticleCategoryEntity> implements IArticleCategoryService {
	
}
