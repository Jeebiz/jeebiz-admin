/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.dict.dao.entities.ChinaAreaModel;
import net.jeebiz.admin.extras.dict.web.vo.ChinaAreaPairVo;
import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.boot.api.dao.entities.BaseMap;


@Mapper
public interface IChinaAreaDao extends BaseDao<ChinaAreaModel> {
	
	/**
	 * 查询中国省、市、区数结构
	 * @return
	 */
	List<BaseMap> getChinaPairList();
	
	/**
	 * 查询中国省、直辖市、特别行政区列表
	 * @return
	 */
	List<ChinaAreaModel> getChinaProvList();
	/**
	 * 查询中国省、直辖市、特别行政区键值对
	 * @return
	 */
	List<ChinaAreaPairVo> getChinaProvPairList();
	/**
	 * 查询中国市、县、区键值对
	 * @return
	 */
	List<ChinaAreaPairVo> getChinaAreaPairList(@Param("pcode") String pcode);
	/**
	 * 查询中国市、县、区列表
	 * @return
	 */
	List<ChinaAreaModel> getChinaAreaList(@Param("pcode") String pcode);
	
}
