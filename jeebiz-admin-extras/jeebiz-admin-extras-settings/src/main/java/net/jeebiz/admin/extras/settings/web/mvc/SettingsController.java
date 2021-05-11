/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.settings.dao.entities.SettingsModel;
import net.jeebiz.admin.extras.settings.service.ISettingsService;
import net.jeebiz.admin.extras.settings.setup.Constants;
import net.jeebiz.admin.extras.settings.web.dto.SettingsDTO;
import net.jeebiz.admin.extras.settings.web.dto.SettingsGroupRenewDTO;
import net.jeebiz.admin.extras.settings.web.dto.SettingsRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;


@Api(tags = "系统参数：各类系统参数维护")
@RestController
@RequestMapping("/settings/")
public class SettingsController extends BaseApiController {
	
	@Autowired
	private ISettingsService settingsService;
	
	@ApiOperation(value = "系统参数详细信息查询", notes = "根据分组查询系统参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "gkey", value = "系统参数分组", required = true, dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.EXTRAS_SETTINGS, business = "根据分组查询系统参数", opt = BusinessType.SELECT)
	@GetMapping("list")
	@RequiresPermissions("settings:list")
	@ResponseBody
	public Result<SettingsDTO> list(@RequestParam String gkey) throws Exception {
		
		SettingsModel model = new SettingsModel();
		model.setGkey(gkey);
		
		List<SettingsModel> records = getSettingsService().getModelList(model);
		List<SettingsDTO> retList = Lists.newArrayList();
		for (SettingsModel settingsModel : records) {
			retList.add(getBeanMapper().map(settingsModel, SettingsDTO.class));
		}
		return new Result<SettingsDTO>(retList);
		
	}
	
	@ApiOperation(value = "系统参数键值对查询", notes = "根据分组查询系统参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "gkey", value = "系统参数分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_SETTINGS, business = "根据分组查询系统参数", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("settings:list")
	@ResponseBody
	public ApiRestResponse<List<PairModel>> pairs(@RequestParam String gkey) throws Exception {
		return ApiRestResponse.success(getSettingsService().getPairValues(gkey));
	}
	
	@ApiOperation(value = "更新系统参数", notes = "更新系统参数")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewDTO", value = "系统参数", required = true, dataType = "SettingsRenewDTO")
	})
	@BusinessLog(module = Constants.EXTRAS_SETTINGS, business = "更新系统参数", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("settings:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody SettingsRenewDTO renewDTO) throws Exception {
		SettingsModel model = getBeanMapper().map(renewDTO, SettingsModel.class);
		boolean result = getSettingsService().updateById(model);
		if(result) {
			return success("settings.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("settings.renew.fail", result);
	}
	
	@ApiOperation(value = "批量更新分组内的系统参数", notes = "批量更新分组内的系统参数")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "body", name = "renewDTO", value = "系统参数集合", required = true, dataType = "SettingsGroupRenewDTO")
	})
	@BusinessLog(module = Constants.EXTRAS_SETTINGS, business = "批量更新分组内的系统参数", opt = BusinessType.UPDATE)
	@PostMapping("batch/renew")
	@RequiresPermissions("settings:renew")
	@ResponseBody
	public Object batchRenew(@Valid @RequestBody SettingsGroupRenewDTO renewDTO) throws Exception {
		
		try {
			
			List<SettingsModel> list = Lists.newArrayList();
			for (SettingsRenewDTO settingsDTO : renewDTO.getDatas()) {
				SettingsModel model = getBeanMapper().map(settingsDTO, SettingsModel.class);
				model.setGkey(renewDTO.getGkey());
				list.add(model);
			}
			// 批量执行系统参数更新操作
			getSettingsService().batchUpdate(list);
			return success("settings.renew.success");
		} catch (Exception e) {
			// 逻辑代码，如果发生异常将不会被执行
			return fail("settings.renew.fail");
		}
	}

	public ISettingsService getSettingsService() {
		return settingsService;
	}

	public void setSettingsService(ISettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
}
