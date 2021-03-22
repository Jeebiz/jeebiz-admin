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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRolePermsModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRolePermsService;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleAllotPermsVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;

/**
 * 权限管理：角色功能权限
 */
@Api(tags = "权限管理：角色功能权限（Ok）")
@ApiResponses({ 
	@ApiResponse(code = 0, message = "请求成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10001, message = "认证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10021, message = "授权失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10022, message = "Token缺失", response = ApiRestResponse.class),
	@ApiResponse(code = 10023, message = "Token已过期", response = ApiRestResponse.class),
	@ApiResponse(code = 10024, message = "Token已失效", response = ApiRestResponse.class),
	@ApiResponse(code = 10025, message = "Token错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10110, message = "不允许访问（功能未授权）", response = ApiRestResponse.class),
	@ApiResponse(code = 10111, message = "请求失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10112, message = "数据为空", response = ApiRestResponse.class),
	@ApiResponse(code = 10113, message = "参数类型不匹配", response = ApiRestResponse.class),
	@ApiResponse(code = 10114, message = "缺少矩阵变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10115, message = "缺少URI模板变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10116, message = "缺少Cookie变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10117, message = "缺少请求头", response = ApiRestResponse.class),
	@ApiResponse(code = 10118, message = "缺少参数", response = ApiRestResponse.class),
	@ApiResponse(code = 10119, message = "缺少请求对象", response = ApiRestResponse.class),
	@ApiResponse(code = 10120, message = "参数规则不满足", response = ApiRestResponse.class),
	@ApiResponse(code = 10121, message = "参数绑定错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10122, message = "参数解析错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10123, message = "参数验证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10201, message = "服务器：运行时异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10202, message = "服务器：空值异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10203, message = "服务器：数据类型转换异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10204, message = "服务器：IO异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10205, message = "服务器：未知方法异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10206, message = "服务器：非法参数异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10207, message = "服务器：数组越界异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10208, message = "服务器：网络异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping(value = "/authz/role/perms/")
public class AuthzRolePermsController extends BaseMapperController {

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
