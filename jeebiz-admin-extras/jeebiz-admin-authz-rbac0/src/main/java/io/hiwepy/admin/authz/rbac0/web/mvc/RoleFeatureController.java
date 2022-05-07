/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.web.mvc;

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
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.admin.authz.feature.enums.FeatureNodeType;
import io.hiwepy.admin.authz.feature.service.IFeatureService;
import io.hiwepy.admin.authz.feature.setup.Constants;
import io.hiwepy.admin.authz.feature.strategy.FeatureStrategyRouter;
import io.hiwepy.admin.authz.feature.web.dto.FeatureTreeNode;
import io.hiwepy.admin.authz.rbac0.service.IRoleFeatureService;
import io.hiwepy.admin.authz.rbac0.service.IRoleService;
import io.hiwepy.admin.authz.rbac0.service.IUserAccountService;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping(value = "/authorized/feature/")
public class RoleFeatureController extends BaseApiController {

	@Autowired
	private IUserAccountService authzUserService;
	@Autowired
	private IRoleService authzRoleService;
	@Autowired
	private IFeatureService featureService;
	@Autowired
	private IRoleFeatureService authorizedFeatureService;
	@Autowired
	private FeatureStrategyRouter featureStrategyRouter;

	@ApiOperation(value = "功能菜单-树形结构数据（全部菜单数据）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("tree")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> nav(){
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		List<FeatureEntity> featureList = getAuthorizedFeatureService().getFeatures(principal.getRoleid());
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList));
	}

	@ApiOperation(value = "功能菜单-树形结构数据（当前登录用户）", notes = "根据服务id及等登录人信息查询该服务的功能菜单-树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("tree-opts")
	@RequiresAuthentication
	public ApiRestResponse<List<FeatureTreeNode>> tree(){
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		List<FeatureEntity> featureList = getAuthorizedFeatureService().getFeatures(principal.getRoleid());
		// 所有的功能菜单
		List<FeatureOptEntity> featureOptList = getAuthorizedFeatureService().getFeatureOpts(principal.getRoleid());
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
	}

	@ApiOperation(value = "功能菜单-树形结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-树形结构数据")
	@GetMapping("treeForRole")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> tree(@RequestParam("roleId") String roleId){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<FeatureOptEntity> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
	}

	@ApiOperation(value = "指定功能菜单-树形结构数据（当前登录用户）", notes = "根据功能菜单id及等登录人信息查询该功能菜单的子功能菜单-树形结构数据")
	@GetMapping("children")
	@RequiresAuthentication
	public ApiRestResponse<FeatureTreeNode> children(@RequestParam String featureId){
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		FeatureTreeNode featureTree = getAuthorizedFeatureService().getChildFeatures(principal.getRoleid(), featureId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureTree);
	}

	@ApiOperation(value = "功能菜单-扁平结构数据（当前登录用户）", notes = "根据服务id及等登录人信息查询该服务的功能菜单-树形结构数据")
	@GetMapping("flat")
	@RequiresAuthentication
	public ApiRestResponse<List<FeatureTreeNode>> flat(){
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 所有的功能菜单
		List<FeatureEntity> featureList = getAuthorizedFeatureService().getFeatures(principal.getRoleid());
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList));
	}

	@ApiOperation(value = "功能菜单-扁平结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-扁平结构数据")
	@GetMapping("flatForRole")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> flat(@RequestParam("roleId") String roleId){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<FeatureOptEntity> featureOptList = getAuthorizedFeatureService().getFeatureOpts(roleId);
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList, featureOptList));
	}

	public IRoleService getRoleService() {
		return authzRoleService;
	}

	public IUserAccountService getUserService() {
		return authzUserService;
	}

	public IRoleFeatureService getAuthorizedFeatureService() {
		return authorizedFeatureService;
	}

	public IFeatureService getFeatureService() {
		return featureService;
	}

}
