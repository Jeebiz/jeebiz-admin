package io.hiwepy.admin.extras.article.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import io.hiwepy.admin.extras.article.dao.ArticleAttachmentMapper;
import io.hiwepy.admin.extras.article.dao.ArticleMapper;
import io.hiwepy.admin.extras.article.dao.ArticleTargetMapper;
import io.hiwepy.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import io.hiwepy.admin.extras.article.dao.entities.ArticleEntity;
import io.hiwepy.admin.extras.article.dao.entities.ArticleTargetEntity;
import io.hiwepy.admin.extras.article.service.IArticleService;
import io.hiwepy.admin.extras.article.utils.ArticleUtils;
import io.hiwepy.admin.extras.article.web.dto.ArticleDetailDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, ArticleEntity> implements IArticleService {

	@Autowired
	private ArticleTargetMapper articleTargetMapper;
	@Autowired
	private ArticleAttachmentMapper articleAttachmentMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(ArticleEntity model) {
		
		// 先保存文章内容
		int rt = getBaseMapper().insert(model);
		
		/**
		 * 文章发布对象
		 */
		List<ArticleTargetEntity> targets = model.getTargets();
		if(CollectionUtils.isNotEmpty(targets)) {
			for (ArticleTargetEntity targetModel : targets) {
				targetModel.setCid(model.getId());
				getArticleTargetMapper().insert(targetModel);
			}
		}
		/**
		 * 文章封面
		 */
		ArticleAttachmentEntity cover = model.getCover();
		if(!Objects.isNull(cover)) {
			cover.setCid(model.getId());
			cover.setType(1);
			getArticleAttachmentMapper().insert(cover);
		}
		/**
		 * 文章附件
		 */
		List<ArticleAttachmentEntity> atts = model.getAtts();
		if(CollectionUtils.isNotEmpty(atts)) {
			for (ArticleAttachmentEntity attModel : atts) {
				if(!Objects.isNull(attModel)) {
					attModel.setCid(model.getId());
					attModel.setType(3);
					getArticleAttachmentMapper().insert(attModel);
				}
			}
		}
		return SqlHelper.retBool(rt);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(ArticleEntity model) {
		
		// 先更新文章内容
		int rt = getBaseMapper().updateById(model);
		
		/**
		 * 文章发布对象
		 */
		
		List<ArticleTargetEntity> targets = model.getTargets();
		// 查询发布范围
		List<ArticleTargetEntity> oldTargets = getArticleTargetMapper().getTargetList(model.getId());
		// 之前没有发布范围
		if(CollectionUtils.isEmpty(oldTargets) && CollectionUtils.isNotEmpty(targets)) {
			for (ArticleTargetEntity targetModel : targets) {
				targetModel.setCid(model.getId());
				getArticleTargetMapper().insert(targetModel);
			}
		} else if(CollectionUtils.isNotEmpty(oldTargets) && CollectionUtils.isNotEmpty(targets)) {
			// 保存新增的发布范围
			for (ArticleTargetEntity targetModel : ArticleUtils.incrementTarget(targets, oldTargets)) {
				targetModel.setCid(model.getId());
				getArticleTargetMapper().insert(targetModel);
			}
			// 删除移除的发布范围
			for (ArticleTargetEntity targetModel : ArticleUtils.decrementTarget(targets, oldTargets)) {
				getArticleTargetMapper().deleteById(targetModel.getId());
			}
		} else {
			// 没有设置发布范围
			getArticleTargetMapper().deleteTarget(model.getId());
		}
		/**
		 * 文章封面
		 */
		ArticleAttachmentEntity cover = model.getCover();
		if(!Objects.isNull(cover)) {
			cover.setType(1);
			getArticleAttachmentMapper().updateById(cover);
		}
		/**
		 * 文章附件
		 */
		List<ArticleAttachmentEntity> atts = model.getAtts();
		// 查询文章附件
		List<ArticleAttachmentEntity> oldAtts = getArticleAttachmentMapper().getAttachmentList(model.getId());
		// 之前没有文章附件
		if(CollectionUtils.isEmpty(oldAtts) && CollectionUtils.isNotEmpty(atts)) {
			for (ArticleAttachmentEntity attModel : atts) {
				attModel.setType(3);
				getArticleAttachmentMapper().updateById(attModel);
			}
		} else if(CollectionUtils.isNotEmpty(oldAtts) && CollectionUtils.isNotEmpty(atts)) {
			// 保存新增的文章附件
			for (ArticleAttachmentEntity attModel : ArticleUtils.incrementAtt(atts, oldAtts)) {
				attModel.setCid(model.getId());
				getArticleAttachmentMapper().insert(attModel);
			}
			// 删除移除的文章附件
			for (ArticleAttachmentEntity attModel : ArticleUtils.decrementAtt(atts, oldAtts)) {
				getArticleAttachmentMapper().deleteById(attModel.getId());
			}
		} else {
			// 没有设置文章附件
			getArticleAttachmentMapper().deleteAtt(model.getId());
		}
		
		return SqlHelper.retBool(rt);
	}
	
	
	@Override
	public int setRecommend(String id, String status) {
		return getBaseMapper().setRecommend(id, status);
	}

	@Override
	public int setReview(String id, String status) {
		return getBaseMapper().setReview(id, status);
	}

	@Override
	public ArticleDetailDTO getDetail(String id) {
		return getBaseMapper().getDetail(id);
	}

	public ArticleTargetMapper getArticleTargetMapper() {
		return articleTargetMapper;
	}
	
	public ArticleAttachmentMapper getArticleAttachmentMapper() {
		return articleAttachmentMapper;
	}

    
}