package net.jeebiz.admin.extras.article.service;

import java.util.List;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicModel;
import net.jeebiz.admin.extras.article.web.dto.ArticleTopicTreeDTO;
import net.jeebiz.boot.api.service.IBaseService;

public interface IArticleTopicService extends IBaseService<ArticleTopicModel> {

	/**
	 * 文章栏目树
	 * 
	 * @return 文章栏目树
	 */
	List<ArticleTopicTreeDTO> tree();
}