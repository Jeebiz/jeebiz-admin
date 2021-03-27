/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.setup.handler;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.utils.FeatureNavUtils;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;

public class FeatureFlatDataHandler implements FeatureDataHandler<List<AuthzFeatureDTO>> {

	@Override
	public List<AuthzFeatureDTO> handle(List<AuthzFeatureModel> featureList) {
		return FeatureNavUtils.getFeatureFlatList(featureList);
	}
	
	@Override
	public List<AuthzFeatureDTO> handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		return FeatureNavUtils.getFeatureFlatList(featureList, featureOptList);
	}


}
