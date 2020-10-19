/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import net.jeebiz.admin.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.authz.org.web.vo.AuthzOrganizationTreeVo;

public class OrgUtils {
	
	protected static List<AuthzOrganizationTreeVo> getSubOrgList(AuthzOrganizationModel parentNav,List<AuthzOrganizationModel> orgList) {
		
		List<AuthzOrganizationTreeVo> orgs = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<AuthzOrganizationModel> childOrgList = orgList.stream()
				.filter(org -> StringUtils.equals(parentNav.getId(), org.getParent()))
				.collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(childOrgList)){
			for (AuthzOrganizationModel org : childOrgList) {
				AuthzOrganizationTreeVo orgVo = new AuthzOrganizationTreeVo();
				// 组织机构ID
				orgVo.setId(org.getId());
				// 组织机构编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				orgVo.setCode(org.getCode());
				// 组织机构名称
				orgVo.setName(org.getName());
				// 组织机构简介
				orgVo.setIntro(org.getIntro());
				// 父级组织机构ID
				orgVo.setParent(org.getParent());
				// 判断是否是有子组织机构
				boolean isParent = orgList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), org.getId()));
				if(isParent){
					// 子组织机构
					List<AuthzOrganizationTreeVo> subOrgs = getSubOrgList(org, orgList);
					if(null != subOrgs && subOrgs.size() > 0) {
						orgVo.setChildren(subOrgs);
					}
				} else {
					orgVo.setLeaf(true);
				}
				orgs.add(orgVo);
			}
			return orgs.stream().sorted().collect(Collectors.toList());
		}
		return orgs;
	}
	
	/**
	 * 获取组织机构树
	 * @param orgList
	 * @param orgOptList
	 * @return
	 */
	public static List<AuthzOrganizationTreeVo> getOrgTreeList(List<AuthzOrganizationModel> orgList) {
		if(CollectionUtils.isEmpty(orgList)) {
			return new ArrayList<AuthzOrganizationTreeVo>();
		}
		// 优先获得最顶层的组织机构集合
		List<AuthzOrganizationModel> topList = orgList.stream()
				.filter(org -> StringUtils.equalsIgnoreCase(org.getCode(), "0"))
				.collect(Collectors.toList());
		List<AuthzOrganizationTreeVo> orgs = Lists.newArrayList();
		if(CollectionUtils.isEmpty(topList)){
			
			for (AuthzOrganizationModel org : topList) {
				
				AuthzOrganizationTreeVo orgVo = new AuthzOrganizationTreeVo();
				// 组织机构ID
				orgVo.setId(org.getId());
				// 组织机构编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				orgVo.setCode(org.getCode());
				// 组织机构名称
				orgVo.setName(org.getName());
				// 父级组织机构ID
				orgVo.setParent(org.getParent());
				// 组织机构简介
				orgVo.setIntro(org.getIntro());
				// 判断是否是有子组织机构
				boolean isParent = orgList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), org.getId()));
				if(isParent){
					orgVo.setLeaf(false);
					// 子组织机构
					List<AuthzOrganizationTreeVo> subOrgs = getSubOrgList(org, orgList);
					// 有子组织机构
					if(CollectionUtils.isEmpty(subOrgs)) {
						orgVo.setChildren(subOrgs);
					}
				} else {
					orgVo.setLeaf(true);
				}
				orgs.add(orgVo);
			}
			
			return orgs.stream().sorted().collect(Collectors.toList());
		}
		return orgs;
	}
	
}
