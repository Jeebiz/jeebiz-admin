/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import io.hiwepy.admin.authz.org.dao.entities.OrganizationEntity;
import io.hiwepy.admin.authz.org.web.dto.OrganizationTreeDTO;

public class OrgUtils {
	
	protected static List<OrganizationTreeDTO> getSubOrgList(OrganizationEntity parentNav,List<OrganizationEntity> orgList) {
		
		List<OrganizationTreeDTO> orgs = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<OrganizationEntity> childOrgList = orgList.stream()
				.filter(org -> StringUtils.equals(parentNav.getId(), org.getParent()))
				.collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(childOrgList)){
			for (OrganizationEntity org : childOrgList) {
				OrganizationTreeDTO orgDTO = new OrganizationTreeDTO();
				// 组织机构id
				orgDTO.setId(org.getId());
				// 组织机构编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				orgDTO.setCode(org.getCode());
				// 组织机构名称
				orgDTO.setName(org.getName());
				// 组织机构简介
				orgDTO.setIntro(org.getIntro());
				// 父级组织机构id
				orgDTO.setParent(org.getParent());
				// 机构状态（0:禁用|1:可用）
				orgDTO.setStatus(org.getStatus());
				// 判断是否是有子组织机构
				boolean isParent = orgList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), org.getId()));
				if(isParent){
					// 子组织机构
					List<OrganizationTreeDTO> subOrgs = getSubOrgList(org, orgList);
					if(!CollectionUtils.isEmpty(subOrgs)) {
						orgDTO.setChildren(subOrgs);
					}
				} else {
					orgDTO.setLeaf(true);
				}
				orgs.add(orgDTO);
			}
			return orgs.stream().sorted().collect(Collectors.toList());
		}
		return orgs;
	}
	
	/**
	 * 获取组织机构树
	 * @param orgList
	 * @return
	 */
	public static List<OrganizationTreeDTO> getOrgTreeList(List<OrganizationEntity> orgList) {
		if(CollectionUtils.isEmpty(orgList)) {
			return new ArrayList<OrganizationTreeDTO>();
		}
		// 优先获得最顶层的组织机构集合
		List<OrganizationEntity> topList = orgList.stream()
				.filter(org -> StringUtils.equalsIgnoreCase(org.getParent(), "0"))
				.collect(Collectors.toList());
		List<OrganizationTreeDTO> orgs = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(topList)){
			
			for (OrganizationEntity org : topList) {
				
				OrganizationTreeDTO orgDTO = new OrganizationTreeDTO();
				// 组织机构id
				orgDTO.setId(org.getId());
				// 组织机构编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
				orgDTO.setCode(org.getCode());
				// 组织机构名称
				orgDTO.setName(org.getName());
				// 父级组织机构id
				orgDTO.setParent(org.getParent());
				// 组织机构简介
				orgDTO.setIntro(org.getIntro());
				// 机构状态（0:禁用|1:可用）
				orgDTO.setStatus(org.getStatus());
				// 判断是否是有子组织机构
				boolean isParent = orgList.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getParent(), org.getId()));
				if(isParent){
					orgDTO.setLeaf(false);
					// 子组织机构
					List<OrganizationTreeDTO> subOrgs = getSubOrgList(org, orgList);
					// 有子组织机构
					if(!CollectionUtils.isEmpty(subOrgs)) {
						orgDTO.setChildren(subOrgs);
					}
				} else {
					orgDTO.setLeaf(true);
				}
				orgs.add(orgDTO);
			}
			
			return orgs.stream().sorted().collect(Collectors.toList());
		}
		return orgs;
	}
	
}
