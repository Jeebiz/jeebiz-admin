package net.jeebiz.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTagModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IArticleTagDao extends BaseDao<ArticleTagModel> {

}