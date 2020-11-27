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
import net.jeebiz.admin.extras.logbiz.web.dto.AuthzLogDTO;
import net.jeebiz.admin.extras.logbiz.web.dto.AuthzLogPaginationDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "认证日志：登录、登出")
@Validated
@RestController
@RequestMapping("/logs/authz/")
public class AuthzLogController extends BaseApiController {

	@Autowired
	private IAuthzLogService authzLogService;
	
	@ApiOperation(value = "认证授权类型", notes = "认证授权类型（login:登录认证、logout:会话注销）")
	@GetMapping("opts")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> authzOpts() throws Exception {
		return ApiRestResponse.success(AuthzOptEnum.toList());
	}
	
	@ApiOperation(value = "认证授权日志", notes = "分页查询用户登录、登出日志信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "数据筛选条件", dataType = "AuthzLogPaginationDTO")
	})
	@PostMapping("list")
	@RequiresPermissions("logs-authz:list")
	@ResponseBody
	public ApiRestResponse<Result<AuthzLogDTO>> list(@Valid @RequestBody AuthzLogPaginationDTO paginationDTO)
			throws Exception {
		
		AuthzLogModel model = getBeanMapper().map(paginationDTO, AuthzLogModel.class);
		Page<AuthzLogModel> pageResult = getAuthzLogService().getPagedList(model);
		List<AuthzLogDTO> retList = new ArrayList<AuthzLogDTO>();
		for (AuthzLogModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, AuthzLogDTO.class));
		}
		
		return ApiRestResponse.success(new Result<AuthzLogDTO>(pageResult, retList));
	}

	public IAuthzLogService getAuthzLogService() {
		return authzLogService;
	}

	public void setAuthzLogService(IAuthzLogService authzLogService) {
		this.authzLogService = authzLogService;
	}
	
}
