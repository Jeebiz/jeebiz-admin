package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicModel;

@Mapper
public interface IArticleTopicDao extends BaseDao<ArticleTopicModel> {

    List<ArticleTopicModel> getTopics();
    
}