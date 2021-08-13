/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.StringUtils;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.inform.dao.entities.InformModel;
import net.jeebiz.admin.extras.inform.service.IInformService;
import net.jeebiz.admin.extras.inform.setup.Constants;
import net.jeebiz.admin.extras.inform.web.vo.InformPaginationVo;
import net.jeebiz.admin.extras.inform.web.vo.InformSendVo;
import net.jeebiz.admin.extras.inform.web.vo.InformVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "消息通知")
@RestController
@RequestMapping("/extras/inform/")
public class InformController extends BaseApiController {
	
	@Autowired
	private IInformService informService;

	@ApiOperation(value = "发送消息或通知", notes = "发送消息或通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "informVo", value = "用户信息", dataType = "InformSendVo")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("send")
	@RequiresPermissions("inform:send")
	@ResponseBody
	public Object send(@Valid @RequestBody InformSendVo informVo) throws Exception { 
		
		InformModel model = getBeanMapper().map(informVo, InformModel.class);
		int result = getInformService().insert(model);
		if(result == 1) {
			return success("inform.send.success", result);
		}
		return fail("inform.send.fail", result);
	}
	
	@ApiOperation(value = "待处理通知总数", notes = "查询待处理通知总数")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询待处理通知总数", opt = BusinessType.SELECT)
	@PostMapping(value = "pending")
	@RequiresAuthentication
	@ResponseBody
	public Object pending() throws Exception {
		
		InformModel model = new InformModel();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		
		return getInformService().getCount(model);
	}
	
	@ApiOperation(value = "查询消息通知", notes = "分页查询消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "消息筛选条件", dataType = "InformPaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public Object list(@Valid InformPaginationVo paginationVo) throws Exception {
		
		InformModel model = getBeanMapper().map(paginationVo, InformModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		
		Page<InformModel> pageResult = getInformService().getPagedList(model);
		List<InformVo> retList = new ArrayList<InformVo>();
		for (InformModel registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformVo.class));
		}
		
		return new Result<InformVo>(pageResult, retList);
		
	}

	@ApiOperation(value = "消息通知统计信息", notes = "查询消息通知统计信息")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知统计信息", opt = BusinessType.SELECT)
	@GetMapping("stats")
	@RequiresAuthentication
	@ResponseBody
	public Object stats() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		return getInformService().getStats(principal.getUserid());
	}
	
	@ApiOperation(value = "消息通知信息", notes = "查询指定ID的消息通知信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", value = "消息通知ID", required = true, dataType = "String"),
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = InformVo.class)
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询指定ID的消息通知信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresAuthentication
	@ResponseBody
	public Object detail(@PathVariable String id) throws Exception {
		
		InformModel model = getInformService().getModel(id);
		if(model == null) {
			return fail("inform.get.empty");
		}
		return getBeanMapper().map(model, InformVo.class);
		
	}
	
	@ApiOperation(value = "删除消息通知", notes = "删除消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresAuthentication
	@ResponseBody
	public Object delete(@RequestParam String ids) throws Exception {
		// 执行消息通知删除操作
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int result = getInformService().delInforms(principal.getUserid(), Lists.newArrayList(StringUtils.tokenizeToStringArray(ids)));
		if(result > 0) {
			return success("inform.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.delete.error", result);
	}
	
	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@GetMapping("read")
	@RequiresAuthentication
	@ResponseBody
	public Object read(@RequestParam String ids) throws Exception {
		
		InformModel model = new InformModel();
		model.setIds(Lists.newArrayList(StringUtils.tokenizeToStringArray(ids)));
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
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
	public Object readall() throws Exception {
		
		InformModel model = new InformModel();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		model.setStatus("1");
		
		// 执行消息通知阅读操作
		int result = getInformService().update(model);
		if(result == 1) {
			return success("inform.readall.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.readall.error", result);
	}

	public IInformService getInformService() {
		return informService;
	}

	public void setInformService(IInformService informService) {
		this.informService = informService;
	}
	
}
