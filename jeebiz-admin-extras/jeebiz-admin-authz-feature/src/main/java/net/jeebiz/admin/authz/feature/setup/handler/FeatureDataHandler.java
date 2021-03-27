/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.setup.handler;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;

public interface FeatureDataHandler<T> {

	T handle(List<AuthzFeatureModel> featureList);
	
	T handle(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList);
	
}
