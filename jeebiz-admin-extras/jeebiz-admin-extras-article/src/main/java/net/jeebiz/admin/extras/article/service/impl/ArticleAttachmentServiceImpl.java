package net.jeebiz.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.article.dao.ArticleAttachmentMapper;
import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import net.jeebiz.admin.extras.article.service.IArticleAttachmentService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleAttachmentServiceImpl extends BaseServiceImpl<ArticleAttachmentMapper, ArticleAttachmentEntity> implements IArticleAttachmentService {

	@Override
	public List<ArticleAttachmentEntity> getAttachmentList(String cid) {
		return getBaseMapper().getAttachmentList(cid);
	}
	
}