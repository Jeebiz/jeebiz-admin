/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.mvc;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.setup.Constants;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptNewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptRenewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "功能操作：数据维护（Ok）")
@RestController
@RequestMapping(value = "/extras/feature/opt/")
public class AuthzFeatureOptController extends BaseApiController{

	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;
	
	@ApiOperation(value = "增加功能操作代码信息", notes = "增加功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "optVo", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "新增功能操作代码-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("opt:new")
	@ResponseBody
	public Object newOpt(@Valid @RequestBody AuthzFeatureOptNewVo optVo) throws Exception {
		
		int count = getAuthzFeatureOptService().getOptCountByName(optVo.getName(), optVo.getFeatureId(), null);
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		
		AuthzFeatureOptModel model = getBeanMapper().map(optVo, AuthzFeatureOptModel.class);
		int total = getAuthzFeatureOptService().insert(model);
		if(total > 0) {
			return success("opt.new.success", total);
		}
		return fail("opt.new.fail", total);
	}
	
	@ApiOperation(value = "修改功能操作代码", notes = "修改功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "optVo", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "修改功能操作代码-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("opt:renew")
	@ResponseBody
	public Object renewOpt(@Valid @RequestBody AuthzFeatureOptRenewVo optVo) throws Exception {
		
		int count = getAuthzFeatureOptService().getOptCountByName(optVo.getName(), optVo.getFeatureId(), optVo.getId());
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		
		AuthzFeatureOptModel model = getBeanMapper().map(optVo, AuthzFeatureOptModel.class);
		int total = getAuthzFeatureOptService().update(model);
		if(total > 0) {
			return success("opt.renew.success", total);
		}
		return fail("opt.renew.fail", total);
	}
	
	@ApiOperation(value = "查询功能操作代码信息", notes = "根据功能操作代码ID查询功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能操作代码ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzFeatureOptVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "查询功能操作代码-ID：${optid}", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("opt:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		AuthzFeatureOptModel model = getAuthzFeatureOptService().getModel(id);
		if( model == null) {
			return fail("opt.get.empty");
		}
		return getBeanMapper().map(model, AuthzFeatureOptVo.class);
	}
	
	@ApiOperation(value = "删除功能操作代码信息", notes = "删除功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能操作代码ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "删除功能操作代码-名称：${optid}", opt = BusinessType.DELETE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("opt:delete")
	@ResponseBody
	public Object delOpt(@PathVariable String id) throws Exception { 
		int total = getAuthzFeatureOptService().delete(id);
		if(total > 0) {
			return success("opt.delete.success", total);
		}
		return success("opt.delete.fail", total);
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public void setAuthzFeatureOptService(IAuthzFeatureOptService authzFeatureOptService) {
		this.authzFeatureOptService = authzFeatureOptService;
	}
	
}
