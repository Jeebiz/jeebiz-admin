package io.hiwepy.admin.extras.article.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.article.dao.ArticleCategoryMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleCategoryEntity;
import io.hiwepy.admin.extras.article.service.IArticleCategoryService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryMapper, ArticleCategoryEntity> implements IArticleCategoryService {
	
}
