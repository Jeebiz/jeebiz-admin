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
import net.jeebiz.admin.extras.logbiz.web.dto.BizLogDTO;
import net.jeebiz.admin.extras.logbiz.web.dto.BizLogPaginationDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "操作日志：业务操作日志")
@Validated
@RestController
@RequestMapping("/logs/biz/")
public class BizLogController extends BaseApiController {

	@Autowired
	private IBizLogService bizLogService;
	
	@ApiOperation(value = "操作类型", notes = "操作类型（操作日志筛选条件）")
	@GetMapping("opts")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<Map<String, String>>> bizOpts() throws Exception {
		return ApiRestResponse.success(BusinessType.toList());
	}

	@ApiOperation(value = "功能操作日志", notes = "分页查询除登录外的功能操作日志信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "数据筛选条件", dataType = "BizLogPaginationVo")
	})
	@PostMapping("list")
	@RequiresPermissions("logs-biz:list")
	@ResponseBody
	public ApiRestResponse<Result<BizLogDTO>> list(@Valid @RequestBody BizLogPaginationDTO paginationVo)
			throws Exception {
		
		BizLogModel model = getBeanMapper().map(paginationVo, BizLogModel.class);
		Page<BizLogModel> pageResult = getBizLogService().getPagedList(model);
		List<BizLogDTO> retList = new ArrayList<BizLogDTO>();
		for (BizLogModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, BizLogDTO.class));
		}
		
		return ApiRestResponse.success(new Result<BizLogDTO>(pageResult, retList));
	}
	
	public IBizLogService getBizLogService() {
		return bizLogService;
	}

	public void setBizLogService(IBizLogService bizLogService) {
		this.bizLogService = bizLogService;
	}
	
}
