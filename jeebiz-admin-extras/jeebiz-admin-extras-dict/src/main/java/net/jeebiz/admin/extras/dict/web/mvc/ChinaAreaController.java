/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.dict.dao.entities.ChinaAreaModel;
import net.jeebiz.admin.extras.dict.service.IChinaAreaService;
import net.jeebiz.admin.extras.dict.web.dto.ChinaAreaPairDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "数据字典：行政区域数据")
@RestController
@RequestMapping("/dict/cnarea/")
@Validated
public class ChinaAreaController extends BaseApiController {
	
	@Autowired
	private IChinaAreaService chinaAreaService;
	
	@ApiOperation(value = "中国行政区树结构", notes = "中国省、直辖市、行政区树结构")
	@GetMapping("tree")
	public ApiRestResponse<List<ChinaAreaPairDTO>> tree() throws Exception {
		return ApiRestResponse.success(getChinaAreaService().getChinaProvTree());
	}
	
	@ApiOperation(value = "中国行政区列表", notes = "中国省、直辖市、特别行政区列表")
	@GetMapping("prov/list")
	public ApiRestResponse<List<ChinaAreaModel>> provList() throws Exception {
		return ApiRestResponse.success(getChinaAreaService().getChinaProvList());
	}
	
	@ApiOperation(value = "中国行政区键值对", notes = "中国省、直辖市、特别行政区键值对")
	@GetMapping("prov/pairs")
	public ApiRestResponse<List<ChinaAreaPairDTO>> provPairs() throws Exception {
		return ApiRestResponse.success(getChinaAreaService().getChinaProvPairList());
	}
	
	@ApiOperation(value = "查询中国市、县、区键值对", notes = "根据父级编码分组查询中国市、县、区键值对")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pcode", value = "父级编码", required = true, dataType = "String")
	})
	@GetMapping("pairs")
	public ApiRestResponse<List<ChinaAreaPairDTO>> areaPairs(@RequestParam String pcode) throws Exception {
		return ApiRestResponse.success(getChinaAreaService().getChinaAreaPairList(pcode));
	}
	
	@ApiOperation(value = "查询中国市、县、区列表", notes = "根据父级编码分组查询中国市、县、区列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pcode", value = "父级编码", required = true, dataType = "String")
	})
	@GetMapping("list")
	public ApiRestResponse<List<ChinaAreaModel>> areaList(@RequestParam String pcode) throws Exception {
		return ApiRestResponse.success(getChinaAreaService().getChinaAreaList(pcode));
	}
	
	public IChinaAreaService getChinaAreaService() {
		return chinaAreaService;
	}
	
}

