/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import io.hiwepy.admin.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.admin.authz.feature.enums.FeatureNodeType;
import io.hiwepy.admin.authz.feature.utils.FeatureNavUtils;
import io.hiwepy.admin.authz.feature.web.dto.FeatureTreeNode;

@Component
public class FeatureTreeDataStrategy implements FeatureStrategy {

	@Override
	public FeatureNodeType getNodeType() {
		return FeatureNodeType.TREE;
	}

	@Override
	public List<FeatureTreeNode> handle(List<FeatureEntity> featureList) {
		return FeatureNavUtils.getFeatureTreeList(featureList);
	}

	@Override
	public List<FeatureTreeNode> handle(List<FeatureEntity> featureList, List<FeatureOptEntity> featureOptList) {
		return FeatureNavUtils.getFeatureTreeList(featureList, featureOptList);
	}

}
