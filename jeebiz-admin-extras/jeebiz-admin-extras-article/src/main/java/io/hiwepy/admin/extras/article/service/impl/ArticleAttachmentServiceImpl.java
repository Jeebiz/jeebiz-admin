package io.hiwepy.admin.extras.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.article.dao.ArticleAttachmentMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import io.hiwepy.admin.extras.article.service.IArticleAttachmentService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class ArticleAttachmentServiceImpl extends BaseServiceImpl<ArticleAttachmentMapper, ArticleAttachmentEntity> implements IArticleAttachmentService {

	@Override
	public List<ArticleAttachmentEntity> getAttachmentList(String cid) {
		return getBaseMapper().getAttachmentList(cid);
	}
	
}