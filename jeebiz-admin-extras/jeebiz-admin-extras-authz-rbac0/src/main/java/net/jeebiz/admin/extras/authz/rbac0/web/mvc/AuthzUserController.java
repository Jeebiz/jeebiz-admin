/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.feature.setup.Constants;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserAllotRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserDetailModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserAllotRoleVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserDetailNewVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserDetailRenewVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserDetailVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserPaginationVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserResetVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

/**
 * 权限管理：用户管理
 */
@Api(tags = "权限管理：用户管理（Ok）")
@RestController
@RequestMapping(value = "/authz/user")
public class AuthzUserController extends BaseApiController {

	@Autowired
	private IAuthzUserService authzUserService;//用户管理SERVICE
	
	@ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "AuthzUserPaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("user:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
		AuthzUserDetailModel model = getBeanMapper().map(paginationVo, AuthzUserDetailModel.class);
		Page<AuthzUserDetailModel> pageResult = getAuthzUserService().getPagedList(model);
		List<AuthzUserDetailVo> retList = new ArrayList<AuthzUserDetailVo>();
		for (AuthzUserDetailModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserDetailVo.class));
		}
		
		return new Result<AuthzUserDetailVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "根据用户ID查询用户信息", notes = "根据用户ID查询用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "用户ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzUserDetailVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "查询用户-ID：${userid}", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("user:detail")
	@ResponseBody
	public Object detail(@PathVariable String id) throws Exception {
		AuthzUserDetailModel model = getAuthzUserService().getModel(id);
		if(model == null) {
			return fail("user.get.empty");
		}
		return getBeanMapper().map(model, AuthzUserDetailVo.class);
	}
	
	@ApiOperation(value = "根据认证信息中的用户ID查询用户详情", notes = "根据认证信息中的用户ID查询用户详情")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzUserDetailVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "根据认证信息中的用户ID查询用户详情", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public Object detail() throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserDetailModel model = getAuthzUserService().getModel(principal.getUserid());
		if(model == null) {
			return fail("user.get.empty");
		}
		return getBeanMapper().map(model, AuthzUserDetailVo.class);
	}
	
	@ApiOperation(value = "增加用户信息", notes = "增加用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "userVo", value = "用户信息", required = true, dataType = "AuthzUserDetailNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("user:new")
	@ResponseBody
	public Object newUser(@Valid @RequestBody AuthzUserDetailNewVo userVo) throws Exception { 
		int total = getAuthzUserService().getCountByName(userVo.getUsername());
		if(total > 0) {
			return fail("user.new.exists");
		}
		AuthzUserDetailModel model = getBeanMapper().map(userVo, AuthzUserDetailModel.class);
		int result = getAuthzUserService().insert(model);
		if(result == 1) {
			return success("user.new.success", result);
		}
		return fail("user.new.fail", result);
	}
	
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "userVo", value = "用户信息", required = true, dataType = "AuthzUserDetailRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("user:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzUserDetailRenewVo userVo) throws Exception { 
		AuthzUserDetailModel model = getBeanMapper().map(userVo, AuthzUserDetailModel.class);
		int result = getAuthzUserService().update(model);
		if(result == 1) {
			return success("user.renew.success", result);
		}
		return fail("user.renew.fail", result);
	}
	
	@ApiOperation(value = "更新用户状态", notes = "更新用户状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "form", name = "id", required = true, value = "用户ID", dataType = "String"),
		@ApiImplicitParam(paramType = "form", name = "status", required = true, value = "用户状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "更新用户状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("user:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzUserService().setStatus(id, status);
		if(result == 1) {
			return success("user.status.success", result);
		}
		return fail("user.status.fail", result);
	}
	
	@ApiOperation(value = "删除用户信息", notes = "删除用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", value = "基础数据ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid}", opt = BusinessType.DELETE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("user:delete")
	@ResponseBody
	public Object delUser(@PathVariable String id) throws Exception {
		int total = getAuthzUserService().delete(id);
		if(total > 0) {
			return success("user.delete.success", total); 
		}
		return fail("user.delete.fail", total);
	}
	
	@ApiOperation(value = "删除用户信息（支持批量）", notes = "删除用户信息（支持批量）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "form", name = "ids", value = "基础数据ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid}", opt = BusinessType.DELETE)
	@PostMapping("delete")
	@RequiresPermissions("user:delete")
	@ResponseBody
	public Object delUsers(@RequestParam String ids) throws Exception {
		// 执行基础数据删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int total = getAuthzUserService().batchDelete(idList);
		if(total > 0) {
			return success("user.delete.success", total); 
		}
		return fail("user.delete.fail", total);
	}
	
	@ApiOperation(value = "分页查询用户已分配角色信息", notes = "分页查询用户已分配角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "已分配角色信息筛选条件", dataType = "AuthzUserPaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户已分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("user:allocated")
	@ResponseBody
	public Object allocated(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
		AuthzUserModel model = getBeanMapper().map(paginationVo, AuthzUserModel.class);
		Page<AuthzRoleModel> pageResult = getAuthzUserService().getPagedAllocatedList(model);
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, AuthzRoleVo.class));
		}
		return new Result<AuthzRoleVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "分页查询用户未分配角色信息", notes = "分页查询用户未分配角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "未分配角色信息筛选条件", dataType = "AuthzUserPaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户未分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("user:unallocated")
	@ResponseBody
	public Object unallocated(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
		AuthzUserModel model = getBeanMapper().map(paginationVo, AuthzUserModel.class);
		Page<AuthzRoleModel> pageResult = getAuthzUserService().getPagedUnAllocatedList(model);
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, AuthzRoleVo.class));
		}
		return new Result<AuthzRoleVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "给指定用户分配角色", notes = "给指定用户分配角色")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "用户分配的角色信息", dataType = "AuthzUserAllotRoleVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "给指定用户分配角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("user:allot")
	@ResponseBody
	public Object allot(@Valid @RequestBody AuthzUserAllotRoleVo allotVo) throws Exception { 
		AuthzUserAllotRoleModel model = getBeanMapper().map(allotVo, AuthzUserAllotRoleModel.class);
		int total = getAuthzUserService().doAllot(model);
		return success("user.allot.success", total); 
	}
	
	@ApiOperation(value = "取消已分配给指定用户的角色", notes = "取消已分配给指定用户的角色")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "用户取消分配的角色信息", dataType = "AuthzUserAllotRoleVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "取消已分配给指定用户的角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("user:unallot")
	@ResponseBody
	public Object unallot(@Valid @RequestBody AuthzUserAllotRoleVo allotVo) throws Exception { 
		AuthzUserAllotRoleModel model = getBeanMapper().map(allotVo, AuthzUserAllotRoleModel.class);
		int total = getAuthzUserService().doUnAllot(model);
		return success("user.unallot.success", total); 
	}
	
	/*------------个人功能-----------------------*/
	
	@ApiOperation(value = "user:reset-info", notes = "设置个人信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "resetVo", value = "用户信息", dataType = "AuthzUserResetVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置个人信息-名称：${username}", opt = BusinessType.UPDATE)
	@PostMapping("reset/info")
	@RequiresAuthentication
	@ResponseBody
	public Object resetInfo(@Valid @RequestBody AuthzUserResetVo resetVo) throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserDetailModel model = getBeanMapper().map(resetVo, AuthzUserDetailModel.class);
		model.setId(principal.getUserid());
		int total = getAuthzUserService().update(model);
		if(total > 0) {
			return success("user.reset.info.success", total); 
		}
		return fail("user.reset.info.fail", total);
	}
	
	@ApiOperation(value = "重置当前登录用户密码", notes = "重置当前登录用户密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oldPassword", required = true, value = "当前密码", dataType = "String"),
		@ApiImplicitParam(name = "password", required = true, value = "新密码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置密码", opt = BusinessType.UPDATE)
	@PostMapping("reset/pwd")
	@RequiresAuthentication
	@ResponseBody
	public Object resetPwd(@RequestParam String oldPassword, @RequestParam String password) throws Exception {
		// 密码加密
		oldPassword = Base64.getEncoder().encodeToString(new String(oldPassword).getBytes());
		password = Base64.getEncoder().encodeToString(new String(password).getBytes());
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getAuthzUserService().resetPwd(principal.getUserid(), oldPassword, password);
		if(total > 0) {
			return success("user.reset.pwd.success", total); 
		}
		return fail("user.reset.pwd.fail", total);
	}
	
	@ApiOperation(value = "查询已分配给当前用户所属角色的权限", notes = "查询已分配给当前用户所属角色的权限")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = String.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "查询已分配给当前用户所属角色的权限", opt = BusinessType.SELECT)
	@GetMapping("perms")
	@RequiresAuthentication
	@ResponseBody
	public Object perms() throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		return ApiRestResponse.success(getAuthzUserService().getPermissions(principal.getUserid()));
	}
	
	public IAuthzUserService getAuthzUserService() {
		return authzUserService;
	}

	public void setAuthzUserService(IAuthzUserService authzUserService) {
		this.authzUserService = authzUserService;
	}
	
}
