/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import net.jeebiz.boot.api.utils.StringUtils;

public class AuthzServsUtils {
	
	/**
	 * 对提交的服务ID进行去重处理
	 * @param originServs ： 原始服务ID集合
	 * @return 解析和去重后的标记集合
	 */
	public static List<String> distinct(Set<String> originServs){
		List<String> servs = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(originServs)) {
			return distinct(originServs.stream().collect(Collectors.toList()));
		}
		return servs;
	}
	
	/**
	 * 对提交的服务ID进行去重处理
	 * @param originServs ： 原始服务ID集合
	 * @return 解析和去重后的标记集合
	 */
	public static List<String> distinct(List<String> originServs){
		List<String> servs = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(originServs)) {
			// 进行数据处理
			originServs = originServs.stream().filter(p -> StringUtils.hasText(p) && p.split(":").length > 0).collect(Collectors.toList());
			// 服务ID处理，这里的每个元素可能是多个标记的组合
			for(String serv : originServs) {
				serv = StringUtils.trimAllWhitespace(serv);
				if(StringUtils.hasText(serv)) {
					Collections.addAll(servs, StringUtils.tokenizeToStringArray(serv));
				}
			}
			// 组合最终的标记
			return servs.stream().distinct().sorted().collect(Collectors.toList());
		}
		return servs;
	}
	
	/**
	 * 获取服务ID增量集合
	 * @param servs		：此次提交的服务ID
	 * @param oldservs	： 已经服务ID
	 * @return
	 */
	public static List<String> increment(List<String> servs, List<String> oldservs){
		if(CollectionUtils.isEmpty(servs)) {
			return Lists.newArrayList();
		}
		return servs.stream()
				.filter(serv -> !oldservs.contains(serv))
                .collect(Collectors.toList());
	}
	
	/**
	 * 获取服务ID减量集合
	 * @param servs		：此次提交的服务ID
	 * @param oldservs	： 已经服务ID
	 * @return
	 */
	public static List<String> decrement(List<String> servs, List<String> oldservs){
		if(CollectionUtils.isEmpty(oldservs)) {
			return Lists.newArrayList(servs);
		}
		return oldservs.stream()
				.filter(serv -> !servs.contains(serv))
                .collect(Collectors.toList());
	}
	
}
