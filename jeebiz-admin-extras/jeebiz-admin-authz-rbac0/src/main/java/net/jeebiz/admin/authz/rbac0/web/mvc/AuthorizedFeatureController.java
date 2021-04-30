/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.mvc;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;
import net.jeebiz.admin.authz.rbac0.service.IAuthorizedFeatureService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping(value = "/authorized/feature/")
public class AuthorizedFeatureController extends BaseApiController {

	@Autowired
	private IAuthzUserService authzUserService;
	@Autowired
	private IAuthzRoleService authzRoleService;
	@Autowired
	private IAuthzFeatureService authzFeatureService;
	@Autowired
	private IAuthorizedFeatureService authorizedFeatureService;

    @ApiOperation(value = "功能菜单-树形结构数据（当前登录用户）", notes = "根据服务id及等登录人信息查询该服务的功能菜单-树形结构数据")
	@GetMapping("tree")
    @RequiresAuthentication
	public ApiRestResponse<List<AuthzFeatureDTO>> tree(){
		// 登录账号信息
    	ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthorizedFeatureService().getFeatures(principal.getRoleid());
		// 所有的功能菜单
		List<AuthzFeatureOptModel> featureOptList = getAuthorizedFeatureService().getFeatureOpts(principal.getRoleid());
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}

	@ApiOperation(value = "指定功能菜单-树形结构数据（当前登录用户）", notes = "根据功能菜单id及等登录人信息查询该功能菜单的子功能菜单-树形结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "fid", required = false, value = "功能菜单id", dataType = "String")
	})
	@GetMapping("children")
	@RequiresAuthentication
	public ApiRestResponse<AuthzFeatureDTO> children(@RequestParam String featureId){
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		AuthzFeatureDTO featureTree = getAuthorizedFeatureService().getChildFeatures(principal.getRoleid(), featureId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureTree);
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-树形结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "roleId", required = true, value = "角色id", dataType = "String")
	})
	@GetMapping("features/tree")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> tree(@RequestParam("roleId") String roleId){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-树形结构数据（指定角色、数据处理）", notes = "根据角色id查询角色拥有的功能菜单-树形结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "roleId", required = true, value = "角色id", dataType = "String"),
		@ApiImplicitParam( paramType = "query", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@GetMapping("features/tree/tag")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> treeByTag(@RequestParam("roleId") String roleId, 
			@RequestParam("tag") String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler(tag).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-扁平结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-扁平结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "roleId", required = true, value = "角色id", dataType = "String")
	})
	@GetMapping("features/flat")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> flat(@RequestParam("roleId") String roleId){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "功能菜单-扁平结构数据（指定角色、数据处理）", notes = "根据角色id查询角色拥有的功能菜单-扁平结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "roleId", required = true, value = "角色id", dataType = "String"),
		@ApiImplicitParam( paramType = "query", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@GetMapping("features/flat/tag")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<AuthzFeatureDTO>> flatByTag(@RequestParam("roleId") String roleId,
			@RequestParam("tag") String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler(tag).handle(featureList, featureOptList));
	}
	
	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}
	
	public IAuthzUserService getAuthzUserService() {
		return authzUserService;
	}
	
	public IAuthorizedFeatureService getAuthorizedFeatureService() {
		return authorizedFeatureService;
	}
	
	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}
	
}
