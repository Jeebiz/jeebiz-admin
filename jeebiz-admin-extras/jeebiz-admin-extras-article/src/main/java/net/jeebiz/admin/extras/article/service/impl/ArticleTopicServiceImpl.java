package net.jeebiz.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.article.dao.IArticleTopicDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicModel;
import net.jeebiz.admin.extras.article.service.IArticleTopicService;
import net.jeebiz.admin.extras.article.utils.TopicUtils;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicTreeVo;

@Service
public class ArticleTopicServiceImpl extends BaseServiceImpl<ArticleTopicModel, IArticleTopicDao> implements IArticleTopicService {

	@Override
	public List<ArticleTopicTreeVo> tree() {
		List<ArticleTopicModel> topicList = getDao().getTopics();
		return TopicUtils.getTopicTreeList(topicList);
	}

}