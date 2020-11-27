/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.setup.handler;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;

public class FeatureDataHandlerFactory {

	private static Map<String, FeatureDataHandler<List<AuthzFeatureDTO>>> COMPLETED_HANDLER = Maps.newConcurrentMap();
	private static FeatureTreeDataHandler featureTreeDataHandler = new FeatureTreeDataHandler();
	private static FeatureFlatDataHandler featureFlatDataHandler = new FeatureFlatDataHandler();
	
	static {
		COMPLETED_HANDLER.put("tree", featureTreeDataHandler);
		COMPLETED_HANDLER.put("flat", featureFlatDataHandler);
	}
	
	public static FeatureDataHandler<List<AuthzFeatureDTO>> newHandler(String key, FeatureDataHandler<List<AuthzFeatureDTO>> handler) {
		FeatureDataHandler<List<AuthzFeatureDTO>> rt = COMPLETED_HANDLER.get(key);
		if(rt != null) {
			return rt;
		}
		return COMPLETED_HANDLER.putIfAbsent(key, handler);
	}
	
	public static FeatureDataHandler<List<AuthzFeatureDTO>> getTreeHandler() {
		return featureTreeDataHandler;
	}
	
	public static FeatureDataHandler<List<AuthzFeatureDTO>> getTreeHandler(String key) {
		if(StringUtils.hasText(key)) {
			return COMPLETED_HANDLER.getOrDefault(key, featureTreeDataHandler);
		}
		return featureTreeDataHandler;
	}
	
	public static FeatureDataHandler<List<AuthzFeatureDTO>> getFlatHandler() {
		return featureFlatDataHandler;
	}
	
	public static FeatureDataHandler<List<AuthzFeatureDTO>> getFlatHandler(String key) {
		if(StringUtils.hasText(key)) {
			return COMPLETED_HANDLER.getOrDefault(key, featureFlatDataHandler);
		}
		return featureFlatDataHandler;
	}
	
	
}
