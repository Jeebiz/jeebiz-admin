/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.web.mvc;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.authz.rbac0.service.IRolePermsService;
import net.jeebiz.admin.authz.rbac0.setup.Constants;
import net.jeebiz.admin.authz.rbac0.web.dto.RoleAllotPermsDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.web.BaseMapperController;

/**
 * 权限管理：角色功能权限
 */
@Api(tags = "权限管理：角色功能权限（Ok）")
@RestController
@RequestMapping(value = "/authz/role/perms/")
public class RolePermsController extends BaseMapperController {

    /**
     * 	角色权限管理SERVICE
     */
    @Autowired
    private IRolePermsService authzRolePermsService;

	@ApiOperation(value = "指定角色已授权功能列表（键值对）", notes = "查询指定角色已授权功能列表")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "roleId", required = true, value = "角色id", dataType = "String")
	})
	@GetMapping("pairs")
	@RequiresPermissions("role:perms")
	@ResponseBody
	public ApiRestResponse<List<PairModel>> list(@RequestParam String roleId) throws Exception {
		List<PairModel> roleList = getRolePermsService().getPairValues(roleId);
		if(roleList == null) {
			roleList = Lists.newArrayList();
		}
		return ApiRestResponse.success(roleList);
	}

	@ApiOperation(value = "给指定角色分配功能权限", notes = "给指定角色分配功能权限")
	@BusinessLog(module = Constants.AUTHZ_ROLE_PERMS, business = "给指定角色分配权限，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("perms")
	@RequiresPermissions("role:perms")
	@ResponseBody
	public ApiRestResponse<String> perms(@Valid @RequestBody RoleAllotPermsDTO permsDTO) throws Exception {

		int total = getRolePermsService().doPerms(permsDTO.getRoleId(), permsDTO.getPerms());
        // 删除菜单缓存
        //getRedisTemplate().delete(Redisc.AUTHZ_FEATURE_CACHE);
		if(total > 0) {
			return success("role.perms.success", total);
		}
		return fail("role.perms.fail", total);
	}

	@ApiOperation(value = "取消已分配给指定角色的权限", notes = "取消已分配给指定角色的权限")
	@BusinessLog(module = Constants.AUTHZ_ROLE_PERMS, business = "取消已分配给指定角色的权限", opt = BusinessType.DELETE)
	@PostMapping("unperms")
	@RequiresPermissions("role:unperms")
	@ResponseBody
	public ApiRestResponse<String> unperms(@Valid @RequestBody RoleAllotPermsDTO permsDTO) throws Exception {
		int total = getRolePermsService().unPerms(permsDTO.getRoleId(), permsDTO.getPerms());
        // 删除菜单缓存
        //getRedisTemplate().delete(Constants.AUTHZ_FEATURE_CACHE);
		if(total > 0) {
			return success("role.unperms.success", total);
		}
		return fail("role.unperms.fail", total);
	}

	public IRolePermsService getRolePermsService() {
		return authzRolePermsService;
    }

}
