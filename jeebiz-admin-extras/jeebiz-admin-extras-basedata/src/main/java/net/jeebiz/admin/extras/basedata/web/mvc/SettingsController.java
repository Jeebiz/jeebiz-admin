/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.mvc;


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
import net.jeebiz.admin.extras.basedata.dao.entities.SettingsModel;
import net.jeebiz.admin.extras.basedata.service.ISettingsService;
import net.jeebiz.admin.extras.basedata.setup.Constants;
import net.jeebiz.admin.extras.basedata.web.vo.SettingsGroupRenewVo;
import net.jeebiz.admin.extras.basedata.web.vo.SettingsRenewVo;
import net.jeebiz.admin.extras.basedata.web.vo.SettingsVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;


@Api(tags = "系统参数：各类系统参数维护")
@RestController
@RequestMapping("/extras/basedata/settings/")
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
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组查询系统参数", opt = BusinessType.SELECT)
	@GetMapping("list")
	@RequiresPermissions("settings:list")
	@ResponseBody
	public Object list(@RequestParam String gkey) throws Exception {
		
		SettingsModel model = new SettingsModel();
		model.setGkey(gkey);
		
		List<SettingsModel> records = getSettingsService().getModelList(model);
		List<SettingsVo> retList = Lists.newArrayList();
		for (SettingsModel settingsModel : records) {
			retList.add(getBeanMapper().map(settingsModel, SettingsVo.class));
		}
		return new Result<SettingsVo>(retList);
		
	}
	
	@ApiOperation(value = "系统参数键值对查询", notes = "根据分组查询系统参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "gkey", value = "系统参数分组", required = true, dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组查询系统参数", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("settings:list")
	@ResponseBody
	public Object pairs(@RequestParam String gkey) throws Exception {
		return getSettingsService().getPairValues(gkey);
	}
	
	@ApiOperation(value = "更新系统参数", notes = "更新系统参数")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewVo", value = "系统参数", required = true, dataType = "SettingsRenewVo")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新系统参数", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("settings:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody SettingsRenewVo renewVo) throws Exception {
		SettingsModel model = getBeanMapper().map(renewVo, SettingsModel.class);
		int result = getSettingsService().update(model);
		if(result == 1) {
			return success("settings.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("settings.renew.fail", result);
	}
	
	@ApiOperation(value = "批量更新分组内的系统参数", notes = "批量更新分组内的系统参数")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "body", name = "renewVo", value = "系统参数集合", required = true, dataType = "SettingsGroupRenewVo")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "批量更新分组内的系统参数", opt = BusinessType.UPDATE)
	@PostMapping("batch/renew")
	@RequiresPermissions("settings:renew")
	@ResponseBody
	public Object batchRenew(@Valid @RequestBody SettingsGroupRenewVo renewVo) throws Exception {
		
		try {
			
			List<SettingsModel> list = Lists.newArrayList();
			for (SettingsRenewVo settingsVo : renewVo.getDatas()) {
				SettingsModel model = getBeanMapper().map(settingsVo, SettingsModel.class);
				model.setGkey(renewVo.getGkey());
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
