/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureNewDTO;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.web.BaseMapperController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping("feature")
public class AuthzFeatureController extends BaseMapperController{

	@Autowired
	protected IAuthzFeatureService authzFeatureService;
	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	@ApiOperation(value = "功能菜单（全部数据）", notes = "查询功能菜单列表")
	@GetMapping("list")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzFeatureDTO>> list(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		List<AuthzFeatureDTO> featureDTOList = Lists.newArrayList();
		for (AuthzFeatureModel model : featureList) {
			featureDTOList.add(getBeanMapper().map(model, AuthzFeatureDTO.class));
		}
		return ApiRestResponse.success(featureDTOList);
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（全部菜单数据）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("nav")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> nav(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList));
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（包含功能操作按钮）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("tree")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> tree(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
		//return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（包含功能操作按钮）", notes = "查询功能菜单树形结构数据且自定义数据处理器")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("tree/tag")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> treeByTag(@RequestParam("tag") String tag){
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
	public ApiRestResponse<List<AuthzFeatureDTO>> flat(){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-扁平结构数据（全部数据）", notes = "查询功能菜单扁平结构数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat/tag")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> flatByTag(@RequestParam("tag") String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler(tag).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "增加功能菜单信息", notes = "增加功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "AuthzFeatureNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "新增功能菜单-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("feature:new")
	@ResponseBody
	public ApiRestResponse<String> feature(@Valid @RequestBody AuthzFeatureNewDTO featureDTO) throws Exception { 
		int count = getAuthzFeatureService().getCountByCode(featureDTO.getCode(), null);
		if(count > 0) {
			return fail("feature.new.code-exists");
		}
		AuthzFeatureModel model = getBeanMapper().map(featureDTO, AuthzFeatureModel.class);
		/**
		 * 菜单类型(1:原生|2:自定义)
		 */
		model.setType("2");
		int total = getAuthzFeatureService().insert(model);
		if(total > 0) {
			// 删除菜单缓存
			getRedisTemplate().delete(Constants.AUTHZ_FEATURE_CACHE);
			return success("feature.new.success", total);
		}
		return fail("feature.new.fail", total);
	}
	
	@ApiOperation(value = "修改功能菜单信息", notes = "修改功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "AuthzFeatureRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "修改功能菜单-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("feature:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzFeatureRenewDTO featureDTO) throws Exception { 
		AuthzFeatureModel model = getBeanMapper().map(featureDTO, AuthzFeatureModel.class);
		int total = getAuthzFeatureService().update(model);
		if(total > 0) {
			// 删除菜单缓存
			getRedisTemplate().delete(Constants.AUTHZ_FEATURE_CACHE);
			return success("feature.renew.success", total);
		}
		return fail("feature.renew.fail", total);
	}
	
	@ApiOperation(value = "查询功能菜单信息", notes = "根据功能菜单ID查询功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "功能菜单ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单-ID：${featureid}", opt = BusinessType.SELECT)
	@PostMapping("detail")
	@RequiresPermissions("feature:detail")
	@ResponseBody
	public ApiRestResponse<AuthzFeatureDTO> detail(@RequestParam("id") String id) throws Exception {
		AuthzFeatureModel model = getAuthzFeatureService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("feature.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzFeatureDTO.class)) ;
	}
	
	@ApiOperation(value = "删除功能菜单信息", notes = "删除功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能菜单ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "删除功能菜单-名称：${featureid}", opt = BusinessType.DELETE)
	@PostMapping("delete")
	@RequiresPermissions("feature:delete")
	@ResponseBody
	public ApiRestResponse<String> delFeature(@RequestParam String id) throws Exception { 
		
		int count = getAuthzFeatureService().getCountByParent(id);
		if(count > 0) {
			return fail("feature.delete.child-exists");
		}
		
		int total = getAuthzFeatureService().delete(id);
		if(total > 0) {
			// 删除菜单缓存
			getRedisTemplate().delete(Constants.AUTHZ_FEATURE_CACHE);
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

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
	}
}
