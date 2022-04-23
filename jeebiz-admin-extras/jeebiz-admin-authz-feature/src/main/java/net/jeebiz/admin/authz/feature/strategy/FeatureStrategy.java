package net.jeebiz.admin.authz.feature.strategy;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.FeatureEntity;
import net.jeebiz.admin.authz.feature.dao.entities.FeatureOptEntity;
import net.jeebiz.admin.authz.feature.enums.FeatureNodeType;
import net.jeebiz.admin.authz.feature.web.dto.FeatureTreeNode;

/**
 * 数据格式策略
 */
public interface FeatureStrategy {

	/**
	 * 获取发送渠道
	 * @return
	 */
	FeatureNodeType getNodeType();

	List<FeatureTreeNode> handle(List<FeatureEntity> featureList);

	List<FeatureTreeNode> handle(List<FeatureEntity> featureList, List<FeatureOptEntity> featureOptList);

}
