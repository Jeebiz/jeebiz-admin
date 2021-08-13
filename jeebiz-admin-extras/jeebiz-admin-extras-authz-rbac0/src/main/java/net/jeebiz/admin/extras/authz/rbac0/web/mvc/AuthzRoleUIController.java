/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureFlatDataHandler;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureTreeDataHandler;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 权限管理：角色界面管理
 */
@Api(tags = "权限管理：角色界面管理（Ok）")
@Controller
@RequestMapping(value = "/authz/role/ui/")
public class AuthzRoleUIController extends BaseMapperController {

	@Autowired
	private IAuthzRoleService authzRoleService;//角色管理SERVICE
	@Autowired
	private IAuthzFeatureService authzFeatureService;
	@Autowired
	private IAuthzFeatureOptService authzFeatureOptService;
	@Autowired
	private FeatureTreeDataHandler featureTreeDataHandler;
	@Autowired
	private FeatureFlatDataHandler featureFlatDataHandler;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("role:list")
	public String list() {
		return "html/authz/rbac0/role/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("role:new")
	public String newRole(Model model) {
		
		// 所有的功能菜单
		//List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		//List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		//model.addAttribute("features", getFeatureFlatDataHandler().handle(featureList, featureOptList));
		
		return "html/authz/rbac0/role/new";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "角色信息ID", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("role:renew")
	public String renew(@PathVariable("id") String id, Model model) {
		// 所有的功能菜单
		//List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		//List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(id);
		// 返回各级菜单 + 对应的功能权限数据
		//model.addAttribute("features", getFeatureFlatDataHandler().handle(featureList, featureOptList));
		AuthzRoleModel roleModel = getAuthzRoleService().getModel(id);
		model.addAttribute("model", roleModel );
		model.addAttribute("perms", StringUtils.join(roleModel.getPerms().iterator(), ","));
		return "html/authz/rbac0/role/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "角色信息ID", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("role:detail")
	public String detail(@PathVariable("id") String id, Model model) {
		// 所有的功能菜单
		//List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		//List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(id);
		// 返回各级菜单 + 对应的功能权限数据
		//model.addAttribute("features", getFeatureFlatDataHandler().handle(featureList, featureOptList));
		AuthzRoleModel roleModel = getAuthzRoleService().getModel(id);
		model.addAttribute("model", roleModel );
		model.addAttribute("perms", StringUtils.join(roleModel.getPerms().iterator(), ","));
		return "html/authz/rbac0/role/detail";
	}

	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public FeatureTreeDataHandler getFeatureTreeDataHandler() {
		return featureTreeDataHandler;
	}

	public FeatureFlatDataHandler getFeatureFlatDataHandler() {
		return featureFlatDataHandler;
	}

}
