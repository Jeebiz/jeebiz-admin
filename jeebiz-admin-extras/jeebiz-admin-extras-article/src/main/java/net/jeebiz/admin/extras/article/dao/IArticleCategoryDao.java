package net.jeebiz.admin.extras.article.dao;

import net.jeebiz.boot.api.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.article.dao.entities.ArticleCategoryModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IArticleCategoryDao extends BaseMapper<ArticleCategoryModel> {

}