/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.setup.handler;

import java.util.List;

import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.utils.FeatureNavUtils;

public class FeatureFlatDataHandler implements FeatureDataHandler {

	@Override
	public Object handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		return FeatureNavUtils.getFeatureFlatList(featureList, featureOptList);
	}

}
