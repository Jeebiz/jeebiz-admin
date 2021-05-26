/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.dict.dao.entities.KeyValueModel;
import net.jeebiz.admin.extras.dict.service.IKeyValueService;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.web.dto.KeyValueDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyValueGroupRenewDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyValueNewDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyValuePaginationDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyValueRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.StringUtils;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "数据字典：基础数据")
@RestController
@RequestMapping("/dict/keyvalue/")
@Validated
public class KeyValueController extends BaseApiController {
	
	@Autowired
	private IKeyValueService keyValueService;
	
	@ApiOperation(value = "分页查询基础数据", notes = "分页查询基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "KeyValuePaginationDTO")
	})
	@PostMapping("list")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Result<KeyValueDTO> list(@Valid @RequestBody KeyValuePaginationDTO paginationDTO){
		
		KeyValueModel model =  getBeanMapper().map(paginationDTO, KeyValueModel.class);
		Page<KeyValueModel> pageResult = getKeyValueService().getPagedList(model);
		List<KeyValueDTO> retList = Lists.newArrayList();
		for (KeyValueModel keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, KeyValueDTO.class));
		}
		
		return new Result<KeyValueDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "根据分组查询基础数据（完整）", notes = "根据分组查询基础数据（完整）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "gkey", value = "基础数据分组,多个分组用,分割", required = true, dataType = "String")
	})
	@GetMapping("groups")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public ApiRestResponse<Map<String, List<KeyValueDTO>>> groups(@Valid @NotNull(message = "基础数据分组编码不能为空") @RequestParam String gkey){
		Map<String, List<KeyValueModel>> pairList = getKeyValueService().getGroupPairValues(StringUtils.tokenizeToStringArray(gkey));
		Map<String, List<KeyValueDTO>> reMap = new HashMap<String, List<KeyValueDTO>>();
		if(CollectionUtils.isEmpty(pairList)) {
			return ApiRestResponse.success(reMap);
		}
		for (Entry<String, List<KeyValueModel>> entry : pairList.entrySet()) {
			reMap.put(entry.getKey(), entry.getValue().stream().map(source -> {
				return getBeanMapper().map(source, KeyValueDTO.class);
			}).collect(Collectors.toList()));
		}
		return ApiRestResponse.success(reMap);
		
	}
	
	@ApiOperation(value = "根据分组查询基础数据（键值对）", notes = "根据分组查询基础数据（键值对）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "gkey", value = "基础数据分组", required = true, dataType = "String")
	})
	@GetMapping("pairs")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public ApiRestResponse<List<PairModel>> pairs(@Valid @RequestParam @NotNull(message = "基础数据分组编码不能为空") String gkey) throws Exception {
		return ApiRestResponse.success(getKeyValueService().getPairValues(gkey));
	}
	
	@ApiOperation(value = "创建基础数据", notes = "增加一个新的基础数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据传输对象", dataType = "KeyValueNewDTO") 
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("keyvalue:new")
	@ResponseBody
	public ApiRestResponse<String> keyvalue(@Valid @RequestBody KeyValueNewDTO DTO) throws Exception {
		KeyValueModel model = getBeanMapper().map(DTO, KeyValueModel.class);
		
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.new.conflict");
		}
		// 新增一条数据库配置记录
		boolean result = getKeyValueService().save(model);
		if(result) {
			return success("keyvalue.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.new.fail", result);
	}
	
	@ApiOperation(value = "删除基础数据", notes = "删除基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "基础数据id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("keyvalue:delete")
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行基础数据删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getKeyValueService().removeByIds(idList);
		if(result) {
			return success("keyvalue.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.delete.fail", result);
	}
	
	
	@ApiOperation(value = "更新基础数据", notes = "更新基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据", required = true, dataType = "KeyValueRenewDTO"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("keyvalue:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody KeyValueRenewDTO DTO) throws Exception {
		KeyValueModel model = getBeanMapper().map(DTO, KeyValueModel.class);
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.renew.conflict");
		}
		boolean result = getKeyValueService().updateById(model);
		if(result) {
			return success("keyvalue.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.renew.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据状态", notes = "更新基础数据状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据id", dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "基础数据状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("keyvalue:status")
	@ResponseBody
	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "数据状态错误") @RequestParam String status) throws Exception {
		int result = getKeyValueService().setStatus(id, status);
		if(result == 1) {
			return success("keyvalue.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.status.fail", result);
	}
	
	@ApiOperation(value = "批量更新分组内的基础数据", notes = "批量更新分组内的基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewDTO", value = "基础数据集合", required = true, dataType = "KeyValueGroupRenewDTO"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "批量更新分组内的基础数据", opt = BusinessType.UPDATE)
	@PostMapping(value = "batch/renew")
	@RequiresPermissions("keyvalue:renew")
	@ResponseBody
	public ApiRestResponse<String> batchRenew(@Valid @RequestBody KeyValueGroupRenewDTO renewDTO) throws Exception {
		
		try {
			
			List<KeyValueModel> list = Lists.newArrayList();
			for (KeyValueRenewDTO keyvalueDTO : renewDTO.getDatas()) {
				KeyValueModel model = getBeanMapper().map(keyvalueDTO, KeyValueModel.class);
				model.setGkey(renewDTO.getGkey());
				list.add(model);
			}
			// 批量执行基础数据更新操作
			getKeyValueService().updateBatchById(list);
			return success("keyvalue.renew.success");
		} catch (Exception e) {
			return fail("keyvalue.renew.fail");
		}
	}
	
	@ApiOperation(value = "查询基础数据信息", notes = "根据id查询基础数据信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据id", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresPermissions("keyvalue:detail")
	@ResponseBody
	public ApiRestResponse<KeyValueDTO> detail(@RequestParam("id") String id) throws Exception { 
		KeyValueModel model = getKeyValueService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("keyvalue.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, KeyValueDTO.class));
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
