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

public class FeatureTreeDataHandler implements FeatureDataHandler<List<AuthzFeatureDTO>> {

	@Override
	public List<AuthzFeatureDTO> handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		return FeatureNavUtils.getFeatureTreeList(featureList, featureOptList);
	}

}
