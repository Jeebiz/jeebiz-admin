/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.service.IInformTemplateService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateNewDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplatePaginationDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateRenewDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "消息通知模板：消息模板管理")
@RestController
@RequestMapping("/inform/tmpl/")
public class InformTemplateController extends BaseMapperController {
	
	@Autowired
	private IInformTemplateService informTemplateService;
	
	@ApiOperation(value = "查询消息通知模板", notes = "分页查询消息通知模板")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "消息筛选条件", dataType = "InformTemplatePaginationDTO")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知模板", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public Result<InformTemplateDTO> list(@Valid @RequestBody InformTemplatePaginationDTO paginationDTO) throws Exception {
		
		InformTemplateEntity model = getBeanMapper().map(paginationDTO, InformTemplateEntity.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		if(!principal.isAdmin()) {
			model.setUserId(principal.getUserid());
		}
		Page<InformTemplateEntity> pageResult = getInformTemplateService().getPagedList(model);
		List<InformTemplateDTO> retList = new ArrayList<InformTemplateDTO>();
		for (InformTemplateEntity registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformTemplateDTO.class));
		}
		
		return new Result<InformTemplateDTO>(pageResult, retList);
		
	}

	@ApiOperation(value = "消息通知模板统计信息", notes = "查询消息通知模板统计信息")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知模板统计信息", opt = BusinessType.SELECT)
	@GetMapping("stats")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<InformTemplateStatsDTO>> stats() throws Exception {
		return ApiRestResponse.success(getInformTemplateService().getStats());
	}
	
	@ApiOperation(value = "创建消息通知模板", notes = "增加一个新的消息通知模板")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "newDTO", value = "消息通知模板传输对象", dataType = "InformTemplateNewDTO") 
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "创建消息通知模板", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("inform-tmpl:new")
	@ResponseBody
	public ApiRestResponse<String> newTmpl(@Valid @RequestBody InformTemplateNewDTO newDTO) throws Exception {
		InformTemplateEntity model = getBeanMapper().map(newDTO, InformTemplateEntity.class);
		
		Long ct = getInformTemplateService().getCount(model);
		if(ct > 0) {
			return fail("inform.template.new.conflict");
		}
		// 新增一条数据库配置记录
		boolean result = getInformTemplateService().save(model);
		if(result) {
			return success("inform.template.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.template.new.fail", result);
	}
	
	@ApiOperation(value = "删除消息通知模板", notes = "删除消息通知模板")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "消息通知模板id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知模板", opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("inform-tmpl:delete")
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam("ids") String ids) throws Exception {
		// 执行消息通知模板删除操作
		List<String> idList = Lists.newArrayList(StringUtils.split(ids,","));
		boolean result = getInformTemplateService().removeByIds(idList);
		if(result) {
			return success("inform.template.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.template.delete.fail", result);
	}
	
	@ApiOperation(value = "更新消息通知模板", notes = "更新消息通知模板")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewDTO", value = "消息通知模板", required = true, dataType = "InformTemplateRenewDTO"),
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "更新消息通知模板", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("inform-tmpl:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody InformTemplateRenewDTO renewDTO) throws Exception {
		InformTemplateEntity model = getBeanMapper().map(renewDTO, InformTemplateEntity.class);
		Long ct = getInformTemplateService().getCount(model);
		if(ct > 0) {
			return fail("inform.template.renew.conflict");
		}
		boolean result = getInformTemplateService().updateById(model);
		if(result) {
			return success("inform.template.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("inform.template.renew.fail", result);
	}
	
	@ApiOperation(value = "消息通知模板信息", notes = "查询指定id的消息通知模板信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", value = "消息通知模板id", required = true, dataType = "String"),
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询指定id的消息通知模板信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("inform-tmpl:detail")
	@ResponseBody
	public ApiRestResponse<InformTemplateDTO> detail(@RequestParam("id") String id) throws Exception {
		
		InformTemplateEntity model = getInformTemplateService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("inform.template.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, InformTemplateDTO.class));
	}

	public IInformTemplateService getInformTemplateService() {
		return informTemplateService;
	}
	
}
