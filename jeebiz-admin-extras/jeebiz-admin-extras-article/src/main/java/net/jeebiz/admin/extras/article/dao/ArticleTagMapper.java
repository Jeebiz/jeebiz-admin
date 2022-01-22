package net.jeebiz.admin.extras.article.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTagEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {

}
