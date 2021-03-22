/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.shadow.dao.entities.HomepageModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IHomepageDao extends BaseDao<HomepageModel>{

	List<Map<String,String>> getDependencies(List<String> list);
	
}
