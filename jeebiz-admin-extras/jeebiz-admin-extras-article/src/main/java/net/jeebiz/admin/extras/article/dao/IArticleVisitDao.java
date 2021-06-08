package net.jeebiz.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.article.dao.entities.ArticleVisitModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IArticleVisitDao extends BaseDao<ArticleVisitModel> {

}