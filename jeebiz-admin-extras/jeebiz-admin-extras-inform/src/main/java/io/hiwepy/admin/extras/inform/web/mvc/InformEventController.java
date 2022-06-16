/** 
 * Copyright (C) 2022 杭州天音计算机系统工程有限公司
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.admin.extras.inform.emums.InformEventType;
import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.hiwepy.admin.extras.inform.service.IInformEventService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "消息通知：消息事件")
@RestController
@RequestMapping("/inform/evnet/")
public class InformEventController extends BaseMapperController {
	
	@Autowired
	private IInformEventService informEventService;
	
	@ApiOperation(value = "查询消息通知事件", notes = "分页查询消息通知事件")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知事件", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<Result<InformEventDTO>> list(@Valid @RequestBody InformEventPaginationDTO paginationDTO) throws Exception {
		
		InformEventEntity entity = getBeanMapper().map(paginationDTO, InformEventEntity.class);
		Page<InformEventEntity> pageResult = getInformEventService().getPagedList(entity);
		List<InformEventDTO> retList = new ArrayList<InformEventDTO>();
		for (InformEventEntity registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformEventDTO.class));
		}
		
		return ApiRestResponse.success(new Result<InformEventDTO>(pageResult, retList));
		
	}

	@ApiOperation(value = "事件类型", notes = "事件类型列表（示例，应该去查询字典数据）")
	@GetMapping("types")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> types() throws Exception {
		return ApiRestResponse.success(InformEventType.toList());
	}

	@ApiOperation(value = "事件行为", notes = "事件行为列表")
	@GetMapping("actions")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> actions() throws Exception {
		return ApiRestResponse.success(InformSendChannel.toList());
	}

	@ApiOperation(value = "消息通知事件统计信息", notes = "查询消息通知事件统计信息")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知事件统计信息", opt = BusinessType.SELECT)
	@GetMapping("stats")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<InformEventStatsDTO>> stats() throws Exception {
		return ApiRestResponse.success(getInformEventService().getStats());
	}
	
	@ApiOperation(value = "创建消息通知事件", notes = "增加一个新的消息通知事件")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "创建消息通知事件", opt = BusinessType.INSERT)
	@PostMapping("new")
	//@RequiresPermissions("inform-event:new")
	@ResponseBody
	public ApiRestResponse<String> newEvent(@Valid @RequestBody InformEventNewDTO newDTO) throws Exception {
		InformEventEntity entity = getBeanMapper().map(newDTO, InformEventEntity.class);
		Long ct = getInformEventService().getCount(entity);
		if(ct > 0) {
			return fail("inform.event.new.conflict");
		}
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		entity.setCreator(principal.getUserid());
		entity.setTarget(InformTarget.ALL);
		// 新增一条数据库配置记录
		boolean result = getInformEventService().save(entity);
		if(result) {
			return success("inform.event.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.event.new.fail", result);
	}
	
	@ApiOperation(value = "删除消息通知事件", notes = "删除消息通知事件")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "消息通知事件id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知事件", opt = BusinessType.DELETE)
	@DeleteMapping("delete")
	//@RequiresPermissions("inform-event:delete")
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam("ids") String ids) throws Exception {
		// 执行消息通知事件删除操作
		List<String> idList = Lists.newArrayList(StringUtils.split(ids,","));
		boolean result = getInformEventService().removeByIds(idList);
		if(result) {
			return success("inform.event.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.event.delete.fail", result);
	}
	
	@ApiOperation(value = "更新消息通知事件", notes = "更新消息通知事件")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "更新消息通知事件", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	//@RequiresPermissions("inform-event:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody InformEventRenewDTO renewDTO) throws Exception {
		InformEventEntity entity = getBeanMapper().map(renewDTO, InformEventEntity.class);
		Long ct = getInformEventService().getCount(entity);
		if(ct > 0) {
			return fail("inform.event.renew.conflict");
		}
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		entity.setModifyer(principal.getUserid());
		boolean result = getInformEventService().updateById(entity);
		if(result) {
			return success("inform.event.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.event.renew.fail", result);
	}
	
	@ApiOperation(value = "消息通知事件信息", notes = "查询指定id的消息通知事件信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", value = "消息通知事件id", required = true, dataType = "String"),
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询指定id的消息通知事件信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	//@RequiresPermissions("inform-event:detail")
	@ResponseBody
	public ApiRestResponse<InformEventDTO> detail(@RequestParam("id") String id) throws Exception {
		
		InformEventEntity model = getInformEventService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("inform.event.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, InformEventDTO.class));
	}

	public IInformEventService getInformEventService() {
		return informEventService;
	}
	
}
