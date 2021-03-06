/** 
 * Copyright (C) 2019 杭州之乎者也科技有限公司 (http://knowway.cn).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.article.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentModel;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTargetModel;

public class ArticleUtils {

	/**
	 * 获取附件增量集合
	 * @param atts		：此次提交的附件
	 * @param oldAtts	： 已经保存附件
	 * @return
	 */
	public static List<ArticleAttachmentModel> incrementAtt(List<ArticleAttachmentModel> atts, List<ArticleAttachmentModel> oldAtts){
		if(CollectionUtils.isEmpty(atts) || CollectionUtils.isEmpty(oldAtts)) {
			return Lists.newArrayList();
		}
		return atts.stream()
				.filter(att -> !oldAtts.stream().anyMatch(oldAtt -> StringUtils.equalsIgnoreCase(att.getId(), oldAtt.getId())))
                .collect(Collectors.toList());
	}
	
	/**
	 * 获取附件减量集合
	 * @param atts		：此次提交的附件
	 * @param oldAtts	： 已经保存附件
	 * @return
	 */
	public static List<ArticleAttachmentModel> decrementAtt(List<ArticleAttachmentModel> atts, List<ArticleAttachmentModel> oldAtts){
		if(CollectionUtils.isEmpty(oldAtts)) {
			return Lists.newArrayList(atts);
		}
		return oldAtts.stream()
				.filter(oldAtt -> !atts.stream().anyMatch(att -> StringUtils.equalsIgnoreCase(att.getId(), oldAtt.getId())))
                .collect(Collectors.toList());
	}
	
	/**
	 * 获取发布范围增量集合
	 * @param targets		：此次提交的发布范围
	 * @param oldTargets	： 已经发布范围
	 * @return
	 */
	public static List<ArticleTargetModel> incrementTarget(List<ArticleTargetModel> targets, List<ArticleTargetModel> oldTargets){
		if(CollectionUtils.isEmpty(targets) || CollectionUtils.isEmpty(oldTargets)) {
			return Lists.newArrayList();
		}
		return targets.stream()
				.filter(target -> !oldTargets.stream().anyMatch(oldTarget -> StringUtils.equalsIgnoreCase(target.getId(), oldTarget.getId())))
                .collect(Collectors.toList());
	}
	
	/**
	 * 获取发布范围减量集合
	 * @param targets		：此次提交的发布范围
	 * @param oldTargets	： 已经发布范围
	 * @return
	 */
	public static List<ArticleTargetModel> decrementTarget(List<ArticleTargetModel> targets, List<ArticleTargetModel> oldTargets){
		if(CollectionUtils.isEmpty(oldTargets)) {
			return Lists.newArrayList(targets);
		}
		return oldTargets.stream()
				.filter(oldTarget -> !targets.stream().anyMatch(target -> StringUtils.equalsIgnoreCase(target.getId(), oldTarget.getId())))
                .collect(Collectors.toList());
	}
	
}
