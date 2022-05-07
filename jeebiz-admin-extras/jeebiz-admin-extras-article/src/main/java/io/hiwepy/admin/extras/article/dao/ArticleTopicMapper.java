package io.hiwepy.admin.extras.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.article.dao.entities.ArticleTopicEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleTopicMapper extends BaseMapper<ArticleTopicEntity> {

    List<ArticleTopicEntity> getTopics();
    
}
