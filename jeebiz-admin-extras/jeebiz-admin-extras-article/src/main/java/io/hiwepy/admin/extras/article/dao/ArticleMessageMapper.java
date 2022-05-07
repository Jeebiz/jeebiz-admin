package io.hiwepy.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.article.dao.entities.ArticleMessageEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleMessageMapper extends BaseMapper<ArticleMessageEntity> {

}
