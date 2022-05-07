/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureTreeNode;

public final class FeatureNavUtils {

	protected static List<AuthzFeatureTreeNode> getFeatureOptList(AuthzFeatureEntity feature, List<AuthzFeatureOptEntity> featureOptList) {
		List<AuthzFeatureTreeNode> featureOpts = Lists.newArrayList();
		// 筛选当前菜单对应的操作按钮
		List<AuthzFeatureOptEntity> optList = featureOptList.stream()
				.filter(featureOpt -> StringUtils.equals(feature.getId(), featureOpt.getFeatureId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(optList)){
			for (AuthzFeatureOptEntity opt : optList) {
				AuthzFeatureTreeNode optDTO  = new AuthzFeatureTreeNode();
				// 功能菜单id
				optDTO.setId(feature.getId() + "_" + opt.getId());
				// 功能操作简称
				optDTO.setAbb(opt.getName());
				// 功能操作名称
				optDTO.setName(opt.getName());
				optDTO.setLabel(opt.getName());
				// 功能操作是叶子节点
				optDTO.setLeaf(true);
				// 功能操作图标样式
				optDTO.setIcon(opt.getIcon());
				// 功能操作排序
				optDTO.setOrder(Integer.parseInt(StringUtils.trim(opt.getOrderBy())));
				// 功能菜单id
				optDTO.setPid(Integer.parseInt(StringUtils.trim(opt.getFeatureId())));
				// 功能操作权限标记
				optDTO.setPerms(opt.getPerms());
				// 是否已经授权
				optDTO.setChecked(StringUtils.equalsIgnoreCase(opt.getChecked(), "1"));

				featureOpts.add(optDTO);
			}
			return featureOpts.stream().sorted(Comparator.comparing(AuthzFeatureTreeNode::getOrder)).collect(Collectors.toList());
		}
		return featureOpts;
	}

	protected static List<AuthzFeatureTreeNode> getSubFeatureList(AuthzFeatureEntity parentNav, List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {

		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<AuthzFeatureEntity> childFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals(parentNav.getId(), feature.getParent()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(childFeatureList)){
			for (AuthzFeatureEntity feature : childFeatureList) {

				AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
				// 判断是否是有子菜单
				boolean hasChildren = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				featureDTO.setRoot(!hasChildren);
				if(hasChildren){
					// 子菜单
					List<AuthzFeatureTreeNode> subFeatures = getSubFeatureList(feature, featureList, featureOptList);
					if(CollectionUtils.isNotEmpty(subFeatures)) {
						boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setLeaf(false);
						featureDTO.setChildren(subFeatures);
					}
				} else {
					// 当前菜单的操作按钮
					List<AuthzFeatureTreeNode> featureOpts  = getFeatureOptList(feature, featureOptList);
					if(CollectionUtils.isNotEmpty(featureOpts)) {
						boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setLeaf(false);
						featureDTO.setChildren( featureOpts);
					}
				}
				features.add(featureDTO);
			}
			return features.stream().sorted(Comparator.comparing(AuthzFeatureTreeNode::getPid).thenComparing(AuthzFeatureTreeNode::getOrder)).collect(Collectors.toList());
		}
		return features;
	}

	protected static List<AuthzFeatureTreeNode> getSubFeatureList(AuthzFeatureEntity parentNav, List<AuthzFeatureEntity> featureList) {

			List<AuthzFeatureTreeNode> features = Lists.newArrayList();
			//筛选当前父功能模块节点的子功能模块节点数据
			List<AuthzFeatureEntity> childFeatureList = featureList.stream()
					.filter(feature -> StringUtils.equals(parentNav.getId(), feature.getParent()))
					.collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(childFeatureList)){
				for (AuthzFeatureEntity feature : childFeatureList) {
					AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
					// 判断是否是有子菜单
					boolean hasChildren = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
					featureDTO.setRoot(!hasChildren);
					if(hasChildren){
						// 子菜单
						List<AuthzFeatureTreeNode> subFeatures = getSubFeatureList(feature, featureList);
						if(CollectionUtils.isNotEmpty(subFeatures)) {
							boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
							if(checked) {
								featureDTO.setChecked(true);
							} else {
								featureDTO.setChecked(false);
							}
							featureDTO.setLeaf(false);
							featureDTO.setChildren(subFeatures);
						}
					}
					features.add(featureDTO);
				}
				return features.stream().sorted(Comparator.comparing(AuthzFeatureTreeNode::getPid).thenComparing(AuthzFeatureTreeNode::getOrder)).collect(Collectors.toList());
			}
			return features;

		}


	protected static AuthzFeatureTreeNode toTreeNode(AuthzFeatureEntity feature){
		AuthzFeatureTreeNode featureDTO = new AuthzFeatureTreeNode();
		// 功能菜单id
		featureDTO.setId(feature.getId());
		// 功能菜单简称
		featureDTO.setAbb(feature.getAbb());
		// 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
		featureDTO.setCode(feature.getCode());
		// 功能菜单名称
		featureDTO.setName(feature.getName());
		featureDTO.setLabel( feature.getName());
		// 默认指定为叶子节点，下面根据是否有子菜单或功能按钮进行调整
		featureDTO.setLeaf(true);
		featureDTO.setRoot(feature.isRoot());
		// 菜单类型(1:原生|2:自定义)
		featureDTO.setType(feature.getType());
		// 菜单样式或菜单图标路径
		featureDTO.setIcon(feature.getIcon());
		// 菜单显示顺序
		featureDTO.setOrder(Integer.parseInt(StringUtils.trim(feature.getOrderBy())));
		// 父级功能菜单id
		featureDTO.setPid(Integer.parseInt(StringUtils.trim(feature.getParent())));
		// 功能菜单地址
		featureDTO.setPath(feature.getUrl());
		// 功能菜单对应页面相对路径
		featureDTO.setComponent(feature.getPath());
		// 菜单所拥有的权限标记
		featureDTO.setPerms(feature.getPerms());

		return featureDTO;
	}

	/**
	 * 获取菜单树
	 * @param featureList
	 * @return
	 */
	public static List<AuthzFeatureTreeNode> getFeatureTreeList(List<AuthzFeatureEntity> featureList) {

		// 优先获得最顶层的菜单集合
		List<AuthzFeatureEntity> topFeatureList = featureList.stream()
				.filter(feature -> feature.isRoot())
				.collect(Collectors.toList());
		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){

			for (AuthzFeatureEntity feature : topFeatureList) {

				AuthzFeatureTreeNode featureDTO = toTreeNode(feature);

				// 判断是否是有子菜单
				boolean hasChildren = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				featureDTO.setRoot(!hasChildren);
				if(hasChildren){
					// 子菜单
					List<AuthzFeatureTreeNode> subFeatures = getSubFeatureList(feature, featureList);
					// 有子菜单
					if(CollectionUtils.isNotEmpty(subFeatures)) {
						boolean checked = subFeatures.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setLeaf(false);
						featureDTO.setChildren(subFeatures);
					}
				}
				features.add(featureDTO);
			}

			return features.stream().sorted(Comparator.comparing(AuthzFeatureTreeNode::getPid).thenComparing(AuthzFeatureTreeNode::getOrder)).collect(Collectors.toList());
		}
		return features;
	}


	/**
	 * 获取菜单树
	 * @param featureList
	 * @param featureOptList
	 * @return
	 */
	public static List<AuthzFeatureTreeNode> getFeatureTreeList(List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {

		// 优先获得最顶层的菜单集合
		List<AuthzFeatureEntity> topFeatureList = featureList.stream()
				.filter(feature -> feature.isRoot())
				.collect(Collectors.toList());
		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){

			for (AuthzFeatureEntity feature : topFeatureList) {

				AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
				// 判断是否是有子菜单
				boolean hasChildren = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), feature.getId()));
				featureDTO.setRoot(!hasChildren);
				if(hasChildren){
					featureDTO.setLeaf(false);
					featureDTO.setLabel( feature.getName());
					// 子菜单
					List<AuthzFeatureTreeNode> subFeatures = getSubFeatureList(feature, featureList, featureOptList);
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
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
					// 当前菜单的操作按钮
					List<AuthzFeatureTreeNode> featureOpts = getFeatureOptList(feature, featureOptList);
					if(CollectionUtils.isNotEmpty(featureOpts)) {
						boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
						if(checked) {
							featureDTO.setChecked(true);
						} else {
							featureDTO.setChecked(false);
						}
						featureDTO.setChildren(featureOpts);
					}
				}
				features.add(featureDTO);
			}

			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}

	public static List<AuthzFeatureTreeNode> getFeatureFlatList(List<AuthzFeatureEntity> featureList) {
		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		for (AuthzFeatureEntity feature : featureList) {
			AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
			features.add(featureDTO);
		}
		return features.stream().sorted(Comparator.comparing(AuthzFeatureTreeNode::getPid).thenComparing(AuthzFeatureTreeNode::getOrder)).collect(Collectors.toList());
	}

	public static List<AuthzFeatureTreeNode> getFeatureFlatList(List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {
		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		// 筛选菜单中的末节点的功能菜单
		List<AuthzFeatureEntity> leafFeatureList = featureList.stream()
				.filter(feature -> StringUtils.isNotEmpty(feature.getParent())
						&& !StringUtils.equals("0", feature.getParent()) && !StringUtils.equals("#", feature.getUrl()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(leafFeatureList)){

			for (AuthzFeatureEntity feature : leafFeatureList) {
				AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
				// 判断是否是有父菜单
				boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
				if(isLeaf){
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
				} else {
					featureDTO.setLeaf(isLeaf);
					featureDTO.setLabel( feature.getName());
				}
				// 当前菜单的操作按钮
				List<AuthzFeatureTreeNode> featureOpts  = getFeatureOptList(feature, featureOptList);
				if(CollectionUtils.isNotEmpty(featureOpts)) {
					boolean checked = featureOpts.stream().anyMatch(item -> item.isChecked());
					if(checked) {
						featureDTO.setChecked(true);
					} else {
						featureDTO.setChecked(false);
					}
					featureDTO.setChildren(featureOpts);
				}

				features.add(featureDTO);
			}
			return features.stream().sorted().collect(Collectors.toList());
		}
		return features;
	}

	protected static StringBuilder getLabel(StringBuilder builder, AuthzFeatureEntity leaf, List<AuthzFeatureEntity> featureList) {
		// 获取该菜单的父菜单
		List<AuthzFeatureEntity> parentFeatureList = featureList.stream()
				.filter(feature ->  StringUtils.equals(leaf.getParent(), feature.getId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(parentFeatureList)) {
			// 只有一个父亲，只会循环一次
			for (AuthzFeatureEntity feature : parentFeatureList) {
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
	public static List<AuthzFeatureEntity> getFeatureMergedList(List<AuthzFeatureEntity> features,
                                                                List<AuthzFeatureEntity> ownFeatures) {
		// 定义合并后的功能菜单集合对象
		List<AuthzFeatureEntity> mergedFeatureList = Lists.newArrayList();
		// 循环用户拥有的菜单
		for (AuthzFeatureEntity feature : ownFeatures) {
			mergedFeatureList.add(feature);
			// 从系统菜单中获取当前菜单的所有的父级菜单，并加入合并集合
			getParants(mergedFeatureList, feature, features );
		}
		// 去除重复的菜单
		List<AuthzFeatureEntity> mergedFeatures = Lists.newArrayList();
		for (AuthzFeatureEntity feature : mergedFeatureList) {
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
	protected static List<AuthzFeatureEntity> getParants(List<AuthzFeatureEntity> mergedFeatures, AuthzFeatureEntity currentFeature, List<AuthzFeatureEntity> featureList) {
		// 获取该菜单的父菜单
		List<AuthzFeatureEntity> parentFeatureList = featureList.stream()
				.filter(feature ->  StringUtils.equals(currentFeature.getParent(), feature.getId()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(parentFeatureList)) {
			// 只有一个父亲，只会循环一次
			for (AuthzFeatureEntity prentFeature : parentFeatureList) {
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
	public static List<AuthzFeatureTreeNode> getFeatureTree(List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {

		//优先获得最顶层的菜单集合
		List<AuthzFeatureEntity> topFeatureList = featureList.stream()
				.filter(feature -> StringUtils.equals("#", feature.getUrl()))
				.collect(Collectors.toList());
		List<AuthzFeatureTreeNode> features = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topFeatureList)){

			for (AuthzFeatureEntity feature : topFeatureList) {

				AuthzFeatureTreeNode featureDTO = toTreeNode(feature);
				// 判断是否是有父菜单
				boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
				featureDTO.setLeaf(isLeaf);
				if(isLeaf){
					featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
				} else {
					featureDTO.setLabel( feature.getName());
				}
				// 子菜单
				List<AuthzFeatureTreeNode> subFeatures  = getSubFeatureList(feature, featureList, featureOptList);
				if(CollectionUtils.isNotEmpty(subFeatures)) {
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

	public static AuthzFeatureTreeNode getFeatureTree(String servId, List<AuthzFeatureEntity> featureList, List<AuthzFeatureOptEntity> featureOptList) {

		AuthzFeatureTreeNode featureDTO = new AuthzFeatureTreeNode();
		if(CollectionUtils.isEmpty(featureList)) {
			return featureDTO;
		}

		//优先获得最顶层的菜单集合
		AuthzFeatureEntity feature = featureList.stream()
				.filter(f -> StringUtils.equals(servId, f.getParent()))
				.findFirst().get();

		featureDTO = toTreeNode(feature);
		// 判断是否是有父菜单
		boolean isLeaf = featureList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getId(), feature.getParent()));
		featureDTO.setLeaf(isLeaf);
		if(isLeaf){
			featureDTO.setLabel(getLabel(new StringBuilder(feature.getName()) , feature, featureList).toString());
		} else {
			featureDTO.setLabel( feature.getName());
		}
		// 子菜单
		List<AuthzFeatureTreeNode> subFeatures  = getSubFeatureList(feature, featureList, featureOptList);
		if(CollectionUtils.isNotEmpty(subFeatures)) {
			featureDTO.setChecked(true);
			featureDTO.setChildren(subFeatures.stream().filter(item -> item.isChecked()).collect(Collectors.toList()));
		}

		return featureDTO;
	}

}