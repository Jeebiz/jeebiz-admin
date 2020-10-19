package net.jeebiz.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleMessageModel;

@Mapper
public interface IArticleMessageDao extends BaseDao<ArticleMessageModel> {

}