/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.strategy;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.enums.FeatureNodeType;
import net.jeebiz.admin.authz.feature.utils.FeatureNavUtils;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

public class FeatureTreeDataStrategy implements FeatureStrategy {

	@Override
	public FeatureNodeType getNodeType() {
		return FeatureNodeType.TREE;
	}

	@Override
	public List<AuthzFeatureTreeNode> handle(List<AuthzFeatureModel> featureList) {
		return FeatureNavUtils.getFeatureTreeList(featureList);
	}

	@Override
	public List<AuthzFeatureTreeNode> handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		return FeatureNavUtils.getFeatureTreeList(featureList, featureOptList);
	}

}
