package net.jeebiz.admin.extras.article.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.jeebiz.admin.extras.article.dao.IArticleAttachmentDao;
import net.jeebiz.admin.extras.article.dao.IArticleDao;
import net.jeebiz.admin.extras.article.dao.IArticleTargetDao;
import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentModel;
import net.jeebiz.admin.extras.article.dao.entities.ArticleModel;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTargetModel;
import net.jeebiz.admin.extras.article.service.IArticleService;
import net.jeebiz.admin.extras.article.utils.ArticleUtils;
import net.jeebiz.admin.extras.article.web.dto.ArticleDetailDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleModel, IArticleDao> implements IArticleService {

	@Autowired
	private IArticleTargetDao articleTargetDao;
	@Autowired
	private IArticleAttachmentDao articleAttachmentDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(ArticleModel model) {
		
		// 先保存文章内容
		int rt = getDao().insert(model);
		
		/**
		 * 文章发布对象
		 */
		List<ArticleTargetModel> targets = model.getTargets();
		if(CollectionUtils.isNotEmpty(targets)) {
			for (ArticleTargetModel targetModel : targets) {
				targetModel.setCid(model.getId());
				getArticleTargetDao().insert(targetModel);
			}
		}
		/**
		 * 文章封面
		 */
		ArticleAttachmentModel cover = model.getCover();
		if(!Objects.isNull(cover)) {
			cover.setCid(model.getId());
			cover.setType(1);
			getArticleAttachmentDao().insert(cover);
		}
		/**
		 * 文章附件
		 */
		List<ArticleAttachmentModel> atts = model.getAtts();
		if(CollectionUtils.isNotEmpty(atts)) {
			for (ArticleAttachmentModel attModel : atts) {
				if(!Objects.isNull(attModel)) {
					attModel.setCid(model.getId());
					attModel.setType(3);
					getArticleAttachmentDao().insert(attModel);
				}
			}
		}
		return rt;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(ArticleModel model) {
		
		// 先更新文章内容
		int rt = getDao().update(model);
		
		/**
		 * 文章发布对象
		 */
		
		List<ArticleTargetModel> targets = model.getTargets();
		// 查询发布范围
		List<ArticleTargetModel> oldTargets = getArticleTargetDao().getTargetList(model.getId());
		// 之前没有发布范围
		if(CollectionUtils.isEmpty(oldTargets) && CollectionUtils.isNotEmpty(targets)) {
			for (ArticleTargetModel targetModel : targets) {
				targetModel.setCid(model.getId());
				getArticleTargetDao().insert(targetModel);
			}
		} else if(CollectionUtils.isNotEmpty(oldTargets) && CollectionUtils.isNotEmpty(targets)) {
			// 保存新增的发布范围
			for (ArticleTargetModel targetModel : ArticleUtils.incrementTarget(targets, oldTargets)) {
				targetModel.setCid(model.getId());
				getArticleTargetDao().insert(targetModel);
			}
			// 删除移除的发布范围
			for (ArticleTargetModel targetModel : ArticleUtils.decrementTarget(targets, oldTargets)) {
				getArticleTargetDao().delete(targetModel.getId());
			}
		} else {
			// 没有设置发布范围
			getArticleTargetDao().deleteTarget(model.getId());
		}
		/**
		 * 文章封面
		 */
		ArticleAttachmentModel cover = model.getCover();
		if(!Objects.isNull(cover)) {
			cover.setType(1);
			getArticleAttachmentDao().update(cover);
		}
		/**
		 * 文章附件
		 */
		List<ArticleAttachmentModel> atts = model.getAtts();
		// 查询文章附件
		List<ArticleAttachmentModel> oldAtts = getArticleAttachmentDao().getAttachmentList(model.getId());
		// 之前没有文章附件
		if(CollectionUtils.isEmpty(oldAtts) && CollectionUtils.isNotEmpty(atts)) {
			for (ArticleAttachmentModel attModel : atts) {
				attModel.setType(3);
				getArticleAttachmentDao().update(attModel);
			}
		} else if(CollectionUtils.isNotEmpty(oldAtts) && CollectionUtils.isNotEmpty(atts)) {
			// 保存新增的文章附件
			for (ArticleAttachmentModel attModel : ArticleUtils.incrementAtt(atts, oldAtts)) {
				attModel.setCid(model.getId());
				getArticleAttachmentDao().insert(attModel);
			}
			// 删除移除的文章附件
			for (ArticleAttachmentModel attModel : ArticleUtils.decrementAtt(atts, oldAtts)) {
				getArticleAttachmentDao().delete(attModel.getId());
			}
		} else {
			// 没有设置文章附件
			getArticleAttachmentDao().deleteAtt(model.getId());
		}
		
		return rt;
	}
	
	
	@Override
	public int setRecommend(String id, String status) {
		return getDao().setRecommend(id, status);
	}

	@Override
	public int setReview(String id, String status) {
		return getDao().setReview(id, status);
	}

	@Override
	public ArticleDetailDTO getDetail(String id) {
		return getDao().getDetail(id);
	}

	public IArticleTargetDao getArticleTargetDao() {
		return articleTargetDao;
	}
	
	public IArticleAttachmentDao getArticleAttachmentDao() {
		return articleAttachmentDao;
	}

    
}