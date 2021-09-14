package net.jeebiz.admin.authz.feature.strategy;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.enums.FeatureNodeType;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

/**
 * 数据格式策略
 */
public interface FeatureStrategy {

	/**
	 * 获取发送渠道
	 * @return
	 */
	FeatureNodeType getNodeType();

	List<AuthzFeatureTreeNode> handle(List<AuthzFeatureModel> featureList);
	
	List<AuthzFeatureTreeNode> handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList);
	
}
