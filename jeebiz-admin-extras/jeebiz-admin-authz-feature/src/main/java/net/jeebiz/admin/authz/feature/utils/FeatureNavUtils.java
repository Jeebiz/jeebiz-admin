/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureOptDTO;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;

public final class FeatureNavUtils {
	
	protected static List<AuthzFeatureOptDTO> getFeatureOptList(AuthzFeatureModel feature, List<AuthzFeatureOptModel> featureOptList) {
		List<AuthzFeatureOptDTO> featureOpts = Lists.newArrayList();
		// 筛选当前菜单对应的操作按钮
		List<AuthzFeatureOptModel> optList = featureOptList.stream()
				.filter(featureOpt -> StringUtils.equals(feature.getId(), featureOpt.getFeatureId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(optList)){
			for (AuthzFeatureOptModel opt : optList) {
				AuthzFeatureOptDTO optDTO  = new AuthzFeatureOptDTO();
				// 功能菜单ID
				optDTO.setId(feature.getId() + "_" + opt.getId());
				// 功能操作名称
				optDTO.setName(opt.getName());
				// 功能操作图标样式
				optDTO.setIcon(opt.getIcon());
				// 功能操作排序
				optDTO.setOrder(opt.getOrder());
				// 功能菜单ID
				optDTO.setFeatureId( opt.getFeatureId());
				optDTO.setParent(opt.getFeatureId());
				// 功能操作是否可见(1:可见|0:不可见)
				optDTO.setVisible(opt.getVisible());
				// 功能操作权限标记
				optDTO.setPerms(opt.getPerms());
				// 是否已经授权
				optDTO.setChecked(StringUtils.equalsIgnoreCase(opt.getChecked(), "1"));
				
				featureOpts.add(optDTO);
			}
			return featureOpts.stream().sorted().collect(Collectors.toList());
		}
		return featureOpts;
	}
	
	protected static List<AuthzFeatureDTO> getSubFeatureList(AuthzFeatureModel parentNav,List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<AuthzFeatureModel> childFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals(parentNav.getId(), feature.getParent()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(childFeatureList)){
			for (AuthzFeatureModel feature : childFeatureList) {
				AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
				// 功能菜单ID
				featureDTO.setId(feature.getId());
				// 功能菜单简称
				featureDTO.setAbb(feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureDTO.setCode(feature.getCode());
				// 功能菜单名称
				featureDTO.setName(feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureDTO.setType(feature.getType());
				// 菜单样式或菜单图标路径
				featureDTO.setIcon(feature.getIcon());
				// 菜单显示顺序
				featureDTO.setOrder(feature.getOrder());
				// 父级功能菜单ID
				featureDTO.setParent(feature.getParent());
				featureDTO.setPid(feature.getParent());
				// 功能菜单URL
				featureDTO.setUrl(feature.getUrl());
				// 功能菜单对应页面相对路径
				featureDTO.setPath(feature.getPath());
				// 菜单是否可见(1:可见|0:不可见)
				featureDTO.setVisible(feature.getVisible());
				// 菜单所拥有的权限标记
				featureDTO.setPerms(feature.getPerms());
				// 判断是否是有子菜单
				boolean isParent = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				if(isParent){
					// 子菜单
					List<AuthzFeatureDTO> subFeatures = getSubFeatureList(feature, featureList, featureOptList);
					if(null != subFeatures && subFeatures.size() > 0) {
						boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setChildren(subFeatures);
					}
				} else {
					featureDTO.setLeaf(true);
					// 当前菜单的操作按钮
					List<AuthzFeatureOptDTO> featureOpts  = getFeatureOptList(feature, featureOptList);
					if(null != featureOpts && featureOpts.size() > 0) {
						boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setOpts( featureOpts);
					}
				}
				features.add(featureDTO);
			}
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}
	
	protected static List<AuthzFeatureDTO> getSubFeatureList(AuthzFeatureModel parentNav,List<AuthzFeatureModel> featureList) {
			
			List<AuthzFeatureDTO> features = Lists.newArrayList();
			//筛选当前父功能模块节点的子功能模块节点数据
			List<AuthzFeatureModel> childFeatureList = featureList.stream()
					.filter(feature -> StringUtils.equals(parentNav.getId(), feature.getParent()))
					.collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(childFeatureList)){
				for (AuthzFeatureModel feature : childFeatureList) {
					AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
					// 功能菜单ID
					featureDTO.setId(feature.getId());
					// 功能菜单简称
					featureDTO.setAbb(feature.getAbb());
					// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
					featureDTO.setCode(feature.getCode());
					// 功能菜单名称
					featureDTO.setName(feature.getName());
					// 菜单类型(1:原生|2:自定义)
					featureDTO.setType(feature.getType());
					// 菜单样式或菜单图标路径
					featureDTO.setIcon(feature.getIcon());
					// 菜单显示顺序
					featureDTO.setOrder(feature.getOrder());
					// 父级功能菜单ID
					featureDTO.setParent(feature.getParent());
					featureDTO.setPid(feature.getParent());
					// 功能菜单URL
					featureDTO.setUrl(feature.getUrl());
					// 功能菜单对应页面相对路径
					featureDTO.setPath(feature.getPath());
					// 菜单是否可见(1:可见|0:不可见)
					featureDTO.setVisible(feature.getVisible());
					// 菜单所拥有的权限标记
					featureDTO.setPerms(feature.getPerms());
					// 判断是否是有子菜单
					boolean isParent = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
					if(isParent){
						// 子菜单
						List<AuthzFeatureDTO> subFeatures = getSubFeatureList(feature, featureList);
						if(null != subFeatures && subFeatures.size() > 0) {
							boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
							if(checked) {
								featureDTO.setChecked(true);
							} else {
								featureDTO.setChecked(false);
							}
							featureDTO.setChildren(subFeatures);
						}
					}
					features.add(featureDTO);
				}
				return features.stream().sorted().collect(Collectors.toList());
			}
			return features;
		}

	/**
	 * 获取菜单树
	 * @param featureList
	 * @return
	 */
	public static List<AuthzFeatureDTO> getFeatureTreeList(List<AuthzFeatureModel> featureList) {
		
		// 优先获得最顶层的菜单集合
		List<AuthzFeatureModel> topFeatureList = featureList.stream()
				.filter(feature -> feature.isRoot())
				.collect(Collectors.toList());
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){
			
			for (AuthzFeatureModel feature : topFeatureList) {
				
				AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
				// 功能菜单ID
				featureDTO.setId(feature.getId());
				// 功能菜单简称
				featureDTO.setAbb(feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureDTO.setCode(feature.getCode());
				// 功能菜单名称
				featureDTO.setName(feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureDTO.setType(feature.getType());
				// 菜单样式或菜单图标路径
				featureDTO.setIcon(feature.getIcon());
				// 菜单显示顺序
				featureDTO.setOrder(feature.getOrder());
				// 父级功能菜单ID
				featureDTO.setParent(feature.getParent());
				featureDTO.setPid(feature.getParent());
				// 功能菜单URL
				featureDTO.setUrl(feature.getUrl());
				// 功能菜单对应页面相对路径
				featureDTO.setPath(feature.getPath());
				// 菜单是否可见(1:可见|0:不可见)
				featureDTO.setVisible(feature.getVisible());
				// 菜单所拥有的权限标记
				featureDTO.setPerms(feature.getPerms());
				// 判断是否是有子菜单
				boolean isParent = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				if(isParent){
					featureDTO.setLeaf(false);
					featureDTO.setLabel( feature.getName());
					// 子菜单
					List<AuthzFeatureDTO> subFeatures = getSubFeatureList(feature, featureList);
					// 有子菜单
					if(CollectionUtils.isNotEmpty(subFeatures)) {
						boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setChildren(subFeatures);
					}
				} 
				features.add(featureDTO);
			}
			
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}
	
	
	/**
	 * 获取菜单树
	 * @param featureList
	 * @param featureOptList
	 * @return
	 */
	public static List<AuthzFeatureDTO> getFeatureTreeList(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		// 优先获得最顶层的菜单集合
		List<AuthzFeatureModel> topFeatureList = featureList.stream()
				.filter(feature -> feature.isRoot())
				.collect(Collectors.toList());
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){
			
			for (AuthzFeatureModel feature : topFeatureList) {
				
				AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
				// 功能菜单ID
				featureDTO.setId(feature.getId());
				// 功能菜单简称
				featureDTO.setAbb(feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureDTO.setCode(feature.getCode());
				// 功能菜单名称
				featureDTO.setName(feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureDTO.setType(feature.getType());
				// 菜单样式或菜单图标路径
				featureDTO.setIcon(feature.getIcon());
				// 菜单显示顺序
				featureDTO.setOrder(feature.getOrder());
				// 父级功能菜单ID
				featureDTO.setParent(feature.getParent());
				featureDTO.setPid(feature.getParent());
				// 功能菜单URL
				featureDTO.setUrl(feature.getUrl());
				// 功能菜单对应页面相对路径
				featureDTO.setPath(feature.getPath());
				// 菜单是否可见(1:可见|0:不可见)
				featureDTO.setVisible(feature.getVisible());
				// 菜单所拥有的权限标记
				featureDTO.setPerms(feature.getPerms());
				// 判断是否是有子菜单
				boolean isParent = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				if(isParent){
					featureDTO.setLeaf(false);
					featureDTO.setLabel( feature.getName());
					// 子菜单
					List<AuthzFeatureDTO> subFeatures = getSubFeatureList(feature, featureList, featureOptList);
					// 有子菜单
					if(CollectionUtils.isNotEmpty(subFeatures)) {
						boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setChildren(subFeatures);
					}
				} else {
					featureDTO.setLeaf(true);
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
					// 当前菜单的操作按钮
					List<AuthzFeatureOptDTO> featureOpts = getFeatureOptList(feature, featureOptList);
					if(null != featureOpts && featureOpts.size() > 0) {
						boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setOpts(featureOpts);
					}
				}
				//System.out.println(JSONObject.toJSONString(featureDTO));
				features.add(featureDTO);
			}
			
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}
	
	public static List<AuthzFeatureDTO> getFeatureFlatList(List<AuthzFeatureModel> featureList) {
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		for (AuthzFeatureModel feature : featureList) {
			
			AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
			// 功能菜单ID
			featureDTO.setId(feature.getId());
			// 功能菜单简称
			featureDTO.setAbb(feature.getAbb());
			// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
			featureDTO.setCode(feature.getCode());
			// 功能菜单名称
			featureDTO.setName(feature.getName());
			// 菜单类型(1:原生|2:自定义)
			featureDTO.setType(feature.getType());
			// 菜单样式或菜单图标路径
			featureDTO.setIcon(feature.getIcon());
			// 菜单显示顺序
			featureDTO.setOrder(feature.getOrder());
			// 父级功能菜单ID
			featureDTO.setParent(feature.getParent());
			featureDTO.setPid(feature.getParent());
			// 功能菜单URL
			featureDTO.setUrl(feature.getUrl());
			// 功能菜单对应页面相对路径
			featureDTO.setPath(feature.getPath());
			// 菜单是否可见(1:可见|0:不可见)
			featureDTO.setVisible(feature.getVisible());
			// 菜单所拥有的权限标记
			featureDTO.setPerms(feature.getPerms());
			features.add(featureDTO);
		}
		
		return features.stream().sorted().collect(Collectors.toList());
	}
	
	public static List<AuthzFeatureDTO> getFeatureFlatList(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		// 筛选菜单中的末节点的功能菜单
		List<AuthzFeatureModel> leafFeatureList = featureList.stream()
				.filter(feature -> StringUtils.isNotEmpty(feature.getParent())
						&& !StringUtils.equals("0", feature.getParent()) && !StringUtils.equals("#", feature.getUrl()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(leafFeatureList)){
			
			for (AuthzFeatureModel feature : leafFeatureList) {
				
				AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
				// 功能菜单ID
				featureDTO.setId(feature.getId());
				// 功能菜单简称
				featureDTO.setAbb(feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureDTO.setCode(feature.getCode());
				// 功能菜单名称
				featureDTO.setName(feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureDTO.setType(feature.getType());
				// 菜单样式或菜单图标路径
				featureDTO.setIcon(feature.getIcon());
				// 菜单显示顺序
				featureDTO.setOrder(feature.getOrder());
				// 父级功能菜单ID
				featureDTO.setParent(feature.getParent());
				featureDTO.setPid(feature.getParent());
				// 功能菜单URL
				featureDTO.setUrl(feature.getUrl());
				// 功能菜单对应页面相对路径
				featureDTO.setPath(feature.getPath());
				// 菜单是否可见(1:可见|0:不可见)
				featureDTO.setVisible(feature.getVisible());
				// 菜单所拥有的权限标记
				featureDTO.setPerms(feature.getPerms());
				// 判断是否是有父菜单
				boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
				if(isLeaf){
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
				} else {
					featureDTO.setLeaf(isLeaf);
					featureDTO.setLabel( feature.getName());
				}
				
				// 当前菜单的操作按钮
				List<AuthzFeatureOptDTO> featureOpts  = getFeatureOptList(feature, featureOptList);
				if(null != featureOpts && featureOpts.size() > 0) {
					boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
					if(checked) {
						featureDTO.setChecked(true);
					} else {
						featureDTO.setChecked(false);
					}
					featureDTO.setOpts(featureOpts);
				}
				
				features.add(featureDTO);
			}
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}
	
	protected static StringBuilder getLabel(StringBuilder builder,AuthzFeatureModel leaf,List<AuthzFeatureModel> featureList) {
		// 获取该菜单的父菜单
		List<AuthzFeatureModel> parentFeatureList = featureList.stream()
				.filter(feature ->  StringUtils.equals(leaf.getParent(), feature.getId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(parentFeatureList)) {
			// 只有一个父亲，只会循环一次
			for (AuthzFeatureModel feature : parentFeatureList) {
				builder.insert(0, "/").insert(0, feature.getName());
				// Url属性不为#表示有父级菜单
				if(!StringUtils.equals("#", feature.getUrl())){
					getLabel(builder , feature, featureList);
				}
			}
		}
		return builder;
	}
	
	/**
	 * @param features		： 所有的功能菜单
	 * @param ownFeatures	： 用户/角色拥有的菜单
	 * @return
	 */
	public static List<AuthzFeatureModel> getFeatureMergedList(List<AuthzFeatureModel> features,
			List<AuthzFeatureModel> ownFeatures) {
		// 定义合并后的功能菜单集合对象
		List<AuthzFeatureModel> mergedFeatureList = Lists.newArrayList();
		// 循环用户拥有的菜单
		for (AuthzFeatureModel feature : ownFeatures) {
			mergedFeatureList.add(feature);
			// 从系统菜单中获取当前菜单的所有的父级菜单，并加入合并集合
			getParants(mergedFeatureList, feature, features );
		}
		// 去除重复的菜单
		List<AuthzFeatureModel> mergedFeatures = Lists.newArrayList();
		for (AuthzFeatureModel feature : mergedFeatureList) {
			 boolean b = mergedFeatures.stream().anyMatch(u -> u.getId().equals(feature.getId()));
		     if (!b) {
		    	 mergedFeatures.add(feature);
		     }
		}
		return mergedFeatures;
	}
	
	/**
	 * 
	 * @param mergedFeatures 	： 合并后的功能菜单集合对象
	 * @param currentFeature	： 用户拥有的菜单
	 * @param featureList		： 所有的功能菜单
	 * @return
	 */
	protected static List<AuthzFeatureModel> getParants(List<AuthzFeatureModel> mergedFeatures, AuthzFeatureModel currentFeature, List<AuthzFeatureModel> featureList) {
		// 获取该菜单的父菜单
		List<AuthzFeatureModel> parentFeatureList = featureList.stream()
				.filter(feature ->  StringUtils.equals(currentFeature.getParent(), feature.getId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(parentFeatureList)) {
			// 只有一个父亲，只会循环一次
			for (AuthzFeatureModel prentFeature : parentFeatureList) {
				mergedFeatures.add(prentFeature);
				getParants(mergedFeatures, prentFeature, featureList );
			}
		}
		return parentFeatureList;
	}
	
	/**
	 * 根据全部菜单和角色具有的操作权限，组织处最终的菜单树
	 * @param featureList
	 * @param featureOptList
	 * @return
	 */
	public static List<AuthzFeatureDTO> getFeatureTree(List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		//优先获得最顶层的菜单集合
		List<AuthzFeatureModel> topFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals("#", feature.getUrl()))
				.collect(Collectors.toList());
		List<AuthzFeatureDTO> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){
			
			for (AuthzFeatureModel feature : topFeatureList) {
				
				AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
				// 功能菜单ID
				featureDTO.setId(feature.getId());
				// 功能菜单简称
				featureDTO.setAbb(feature.getAbb());
				// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				featureDTO.setCode(feature.getCode());
				// 功能菜单名称
				featureDTO.setName(feature.getName());
				// 菜单类型(1:原生|2:自定义)
				featureDTO.setType(feature.getType());
				// 菜单样式或菜单图标路径
				featureDTO.setIcon(feature.getIcon());
				// 菜单显示顺序
				featureDTO.setOrder(feature.getOrder());
				// 父级功能菜单ID
				featureDTO.setParent(feature.getParent());
				featureDTO.setPid(feature.getParent());
				// 功能菜单URL
				featureDTO.setUrl(feature.getUrl());
				// 功能组件路径
				featureDTO.setPath(feature.getPath());
				// 菜单是否可见(1:可见|0:不可见)
				featureDTO.setVisible(feature.getVisible());
				// 菜单所拥有的权限标记
				featureDTO.setPerms(feature.getPerms());
				// 判断是否是有父菜单
				boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
				if(isLeaf){
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
				} else {
					featureDTO.setLeaf(isLeaf);
					featureDTO.setLabel( feature.getName());
				}
				// 子菜单
				List<AuthzFeatureDTO> subFeatures  = getSubFeatureList(feature, featureList, featureOptList);
				if(null != subFeatures && subFeatures.size() > 0) {
					boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
					if(checked) {
						featureDTO.setChecked(true);
					} else {
						featureDTO.setChecked(false);
					}
					featureDTO.setChildren(subFeatures);
				}
				features.add(featureDTO);
			}
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}

	public static AuthzFeatureDTO getFeatureTree(String servId, List<AuthzFeatureModel> featureList, List<AuthzFeatureOptModel> featureOptList) {
		
		AuthzFeatureDTO featureDTO = new AuthzFeatureDTO();
		if(CollectionUtils.isEmpty(featureList)) {
			return featureDTO;
		}
		
		//优先获得最顶层的菜单集合
		AuthzFeatureModel feature = featureList.stream()
				.filter(f -> StringUtils.equals(servId, f.getParent()))
				.findFirst().get();
		
		// 功能菜单ID
		featureDTO.setId(feature.getId());
		// 功能菜单简称
		featureDTO.setAbb(feature.getAbb());
		// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
		featureDTO.setCode(feature.getCode());
		// 功能菜单名称
		featureDTO.setName(feature.getName());
		// 菜单类型(1:原生|2:自定义)
		featureDTO.setType(feature.getType());
		// 菜单样式或菜单图标路径
		featureDTO.setIcon(feature.getIcon());
		// 菜单显示顺序
		featureDTO.setOrder(feature.getOrder());
		// 父级功能菜单ID
		featureDTO.setParent(feature.getParent());
		featureDTO.setPid(feature.getParent());
		// 功能菜单URL
		featureDTO.setUrl(feature.getUrl());
		// 功能组件路径
		featureDTO.setPath(feature.getPath());
		// 菜单是否可见(1:可见|0:不可见)
		featureDTO.setVisible(feature.getVisible());
		// 菜单所拥有的权限标记
		featureDTO.setPerms(feature.getPerms());
		// 判断是否是有父菜单
		boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
		if(isLeaf){
			featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
		} else {
			featureDTO.setLeaf(isLeaf);
			featureDTO.setLabel( feature.getName());
		}
		// 子菜单
		List<AuthzFeatureDTO> subFeatures  = getSubFeatureList(feature, featureList, featureOptList);
		if(CollectionUtils.isNotEmpty(subFeatures)) {
			featureDTO.setChecked(true);
			featureDTO.setChildren(subFeatures.stream().filter(item -> item.isChecked()).collect(Collectors.toList()));
		}
		
		return featureDTO;
	}
	
}
