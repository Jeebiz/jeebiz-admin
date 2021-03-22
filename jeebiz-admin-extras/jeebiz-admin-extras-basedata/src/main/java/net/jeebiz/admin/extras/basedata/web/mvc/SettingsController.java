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
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;


@Api(tags = "系统参数：各类系统参数维护")
@ApiResponses({ 
	@ApiResponse(code = 0, message = "请求成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10001, message = "认证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10021, message = "授权失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10022, message = "Token缺失", response = ApiRestResponse.class),
	@ApiResponse(code = 10023, message = "Token已过期", response = ApiRestResponse.class),
	@ApiResponse(code = 10024, message = "Token已失效", response = ApiRestResponse.class),
	@ApiResponse(code = 10025, message = "Token错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10110, message = "不允许访问（功能未授权）", response = ApiRestResponse.class),
	@ApiResponse(code = 10111, message = "请求失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10112, message = "数据为空", response = ApiRestResponse.class),
	@ApiResponse(code = 10113, message = "参数类型不匹配", response = ApiRestResponse.class),
	@ApiResponse(code = 10114, message = "缺少矩阵变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10115, message = "缺少URI模板变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10116, message = "缺少Cookie变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10117, message = "缺少请求头", response = ApiRestResponse.class),
	@ApiResponse(code = 10118, message = "缺少参数", response = ApiRestResponse.class),
	@ApiResponse(code = 10119, message = "缺少请求对象", response = ApiRestResponse.class),
	@ApiResponse(code = 10120, message = "参数规则不满足", response = ApiRestResponse.class),
	@ApiResponse(code = 10121, message = "参数绑定错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10122, message = "参数解析错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10123, message = "参数验证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10201, message = "服务器：运行时异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10202, message = "服务器：空值异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10203, message = "服务器：数据类型转换异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10204, message = "服务器：IO异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10205, message = "服务器：未知方法异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10206, message = "服务器：非法参数异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10207, message = "服务器：数组越界异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10208, message = "服务器：网络异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping("/extras/basedata/settings/")
public class SettingsController extends BaseMapperController {
	
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
