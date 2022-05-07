package io.hiwepy.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.article.dao.entities.ArticleCategoryEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategoryEntity> {

}