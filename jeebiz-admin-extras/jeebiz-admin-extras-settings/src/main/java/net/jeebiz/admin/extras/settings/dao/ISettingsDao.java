/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.settings.dao.entities.SettingsModel;
import net.jeebiz.boot.api.dao.BaseMapper;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Mapper
public interface ISettingsDao extends BaseMapper<SettingsModel> {

	/**
	 * 根据分组查询该分组下的系统参数
	 * @param gkey 分组标记
	 * @return
	 */
	public List<PairModel> getPairValues(@Param("gkey") String gkey);
	
}
