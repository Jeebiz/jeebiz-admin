/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;

public final class FeatureTreeUtils {
	
	protected static List<Map<String, Object>> getFeatureOptList(AuthzFeatureModel feature, List<AuthzFeatureOptModel> featureOptList) {
		List<Map<String, Object>> featureOpts = Lists.newArrayList();
		// 筛选当前菜单对应的操作按钮
		List<AuthzFeatureOptModel> optList = featureOptList.stream()
				.filter(featureOpt -> StringUtils.equals(feature.getId(), featureOpt.getFeatureId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(optList)){
			for (AuthzFeatureOptModel opt : optList) {
				Map<String, Object> optMap  = Maps.newHashMap();
				// 功能菜单ID
				optMap.put("id", feature.getId() + "_" + opt.getId());
				// 功能操作名称
				optMap.put("name", opt.getName());
				// 功能操作图标样式
				optMap.put("icon", opt.getIcon());
				// 功能操作排序
				optMap.put("order", opt.getOrder());
				// 功能菜单ID
				optMap.put("featureId", opt.getFeatureId());
				// 功能操作是否可见(1:可见|0:不可见)
				optMap.put("visible", opt.getVisible());
				// 功能操作权限标记
				optMap.put("perms", opt.getPerms());
				optMap.put("value", opt.getPerms());
				// 是否选中
				optMap.put("checked", StringUtils.equalsIgnoreCase("1", opt.getChecked()));
				
				featureOpts.add(optMap);
			}
			Collections.sort(featureOpts, new Comparator<Map<String, Object>>() {
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	                return String.valueOf(o1.get("order")).compareTo(String.valueOf(o2.get("order")));
	            }
	        });
			return featureOpts;
		}
		return featureOpts;
	}
	
	protected static List<Map<String, Object>> getSubFeatureList(AuthzFeatureModel parentNav,List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		List<Map<String, Object>> features = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<AuthzFeatureModel> childFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals(parentNav.getId(), feature.getParent()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(childFeatureList)){
			for (AuthzFeatureModel feature : childFeatureList) {
				
				Map<String, Object> featureMap = Maps.newHashMap();
				// 功能菜单ID
				featureMap.put("id", feature.getId());
				featureMap.put("value", feature.getId());
				// 功能菜单简称
				featureMap.put("abb", feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureMap.put("code", feature.getCode());
				// 功能菜单名称
				featureMap.put("name", feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureMap.put("type",feature.getType());
				// 菜单样式或菜单图标路径
				featureMap.put("icon",feature.getIcon());
				// 菜单显示顺序
				featureMap.put("order",feature.getOrder());
				// 父级功能菜单ID
				featureMap.put("parent",feature.getParent());
				// 功能菜单URL
				featureMap.put("url", feature.getUrl());
				// 菜单是否可见(1:可见|0:不可见)
				featureMap.put("visible", feature.getVisible());
				// 菜单所拥有的权限标记
				featureMap.put("perms", feature.getPerms());
				// 路径为#表示有子菜单
				if(StringUtils.equals("#", feature.getUrl())){
					featureMap.put("list", getSubFeatureList(feature, featureList, featureOptList));
				} else {
					// 当前菜单的操作按钮
					List<Map<String, Object>> featureOpts  = getFeatureOptList(feature, featureOptList);
					if(null != featureOpts && featureOpts.size() > 0) {
						featureMap.put("list", featureOpts);
					}
				}
				features.add(featureMap);
			}
			Collections.sort(features, new Comparator<Map<String, Object>>() {
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	                return String.valueOf(o1.get("order")).compareTo(String.valueOf(o2.get("order")));
	            }
	        });
			return features;
		}
		return features;
	}
	
	public static List<Map<String, Object>> getAuthTreeList(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		//优先获得最顶层的菜单集合
		List<AuthzFeatureModel> topFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals("0", feature.getParent()))
				.collect(Collectors.toList());
		List<Map<String, Object>> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){
			
			for (AuthzFeatureModel feature : topFeatureList) {
				
				Map<String, Object> featureMap = Maps.newHashMap();
				// 功能菜单ID
				featureMap.put("id", feature.getId());
				featureMap.put("value", feature.getId());
				// 功能菜单简称
				featureMap.put("abb", feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureMap.put("code", feature.getCode());
				// 功能菜单名称
				featureMap.put("name", feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureMap.put("type",feature.getType());
				// 菜单样式或菜单图标路径
				featureMap.put("icon",feature.getIcon());
				// 菜单显示顺序
				featureMap.put("order",feature.getOrder());
				// 父级功能菜单ID
				featureMap.put("parent",feature.getParent());
				// 功能菜单URL
				featureMap.put("url", feature.getUrl());
				// 菜单是否可见(1:可见|0:不可见)
				featureMap.put("visible", feature.getVisible());
				// 菜单所拥有的权限标记
				featureMap.put("perms", feature.getPerms());
				// 子菜单
				List<Map<String, Object>> subFeatures  = getSubFeatureList(feature, featureList, featureOptList);
				if(null != subFeatures && subFeatures.size() > 0) {
					featureMap.put("list", subFeatures);
				}
				features.add(featureMap);
			}
			Collections.sort(features, new Comparator<Map<String, Object>>() {
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	                return String.valueOf(o1.get("order")).compareTo(String.valueOf(o2.get("order")));
	            }
	        });
			return features;
		}
		return features;
	}
	
}