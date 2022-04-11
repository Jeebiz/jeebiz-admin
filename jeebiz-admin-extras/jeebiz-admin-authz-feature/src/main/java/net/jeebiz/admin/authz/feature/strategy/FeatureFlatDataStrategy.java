/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import net.jeebiz.admin.authz.feature.enums.FeatureNodeType;
import net.jeebiz.admin.authz.feature.utils.FeatureNavUtils;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

@Component
public class FeatureFlatDataStrategy implements FeatureStrategy {

	@Override
	public FeatureNodeType getNodeType() {
		return FeatureNodeType.FLAT;
	}

	@Override
	public List<AuthzFeatureTreeNode> handle(List<AuthzFeatureEntity> featureList) {
		return FeatureNavUtils.getFeatureFlatList(featureList);
	}

	@Override
	public List<AuthzFeatureTreeNode> handle(List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {
		return FeatureNavUtils.getFeatureFlatList(featureList, featureOptList);
	}

}
