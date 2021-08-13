/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.extras.authz.feature.setup.Constants;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureNewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureRenewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping(value = "/extras/feature/")
public class AuthzFeatureController extends BaseApiController{

	@Autowired
	protected IAuthzFeatureService authzFeatureService;
	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;
	
	@ApiOperation(value = "功能菜单-树形结构数据（全部数据）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@PostMapping("tree")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object tree(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
		//return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（全部数据）", notes = "查询功能菜单树形结构数据且自定义数据处理器")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@PostMapping("tree/{tag}")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object treeByTag(@PathVariable("tag") String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler(tag).handle(featureList, featureOptList));
		//return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-扁平结构数据（全部数据）", notes = "查询功能菜单扁平结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object flat(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-扁平结构数据（全部数据）", notes = "查询功能菜单扁平结构数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat/{tag}")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object flatByTag(@PathVariable("tag") String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler(tag).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "增加功能菜单信息", notes = "增加功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureVo", value = "功能菜单信息", dataType = "AuthzFeatureNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "新增功能菜单-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("feature:new")
	@ResponseBody
	public Object feature(@Valid @RequestBody AuthzFeatureNewVo featureVo) throws Exception { 
		int count = getAuthzFeatureService().getCountByCode(featureVo.getCode(), null);
		if(count > 0) {
			return fail("feature.new.code-exists");
		}
		AuthzFeatureModel model = getBeanMapper().map(featureVo, AuthzFeatureModel.class);
		/**
		 * 菜单类型(1:原生|2:自定义)
		 */
		model.setType("2");
		int total = getAuthzFeatureService().insert(model);
		if(total > 0) {
			return success("feature.new.success", total);
		}
		return fail("feature.new.fail", total);
	}
	
	@ApiOperation(value = "修改功能菜单信息", notes = "修改功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureVo", value = "功能菜单信息", dataType = "AuthzFeatureRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "修改功能菜单-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("feature:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzFeatureRenewVo featureVo) throws Exception { 
		AuthzFeatureModel model = getBeanMapper().map(featureVo, AuthzFeatureModel.class);
		int total = getAuthzFeatureService().update(model);
		if(total > 0) {
			return success("feature.renew.success", total);
		}
		return fail("feature.renew.fail", total);
	}
	
	@ApiOperation(value = "查询功能菜单信息", notes = "根据功能菜单ID查询功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能菜单ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzFeatureVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单-ID：${featureid}", opt = BusinessType.SELECT)
	@PostMapping("detail/{id}")
	@RequiresPermissions("feature:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		AuthzFeatureModel model = getAuthzFeatureService().getModel(id);
		if( model == null) {
			return fail("feature.get.empty");
		}
		return getBeanMapper().map(model, AuthzFeatureVo.class);
	}
	
	@ApiOperation(value = "删除功能菜单信息", notes = "删除功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能菜单ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "删除功能菜单-名称：${featureid}", opt = BusinessType.DELETE)
	@PostMapping("delete/{id}")
	@RequiresPermissions("feature:delete")
	@ResponseBody
	public Object delFeature(@PathVariable String id) throws Exception { 
		
		int count = getAuthzFeatureService().getCountByParent(id);
		if(count > 0) {
			return fail("feature.delete.child-exists");
		}
		
		int total = getAuthzFeatureService().delete(id);
		if(total > 0) {
			return success("feature.delete.success", total);
		}
		return fail("feature.delete.fail", total);
	}
	
	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}

	public void setAuthzFeatureService(IAuthzFeatureService authzFeatureService) {
		this.authzFeatureService = authzFeatureService;
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public void setAuthzFeatureOptService(IAuthzFeatureOptService authzFeatureOptService) {
		this.authzFeatureOptService = authzFeatureOptService;
	}
	
}
