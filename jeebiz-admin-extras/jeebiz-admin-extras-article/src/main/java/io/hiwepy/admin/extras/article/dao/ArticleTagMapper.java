package io.hiwepy.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.article.dao.entities.ArticleTagEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {

}
