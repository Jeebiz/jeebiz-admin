package io.hiwepy.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.article.dao.ArticleTopicMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleTopicEntity;
import io.hiwepy.admin.extras.article.service.IArticleTopicService;
import io.hiwepy.admin.extras.article.utils.TopicUtils;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicTreeDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class ArticleTopicServiceImpl extends BaseServiceImpl<ArticleTopicMapper, ArticleTopicEntity> implements IArticleTopicService {

	@Override
	public List<ArticleTopicTreeDTO> tree() {
		List<ArticleTopicEntity> topicList = getBaseMapper().getTopics();
		return TopicUtils.getTopicTreeList(topicList);
	}

}