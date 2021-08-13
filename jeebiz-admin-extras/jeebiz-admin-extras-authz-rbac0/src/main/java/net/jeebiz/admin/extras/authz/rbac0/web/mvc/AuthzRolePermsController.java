/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.authz.feature.setup.Constants;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRolePermsModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRolePermsService;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleAllotPermsVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;

/**
 * 权限管理：角色功能权限
 */
@Api(tags = "权限管理：角色功能权限（Ok）")
@RestController
@RequestMapping(value = "/authz/role/perms/")
public class AuthzRolePermsController extends BaseApiController {

	@Autowired
	private IAuthzRolePermsService authzRolePermsService;//角色权限管理SERVICE
	
	@ApiOperation(value = "给指定角色分配功能权限", notes = "给指定角色分配功能权限")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "permsVo", value = "角色分配的功能权限信息", dataType = "AuthzRoleAllotPermsVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE_PERMS, business = "给指定角色分配权限，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("perms")
	@RequiresPermissions("role:perms")
	@ResponseBody
	public Object perms(@Valid @RequestBody AuthzRoleAllotPermsVo permsVo) throws Exception { 
		
		AuthzRolePermsModel permsModel = getBeanMapper().map(permsVo, AuthzRolePermsModel.class);
		int total = getAuthzRolePermsService().doPerms(permsModel);
		if(total > 0) {
			return success("role.perms.success", total); 
		}
		return fail("role.perms.fail", total); 
	}
	
	@ApiOperation(value = "取消已分配给指定角色的权限", notes = "取消已分配给指定角色的权限")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "permsVo", value = "角色取消分配的权限信息", dataType = "AuthzRoleAllotPermsVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE_PERMS, business = "取消已分配给指定角色的权限", opt = BusinessType.DELETE)
	@PostMapping("unperms")
	@RequiresPermissions("role:unperms")
	@ResponseBody
	public Object unperms(@Valid @RequestBody AuthzRoleAllotPermsVo permsVo) throws Exception { 
		AuthzRolePermsModel permsModel = getBeanMapper().map(permsVo, AuthzRolePermsModel.class);
		int total = getAuthzRolePermsService().unPerms(permsModel);
		if(total > 0) {
			return success("role.unperms.success", total); 
		}
		return fail("role.unperms.fail", total);
	}
	
	public IAuthzRolePermsService getAuthzRolePermsService() {
		return authzRolePermsService;
	}

	public void setAuthzRolePermsService(IAuthzRolePermsService authzRolePermsService) {
		this.authzRolePermsService = authzRolePermsService;
	}

}
