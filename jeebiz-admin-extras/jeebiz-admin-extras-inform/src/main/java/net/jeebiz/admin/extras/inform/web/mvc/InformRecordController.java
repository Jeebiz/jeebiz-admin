/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.service.IInformRecordService;
import net.jeebiz.admin.extras.inform.setup.Constants;
import net.jeebiz.admin.extras.inform.web.vo.InformRecordPaginationVo;
import net.jeebiz.admin.extras.inform.web.vo.InformRecordStatsVo;
import net.jeebiz.admin.extras.inform.web.vo.InformRecordVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "消息通知：我的消息")
@RestController
@RequestMapping("/inform/")
public class InformRecordController extends BaseMapperController {
	
	@Autowired
	private IInformRecordService informService;
	
	@ApiOperation(value = "待处理通知总数", notes = "查询待处理通知总数")
	@GetMapping("pending")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<Integer> pending() throws Exception {
		
		InformRecordModel model = new InformRecordModel();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setToUid(principal.getUserid());
		
		return ApiRestResponse.success(getInformService().getCount(model));
	}
	
	@ApiOperation(value = "查询消息通知", notes = "分页查询消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "消息筛选条件", dataType = "InformRecordPaginationVo")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public Result<InformRecordVo> list(@Valid @RequestBody InformRecordPaginationVo paginationVo) throws Exception {
		
		InformRecordModel model = getBeanMapper().map(paginationVo, InformRecordModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setToUid(principal.getUserid());
		
		Page<InformRecordModel> pageResult = getInformService().getPagedList(model);
		List<InformRecordVo> retList = new ArrayList<InformRecordVo>();
		for (InformRecordModel registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformRecordVo.class));
		}
		
		return new Result<InformRecordVo>(pageResult, retList);
		
	}

	@ApiOperation(value = "消息通知统计信息", notes = "查询消息通知统计信息")
	@GetMapping("stats")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<InformRecordStatsVo>> stats() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		return ApiRestResponse.success(getInformService().getStats(principal.getUserid()));
	}
	
	@ApiOperation(value = "消息通知信息", notes = "查询指定ID的消息通知信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", value = "消息通知ID", required = true, dataType = "String"),
	})
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<InformRecordVo> detail(@RequestParam("id")  String id) throws Exception {
		
		InformRecordModel model = getInformService().getModel(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("inform.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, InformRecordVo.class));
		
	}
	
	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@GetMapping("read")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> read(@RequestParam("ids") String ids) throws Exception {
		
		InformRecordModel model = new InformRecordModel();
		model.setIds(Lists.newArrayList(StringUtils.split(ids, ",")));
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setToUid(principal.getUserid());
		model.setStatus("1");
		
		// 执行消息通知阅读操作
		int result = getInformService().update(model);
		if(result == 1) {
			return success("inform.read.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.read.error", result);
	}
	

	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@PostMapping("readall")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> readall() throws Exception {
		
		InformRecordModel model = new InformRecordModel();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setToUid(principal.getUserid());
		model.setStatus("1");
		
		// 执行消息通知阅读操作
		int result = getInformService().update(model);
		if(result == 1) {
			return success("inform.readall.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.readall.error", result);
	}
	
	@ApiOperation(value = "删除消息通知", notes = "删除消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行消息通知删除操作
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int result = getInformService().deleteByUid(principal.getUserid(), Lists.newArrayList(StringUtils.split(ids, ",")));
		if(result > 0) {
			return success("inform.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.delete.error", result);
	}

	public IInformRecordService getInformService() {
		return informService;
	}
	
}
