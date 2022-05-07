package io.hiwepy.admin.authz.feature.strategy;

import java.util.List;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.admin.authz.feature.enums.FeatureNodeType;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

/**
 * 数据格式策略
 */
public interface FeatureStrategy {

	/**
	 * 获取发送渠道
	 * @return
	 */
	FeatureNodeType getNodeType();

	List<AuthzFeatureTreeNode> handle(List<AuthzFeatureEntity> featureList);

	List<AuthzFeatureTreeNode> handle(List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList);

}
