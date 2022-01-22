package net.jeebiz.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleTopicMapper extends BaseMapper<ArticleTopicEntity> {

    List<ArticleTopicEntity> getTopics();
    
}
