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
import net.jeebiz.admin.extras.logbiz.dao.entities.BizLogModel;
import net.jeebiz.admin.extras.logbiz.service.IBizLogService;
import net.jeebiz.admin.extras.logbiz.utils.enums.LoggerLevelEnum;
import net.jeebiz.admin.extras.logbiz.web.vo.BizLogPaginationVo;
import net.jeebiz.admin.extras.logbiz.web.vo.BizLogVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "操作日志：业务操作日志")
@Validated
@RestController
@RequestMapping("/logs/biz/")
public class BizLogsController extends BaseApiController {

	@Autowired
	private IBizLogService bizLogService;
	
	@ApiOperation(value = "操作类型", notes = "操作类型（操作日志筛选条件）")
	@GetMapping("opts")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> bizOpts() throws Exception {
		return ApiRestResponse.success(BusinessType.toList());
	}

	@ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@GetMapping("levels")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> levels() throws Exception {
		return ApiRestResponse.success(LoggerLevelEnum.toList());
	}
	
	@ApiOperation(value = "功能操作日志", notes = "分页查询除登录外的功能操作日志信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "数据筛选条件", dataType = "BizLogPaginationVo")
	})
	@PostMapping("list")
	@RequiresPermissions("logs-biz:list")
	@ResponseBody
	public Result<BizLogVo> list(@Valid @RequestBody BizLogPaginationVo paginationVo)
			throws Exception {
		
		BizLogModel model = getBeanMapper().map(paginationVo, BizLogModel.class);
		Page<BizLogModel> pageResult = getBizLogService().getPagedList(model);
		List<BizLogVo> retList = new ArrayList<BizLogVo>();
		for (BizLogModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, BizLogVo.class));
		}
		
		return new Result<BizLogVo>(pageResult, retList);
	}
	
	public IBizLogService getBizLogService() {
		return bizLogService;
	}

	public void setBizLogService(IBizLogService bizLogService) {
		this.bizLogService = bizLogService;
	}
	
}
