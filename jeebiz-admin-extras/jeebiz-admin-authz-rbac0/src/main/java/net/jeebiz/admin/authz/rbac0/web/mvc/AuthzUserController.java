/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.mvc;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserAllotRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserAllotRoleVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserNewVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserPaginationVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserRegisterVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserRenewVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserResetVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.utils.StringUtils;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;

/**
 * 权限管理：用户管理
 */
@Api(tags = "权限管理：用户管理（Ok）")
@RestController
@RequestMapping(value = "/authz/user/")
public class AuthzUserController extends BaseMapperController {

	@Autowired
	private IAuthzUserService authzUserService;
	@Autowired
	private IAuthzRoleService authzRoleService;
	
	@ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "AuthzUserPaginationVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("user:list")
	public Result<AuthzUserVo> list(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
		AuthzUserModel model = getBeanMapper().map(paginationVo, AuthzUserModel.class);
		Page<AuthzUserModel> pageResult = getAuthzUserService().getPagedList(model);
		List<AuthzUserVo> retList = new ArrayList<AuthzUserVo>();
		for (AuthzUserModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserVo.class));
		}
		
		return new Result<AuthzUserVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "指定用户详情", notes = "根据用户ID查询用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "用户ID", dataType = "String")
	})
	@GetMapping("detailById")
	@RequiresPermissions("user:detail")
	public ApiRestResponse<AuthzUserVo> detailById(@RequestParam String id) throws Exception {
		AuthzUserModel model = getAuthzUserService().getModel(id);
		if(model == null) {
			return ApiRestResponse.empty(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzUserVo.class));
	}
	
	@ApiOperation(value = "登录用户详情", notes = "根据认证信息中的用户ID查询用户详情")
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<AuthzUserVo> detail() throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserModel model = getAuthzUserService().getModel(principal.getUserid());
		if(model == null) {
			return ApiRestResponse.empty(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzUserVo.class));
	}
	
	@ApiOperation(value = "注册用户信息", notes = "注册用户信息（默认访客角色）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "userVo", value = "用户信息", required = true, dataType = "AuthzUserRegisterVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("register")
	public ApiRestResponse<String> register(@Valid @RequestBody AuthzUserRegisterVo userVo) throws Exception {
		int total = getAuthzUserService().getCountByPhone(userVo.getPhone());
		if(total > 0) {
			return fail("user.register.exists");
		}
		AuthzUserModel model = getBeanMapper().map(userVo, AuthzUserModel.class);
		int result = getAuthzUserService().register(model);
		if(result == 1) {
			return success("user.register.success", result);
		}
		return fail("user.register.fail", result);
	}
	
	@ApiOperation(value = "增加用户信息", notes = "增加用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "userVo", value = "用户信息", required = true, dataType = "AuthzUserNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
    @RequiresPermissions("user:new")
	public ApiRestResponse<String> newUser(@Valid @RequestBody AuthzUserNewVo userVo) throws Exception {
		int total = getAuthzUserService().getCountByPhone(userVo.getPhone());
		if(total > 0) {
			return fail("user.new.exists");
		}
		int total2 = getAuthzUserService().getCountByEmail(userVo.getEmail());
		if(total2 > 0) {
			return fail("user.new.email.exists");
		}
		AuthzUserModel model = getBeanMapper().map(userVo, AuthzUserModel.class);
		int result = getAuthzUserService().insert(model);
		if(result == 1) {
			return success("user.new.success", result);
		}
		return fail("user.new.fail", result);
	}
	
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "userVo", value = "用户信息", required = true, dataType = "AuthzUserRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("user:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzUserRenewVo userVo) throws Exception { 
		AuthzUserModel model = getBeanMapper().map(userVo, AuthzUserModel.class);
		int countByPhone = getAuthzUserService().getCountUpdateByPhone(model.getPhone(),model.getId());
		if (countByPhone > 0){
			return fail("user.new.exists",countByPhone);
		}
		int total2 = getAuthzUserService().getCountUpdateByEmail(userVo.getEmail(),model.getId());
		if(total2 > 0) {
			return fail("user.new.email.exists");
		}
		int result = getAuthzUserService().update(model);
		if(result > 0) {
			return success("user.renew.success", result);
		}
		return fail("user.renew.fail", result);
	}

	@ApiOperation(value = "重置用户密码", notes = "根据密码初始化方式重置指定用户登录密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", required = true, value = "用户ID,多个用,拼接", dataType = "String"),
		@ApiImplicitParam(name = "type", required = true, value = "密码初始化方式（0：系统默认密码，1：身份证后6位，2：手机号后6位，3：学工号后6位）", dataType = "String", allowableValues = "0,1,2,3")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "根据密码初始化方式重置指定用户登录密码", opt = BusinessType.UPDATE)
	@GetMapping("resetpwd")
	@RequiresPermissions("user:resetpwd")
	public ApiRestResponse<String> resetPwd(@RequestParam String ids,
											@AllowableValues(allows = "0,1,2,3",message = "密码初始化方式（0：系统默认密码，1：身份证后6位，2：手机号后6位，3：学工号后6位）")
											@RequestParam(value = "type", required = false) String type) throws Exception {
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int total = getAuthzUserService().resetPwd(idList, type);
		if(total > 0) {
			return success("user.reset.pwd.success", total);
		}
		return fail("user.reset.pwd.fail", total);
	}
	
	@ApiOperation(value = "更新用户状态", notes = "更新用户状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "用户ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "用户状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "更新用户状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("user:status")
	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "用户状态错误") @RequestParam String status) throws Exception {
		int result = getAuthzUserService().setStatus(id, status);
		if(result == 1) {
			return success("user.status.success", result);
		}
		return fail("user.status.fail", result);
	}
	
	@ApiOperation(value = "删除用户信息", notes = "删除用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", value = "用户ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid}", opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("user:delete")
	public ApiRestResponse<String> delUser(@RequestParam("id") String id) throws Exception {
		int total = getAuthzUserService().deleteUserById(id);
		if(total > 0) {
			return success("user.delete.success", total); 
		}
		return fail("user.delete.fail", total);
	}
	
	@ApiOperation(value = "批量删除用户信息", notes = "批量删除用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "用户ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid} ", opt = BusinessType.DELETE)
	@GetMapping("deleteByIds")
	@RequiresPermissions("user:delete")
	public ApiRestResponse<String> delUsersByIds(@RequestParam String ids) throws Exception {
		// 执行用户ID删除操作
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
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户已分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("user:allocated")
	@ResponseBody
	public Result<AuthzRoleVo> allocated(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
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
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户未分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("user:unallocated")
	@ResponseBody
	public Result<AuthzRoleVo> unallocated(@Valid @RequestBody AuthzUserPaginationVo paginationVo){
		
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
	public ApiRestResponse<String> allot(@Valid @RequestBody AuthzUserAllotRoleVo allotVo) throws Exception { 
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
	public ApiRestResponse<String> unallot(@Valid @RequestBody AuthzUserAllotRoleVo allotVo) throws Exception { 
		AuthzUserAllotRoleModel model = getBeanMapper().map(allotVo, AuthzUserAllotRoleModel.class);
		int total = getAuthzUserService().doUnAllot(model);
		return success("user.unallot.success", total); 
	}
	
	/*------------个人功能-----------------------*/
	
	@ApiOperation(value = "设置个人信息", notes = "设置个人信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "resetVo", value = "用户信息", dataType = "AuthzUserResetVo")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置个人信息-名称：${username}", opt = BusinessType.UPDATE)
	@PostMapping("reset/info")
	@RequiresAuthentication
	public ApiRestResponse<String> resetInfo(@Valid @RequestBody AuthzUserResetVo resetVo) throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserModel model = getBeanMapper().map(resetVo, AuthzUserModel.class);
		model.setId(principal.getUserid());
		int total = getAuthzUserService().update(model);
		if(total > 0) {
			return success("user.reset.info.success", total); 
		}
		return fail("user.reset.info.fail", total);
	}
	
	@ApiOperation(value = "设置个人头像", notes = "设置个人头像")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "avatar", value = "用户头像：图片路径或图标样式", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置个人头像", opt = BusinessType.UPDATE)
	@GetMapping("reset/avatar")
	@RequiresAuthentication
	public ApiRestResponse<String> resetAvatar(@Valid @RequestParam("avatar") @NotBlank(message = "用户头像不能为空！") String avatar) throws Exception { 
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserModel model = new AuthzUserModel();
		model.setId(principal.getUserid());
		model.setAvatar(avatar);
		int total = getAuthzUserService().update(model);
		if(total > 0) {
			return success("user.reset.avatar.success", total); 
		}
		return fail("user.reset.avatar.fail", total);
	}
	
	@ApiOperation(value = "重置密码：当前登录用户", notes = "重置当前登录用户密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oldPassword", required = true, value = "当前密码", dataType = "String"),
		@ApiImplicitParam(name = "password", required = true, value = "新密码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置密码", opt = BusinessType.UPDATE)
	@GetMapping("reset/pwd")
	@RequiresAuthentication
	public ApiRestResponse<String> resetPwdSelf(@RequestParam(defaultValue = "123456") String oldPassword, @RequestParam String password) throws Exception {
		// 密码加密
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getAuthzUserService().resetPwd(principal.getUserid(), oldPassword, password);
		if(total > 0) {
			return success("user.reset.pwd.success", total); 
		}
		return fail("user.reset.pwd.fail", total);
	}
	
	@ApiOperation(value = "角色权限标记：当前登录用户", notes = "查询已分配给当前用户所属角色的权限")
	@GetMapping("perms")
	@RequiresAuthentication
	public ApiRestResponse<List<String>> perms() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		return ApiRestResponse.success(getAuthzUserService().getPermissions(principal.getUserid()));
	}
	
	@ApiOperation(value = "角色信息列表：当前登录用户", notes = "查询当前用户全部可用角色信息")
	@GetMapping("roles")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzRoleVo>> roles(){
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 	用户角色信息集合
		List<AuthzRoleModel> roles = getAuthzUserService().getRoles(principal.getUserid());
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel roleModel : roles) {
			retList.add(getBeanMapper().map(roleModel, AuthzRoleVo.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	public IAuthzUserService getAuthzUserService() {
		return authzUserService;
	}

	public void setAuthzUserService(IAuthzUserService authzUserService) {
		this.authzUserService = authzUserService;
	}
	
	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}
	
}
