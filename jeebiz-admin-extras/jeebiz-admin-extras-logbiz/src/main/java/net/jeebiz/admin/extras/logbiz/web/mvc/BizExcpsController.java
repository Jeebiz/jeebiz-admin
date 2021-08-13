/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;
import net.jeebiz.admin.extras.logbiz.service.IBizExcpService;
import net.jeebiz.admin.extras.logbiz.utils.enums.LoggerLevelEnum;
import net.jeebiz.admin.extras.logbiz.web.vo.BizExcpPaginationVo;
import net.jeebiz.admin.extras.logbiz.web.vo.BizExcpVo;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "系统异常日志 : （除登录外的系统访问异常日志信息）")
@RestController
@RequestMapping("/extras/logs/excp/")
public class BizExcpsController extends BaseApiController {

	@Autowired
	private IBizExcpService bizExcpService;
	
	@ApiOperation(value = "异常类型", notes = "异常类型（服务异常筛选条件）")
	@GetMapping("types")
	@RequiresAuthentication
	@ResponseBody
	public Object types() throws Exception {
		return getBizExcpService().getExcpTypes();
	}
	
	@ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@GetMapping("levels")
	@RequiresAuthentication
	@ResponseBody
	public Object levels() throws Exception {
		return LoggerLevelEnum.toList();
	}
	
	@ApiOperation(value = "系统异常 ", notes = "分页查询除登录外的系统访问异常日志信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "数据筛选条件", dataType = "BizExcpPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class) 
	})
	@PostMapping("list")
	@RequiresPermissions("logs-excp:list")
	@ResponseBody
	public Object list(@Valid @RequestBody BizExcpPaginationVo paginationVo) throws Exception {
		
		BizExcpModel model = getBeanMapper().map(paginationVo, BizExcpModel.class);
		Page<BizExcpModel> pageResult = getBizExcpService().getPagedList(model);
		List<BizExcpVo> retList = new ArrayList<BizExcpVo>();
		for (BizExcpModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, BizExcpVo.class));
		}
		
		return new Result<BizExcpVo>(pageResult, retList);
	}

	public IBizExcpService getBizExcpService() {
		return bizExcpService;
	}

	public void setBizExcpService(IBizExcpService bizExcpService) {
		this.bizExcpService = bizExcpService;
	}
	
}
