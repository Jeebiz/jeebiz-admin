/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.dict.dao.IChinaAreaDao;
import net.jeebiz.admin.extras.dict.dao.entities.ChinaAreaModel;
import net.jeebiz.admin.extras.dict.service.IChinaAreaService;
import net.jeebiz.admin.extras.dict.web.vo.ChinaAreaPairVo;
import net.jeebiz.boot.api.dao.entities.BaseMap;
import net.jeebiz.boot.api.service.BaseServiceImpl;


@Service
public class ChinaAreaServiceImpl extends BaseServiceImpl<ChinaAreaModel, IChinaAreaDao> implements IChinaAreaService {
	
	private static final String PCODE = "pcode";
	private static final String CODE = "code";
	private static final String NAME = "name";
	private static final String DEFAULT_NAME = "市辖区";
	
	@Override
	public List<ChinaAreaPairVo> getChinaProvTree() {
		/**
		 * 查询中国省、市、区数结构
		 */
		List<BaseMap> pairList =  getDao().getChinaPairList();
		
		return pairList.stream()
				.filter(provMap -> MapUtils.getString(provMap, PCODE).equals("0"))
				.map(provMap -> {
					
					// 省级
					ChinaAreaPairVo provPaorVo = new ChinaAreaPairVo();
					
					provPaorVo.setName(MapUtils.getString(provMap, NAME));
					provPaorVo.setValue(MapUtils.getString(provMap, CODE));
					provPaorVo.setChildren(pairList.stream()
							.filter(cityMap -> MapUtils.getString(cityMap, PCODE).equals(MapUtils.getString(provMap, CODE)))
							.map(cityMap -> {
								
								// 市级
								ChinaAreaPairVo cityPaorVo = new ChinaAreaPairVo();
								
								String name = MapUtils.getString(cityMap, NAME);
								cityPaorVo.setName(StringUtils.equalsIgnoreCase(name, DEFAULT_NAME) ? MapUtils.getString(provMap, NAME) : name);
								cityPaorVo.setValue(MapUtils.getString(cityMap, CODE));
								cityPaorVo.setChildren(pairList.stream()
										.filter(areaMap -> MapUtils.getString(areaMap, PCODE).equals(MapUtils.getString(cityMap, CODE)))
										.map(areaMap -> {
											
											// 县级、区级
											ChinaAreaPairVo areaPaorVo = new ChinaAreaPairVo();
											areaPaorVo.setName(MapUtils.getString(areaMap, NAME));
											areaPaorVo.setValue(MapUtils.getString(areaMap, CODE));
											
											return areaPaorVo;
										}).collect(Collectors.toList()));
								
								return cityPaorVo;
							}).collect(Collectors.toList()));
					
					return provPaorVo;
				}).collect(Collectors.toList());
		
	}
	
	@Override
	public List<ChinaAreaModel> getChinaProvList() {
		return getDao().getChinaProvList();
	}

	@Override
	public List<ChinaAreaPairVo> getChinaProvPairList() {
		return getDao().getChinaProvPairList();
	}

	@Override
	public List<ChinaAreaPairVo> getChinaAreaPairList(String pcode) {
		return getDao().getChinaAreaPairList(pcode);
	}

	@Override
	public List<ChinaAreaModel> getChinaAreaList(String pcode) {
		return getDao().getChinaAreaList(pcode);
	}

}
