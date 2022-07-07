/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import io.hiwepy.admin.extras.inform.dao.entities.InformRecordEntity;
import io.hiwepy.admin.extras.inform.service.IInformRecordService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordPaginationDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordStatsDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;

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
	public ApiRestResponse<Long> pending() throws Exception {
		InformRecordEntity entity = new InformRecordEntity();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		entity.setReceiverId(principal.getUserid());
		return ApiRestResponse.success(getInformService().getCount(entity));
	}
	
	@ApiOperation(value = "查询消息通知", notes = "分页查询消息通知")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public Result<InformRecordDTO> list(@Valid @RequestBody InformRecordPaginationDTO paginationDTO) throws Exception {
		
		InformRecordEntity model = getBeanMapper().map(paginationDTO, InformRecordEntity.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setReceiverId(principal.getUserid());
		
		Page<InformRecordEntity> pageResult = getInformService().getPagedList(model);
		List<InformRecordDTO> retList = new ArrayList<InformRecordDTO>();
		for (InformRecordEntity registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformRecordDTO.class));
		}
		
		return new Result<InformRecordDTO>(pageResult, retList);
		
	}

	@ApiOperation(value = "消息通知统计信息", notes = "查询消息通知统计信息")
	@GetMapping("stats")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<InformRecordStatsDTO>> stats() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		return ApiRestResponse.success(getInformService().getStats(principal.getUserid()));
	}
	
	@ApiOperation(value = "消息通知信息", notes = "查询指定id的消息通知信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", value = "消息通知id", required = true, dataType = "String"),
	})
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<InformRecordDTO> detail(@RequestParam("id")  String id) throws Exception {
		
		InformRecordEntity model = getInformService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("inform.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, InformRecordDTO.class));
		
	}
	
	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@GetMapping("read")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> read(@RequestParam("ids") String ids) throws Exception {

		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);

		InformRecordEntity entity = new InformRecordEntity();
		entity.setStatus(Constants.Normal.IS_READ_YES);

		// 执行消息通知阅读操作
		boolean result = getInformService().update(entity, new LambdaQueryWrapper<InformRecordEntity>()
				.eq(InformRecordEntity::getReceiverId, principal.getUserid())
				.eq(InformRecordEntity::getStatus, Constants.Normal.IS_READ_NO)
				.in(InformRecordEntity::getId, Lists.newArrayList(StringUtils.split(ids, ","))));
		if(result) {
			return success("inform.read.success");
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.read.error");
	}

	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@PostMapping("readall")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> readall() throws Exception {
		InformRecordEntity entity = new InformRecordEntity();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		entity.setStatus(Constants.Normal.IS_READ_YES);
		// 执行消息通知阅读操作
		boolean result = getInformService().update(entity, new LambdaQueryWrapper<InformRecordEntity>()
				.eq(InformRecordEntity::getReceiverId, principal.getUserid())
				.eq(InformRecordEntity::getStatus, Constants.Normal.IS_READ_NO));
		if(result) {
			return success("inform.readall.success");
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.readall.error");
	}
	
	@ApiOperation(value = "删除消息通知", notes = "删除消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行消息通知删除操作
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		boolean result = getInformService().removeBatchByIds(Lists.newArrayList(StringUtils.split(ids, ",")));
		if(result) {
			return success("inform.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.delete.error", result);
	}

	public IInformRecordService getInformService() {
		return informService;
	}
	
}
