package net.jeebiz.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.ArticleTopicMapper;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicEntity;
import net.jeebiz.admin.extras.article.service.IArticleTopicService;
import net.jeebiz.admin.extras.article.utils.TopicUtils;
import net.jeebiz.admin.extras.article.web.dto.ArticleTopicTreeDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleTopicServiceImpl extends BaseServiceImpl<ArticleTopicMapper, ArticleTopicEntity> implements IArticleTopicService {

	@Override
	public List<ArticleTopicTreeDTO> tree() {
		List<ArticleTopicEntity> topicList = getBaseMapper().getTopics();
		return TopicUtils.getTopicTreeList(topicList);
	}

}