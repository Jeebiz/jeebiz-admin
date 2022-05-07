package io.hiwepy.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.article.dao.entities.ArticleVisitEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleVisitMapper extends BaseMapper<ArticleVisitEntity> {

}
