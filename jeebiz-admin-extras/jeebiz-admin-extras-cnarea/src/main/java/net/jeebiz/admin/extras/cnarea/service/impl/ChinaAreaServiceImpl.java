/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.cnarea.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.cnarea.dao.IChinaAreaDao;
import net.jeebiz.admin.extras.cnarea.dao.entities.ChinaAreaModel;
import net.jeebiz.admin.extras.cnarea.service.IChinaAreaService;
import net.jeebiz.admin.extras.cnarea.web.dto.ChinaAreaDTO;
import net.jeebiz.admin.extras.cnarea.web.dto.ChinaAreaPairDTO;
import net.jeebiz.boot.api.dao.entities.BaseMap;
import net.jeebiz.boot.api.service.BaseServiceImpl;


@Service
public class ChinaAreaServiceImpl extends BaseServiceImpl<ChinaAreaModel, IChinaAreaDao> implements IChinaAreaService {
	
	private static final String PCODE = "pcode";
	private static final String CODE = "code";
	private static final String name = "name";
	private static final String DEFAULt_name = "市辖区";
	
	@Override
	public List<ChinaAreaPairDTO> getChinaProvTree() {
		/**
		 * 查询中国省、市、区数结构
		 */
		List<BaseMap> pairList =  getDao().getChinaPairList();
		
		return pairList.stream()
				.filter(provMap -> MapUtils.getString(provMap, PCODE).equals("0"))
				.map(provMap -> {
					
					// 省级
					ChinaAreaPairDTO provPaorDTO = new ChinaAreaPairDTO();
					
					provPaorDTO.setName(MapUtils.getString(provMap, name));
					provPaorDTO.setValue(MapUtils.getString(provMap, CODE));
					provPaorDTO.setChildren(pairList.stream()
							.filter(cityMap -> MapUtils.getString(cityMap, PCODE).equals(MapUtils.getString(provMap, CODE)))
							.map(cityMap -> {
								
								// 市级
								ChinaAreaPairDTO cityPaorDTO = new ChinaAreaPairDTO();
								
								String name = MapUtils.getString(cityMap, name);
								cityPaorDTO.setName(StringUtils.equalsIgnoreCase(name, DEFAULt_name) ? MapUtils.getString(provMap, name) : name);
								cityPaorDTO.setValue(MapUtils.getString(cityMap, CODE));
								cityPaorDTO.setChildren(pairList.stream()
										.filter(areaMap -> MapUtils.getString(areaMap, PCODE).equals(MapUtils.getString(cityMap, CODE)))
										.map(areaMap -> {
											
											// 县级、区级
											ChinaAreaPairDTO areaPaorDTO = new ChinaAreaPairDTO();
											areaPaorDTO.setName(MapUtils.getString(areaMap, name));
											areaPaorDTO.setValue(MapUtils.getString(areaMap, CODE));
											
											return areaPaorDTO;
										}).collect(Collectors.toList()));
								
								return cityPaorDTO;
							}).collect(Collectors.toList()));
					
					return provPaorDTO;
				}).collect(Collectors.toList());
		
	}
	
	@Override
	public List<ChinaAreaDTO> getChinaProvList() {
		return getDao().getChinaProvList();
	}

	@Override
	public List<ChinaAreaPairDTO> getChinaProvPairList() {
		return getDao().getChinaProvPairList();
	}

	@Override
	public List<ChinaAreaPairDTO> getChinaAreaPairList(String pcode) {
		return getDao().getChinaAreaPairList(pcode);
	}

	@Override
	public List<ChinaAreaDTO> getChinaAreaList(String pcode) {
		return getDao().getChinaAreaList(pcode);
	}

	@Override
	public ChinaAreaDTO getChinaCity(String name) {
		return getDao().getChinaArea("1", name);
	}

}
