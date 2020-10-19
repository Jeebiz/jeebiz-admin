/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.logbiz.dao.entities.AuthzLogModel;
import net.jeebiz.admin.extras.logbiz.service.IAuthzLogService;
import net.jeebiz.admin.extras.logbiz.utils.enums.AuthzOptEnum;
import net.jeebiz.admin.extras.logbiz.utils.enums.LoggerLevelEnum;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogPaginationVo;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "认证日志：登录、登出")
@Validated
@RestController
@RequestMapping("/logs/authz/")
public class AuthzLogsController extends BaseApiController {

	@Autowired
	private IAuthzLogService authzLogService;
	
	@ApiOperation(value = "认证授权类型", notes = "认证授权类型（login:登录认证、logout:会话注销）")
	@GetMapping("opts")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> authzOpts() throws Exception {
		return ApiRestResponse.success(AuthzOptEnum.toList());
	}

	@ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@GetMapping("levels")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> levels() throws Exception {
		return ApiRestResponse.success(LoggerLevelEnum.toList());
	}
	
	@ApiOperation(value = "认证授权日志", notes = "分页查询用户登录、登出日志信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "数据筛选条件", dataType = "AuthzLogPaginationVo")
	})
	@PostMapping("list")
	@RequiresPermissions("logs-authz:list")
	@ResponseBody
	public Result<AuthzLogVo> list(@Valid @RequestBody AuthzLogPaginationVo paginationVo)
			throws Exception {
		
		AuthzLogModel model = getBeanMapper().map(paginationVo, AuthzLogModel.class);
		Page<AuthzLogModel> pageResult = getAuthzLogService().getPagedList(model);
		List<AuthzLogVo> retList = new ArrayList<AuthzLogVo>();
		for (AuthzLogModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, AuthzLogVo.class));
		}
		
		return new Result<AuthzLogVo>(pageResult, retList);
	}

	public IAuthzLogService getAuthzLogService() {
		return authzLogService;
	}

	public void setAuthzLogService(IAuthzLogService authzLogService) {
		this.authzLogService = authzLogService;
	}
	
}
