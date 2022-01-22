/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.cnarea.service;

import java.util.List;

import net.jeebiz.admin.extras.cnarea.dao.entities.ChinaAreaEntity;
import net.jeebiz.admin.extras.cnarea.web.dto.ChinaAreaDTO;
import net.jeebiz.admin.extras.cnarea.web.dto.ChinaAreaPairDTO;
import net.jeebiz.boot.api.service.IBaseService;


public interface IChinaAreaService extends IBaseService<ChinaAreaEntity> {

	/**
	 * 查询中国省、市、区数结构
	 * @return
	 */
	List<ChinaAreaPairDTO> getChinaProvTree();
	/**
	 * 查询中国省、直辖市、特别行政区列表
	 * @return
	 */
	List<ChinaAreaDTO> getChinaProvList();
	/**
	 * 查询中国省、直辖市、特别行政区键值对
	 * @return
	 */
	List<ChinaAreaPairDTO> getChinaProvPairList();
	/**
	 * 查询中国市、县、区键值对
	 * @return
	 */
	List<ChinaAreaPairDTO> getChinaAreaPairList(String pcode);
	/**
	 * 查询中国市、县、区列表
	 * @return
	 */
	List<ChinaAreaDTO> getChinaAreaList(String pcode);
	
	/**
	 * 根据名称查询中国市
	 * @return
	 */
	ChinaAreaDTO getChinaCity(String sname);
	
}
