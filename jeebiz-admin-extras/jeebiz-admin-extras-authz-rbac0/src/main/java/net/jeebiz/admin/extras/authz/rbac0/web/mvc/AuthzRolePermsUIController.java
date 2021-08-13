/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 权限管理：角色功能权限界面跳转
 */
@Api(tags = "权限管理：角色功能权限界面跳转（Ok）")
@Controller
@RequestMapping(value = "/authz/role/perms/ui/")
public class AuthzRolePermsUIController extends BaseMapperController {

	@Autowired
	private IAuthzRoleService authzRoleService;//角色管理SERVICE
	
	@ApiIgnore
	@RequestMapping("perms")
	@RequiresPermissions("role:perms")
	public String perms() {
		return "html/authz/rbac0/role/perms";
	}
	
	@ApiIgnore
	@RequestMapping("unperms")
	@RequiresPermissions("role:unperms")
	public String unperms() {
		return "html/authz/rbac0/role/unperms";
	}
	
	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

	public void setAuthzRoleService(IAuthzRoleService authzRoleService) {
		this.authzRoleService = authzRoleService;
	}

}
