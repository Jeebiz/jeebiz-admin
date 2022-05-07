/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.admin.authz.feature.enums.FeatureNodeType;
import io.hiwepy.admin.authz.feature.utils.FeatureNavUtils;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

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
