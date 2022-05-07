package io.hiwepy.admin.extras.article.service;

import java.util.List;

import io.hiwepy.admin.extras.article.dao.entities.ArticleTopicEntity;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicTreeDTO;
import io.hiwepy.boot.api.service.IBaseService;

public interface IArticleTopicService extends IBaseService<ArticleTopicEntity> {

	/**
	 * 文章栏目树
	 * 
	 * @return 文章栏目树
	 */
	List<ArticleTopicTreeDTO> tree();
}