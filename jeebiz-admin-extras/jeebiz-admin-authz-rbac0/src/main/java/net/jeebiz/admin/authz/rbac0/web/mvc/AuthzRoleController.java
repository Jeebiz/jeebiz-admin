/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.mvc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleAllotUserPaginationVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleAllotUserVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleNewVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRolePaginationVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleRenewVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleUserVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzRoleVo;
import net.jeebiz.admin.authz.rbac0.web.vo.AuthzUserVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.utils.RandomString;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

/**
 * 权限管理：角色管理
 */
@Api(tags = "权限管理：角色管理（Ok）")
@Validated
@RestController
@RequestMapping(value = "/authz/role/")
public class AuthzRoleController extends BaseApiController {
	
	protected RandomString randomString = new RandomString(20);
	@Autowired
	private IAuthzRoleService authzRoleService;
	@Autowired
	private IAuthzUserService authzUserService;
	
	@ApiOperation(value = "可用角色信息列表（键值对）", notes = "获取当前所有可用的角色信息")
	@GetMapping("pairs")
    @RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> list() throws Exception {
		List<PairModel> roleList = getAuthzRoleService().getPairList();
		if(roleList == null) {
			return ApiRestResponse.empty(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(roleList);
	}
	
	@ApiOperation(value = "可用角色信息列表（对象属性）", notes = "查询全部可用角色信息")
	@GetMapping("roles")
	@RequiresPermissions("role:list")
	public ApiRestResponse<List<AuthzRoleVo>> roles(){
		List<AuthzRoleModel> roles = getAuthzRoleService().getRoles();
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel roleModel : roles) {
			retList.add(getBeanMapper().map(roleModel, AuthzRoleVo.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "角色信息筛选条件", dataType = "AuthzRolePaginationVo")
	})
	@PostMapping("list")
	@RequiresPermissions("role:list")
	public Result<AuthzRoleVo> list(@Valid @RequestBody AuthzRolePaginationVo paginationVo){
		
		AuthzRoleModel model = getBeanMapper().map(paginationVo, AuthzRoleModel.class);
		Page<AuthzRoleModel> pageResult = getAuthzRoleService().getPagedList(model);
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel roleModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(roleModel, AuthzRoleVo.class));
		}
		
		return new Result<AuthzRoleVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "增加角色信息", notes = "增加角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "roleVo", value = "角色信息", required = true, dataType = "AuthzRoleNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "新增角色-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("role:new")
	public ApiRestResponse<String> newRole(@Valid @RequestBody AuthzRoleNewVo roleVo) throws Exception {
		
		if(CollectionUtils.isEmpty(roleVo.getPerms())) {
			return fail("role.new.need-perms");
		}
		int total = getAuthzRoleService().getCountByName(roleVo.getName(), null);
		if(total > 0) {
			return fail("role.new.exists");
		}
		AuthzRoleModel model = getBeanMapper().map(roleVo, AuthzRoleModel.class);
		// 角色编码
		model.setKey(RandomStringUtils.random(10,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
		// 角色类型（1:原生|2:继承|3:复制|4:自定义）
		model.setType("4");
		int result = getAuthzRoleService().insert(model);
		if(result > 0) {
			return success("role.new.success", result);
		}
		return fail("role.new.fail");
	}
	
	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "roleVo", value = "角色信息", required = true, dataType = "AuthzRoleRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "修改角色-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("role:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzRoleRenewVo roleVo) throws Exception { 
		if(CollectionUtils.isEmpty(roleVo.getPerms())) {
			return fail("role.renew.need-perms");
		}
		int total = getAuthzRoleService().getCountByName(roleVo.getName(), roleVo.getId());
		if(total > 0) {
			return fail("role.renew.exists");
		}
		AuthzRoleModel model = getBeanMapper().map(roleVo, AuthzRoleModel.class);
		int result = getAuthzRoleService().update(model);
		if(result > 0) {
			return success("role.renew.success", result);
		}
		return fail("role.renew.fail");
	}
	
	@ApiOperation(value = "更新角色状态", notes = "更新角色状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "form", name = "id", required = true, value = "角色ID", dataType = "String"),
		@ApiImplicitParam(paramType = "form", name = "status", required = true, value = "角色状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "更新角色状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("role:status")
	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "角色状态错误") @RequestParam String status) throws Exception {
		int result = getAuthzRoleService().setStatus(id, status);
		if(result == 1) {
			return success("role.status.success", result);
		}
		return fail("role.status.fail", result);
	}
	
	@ApiOperation(value = "角色信息详情", notes = "根据角色ID查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "角色ID", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresPermissions("role:detail")
	public ApiRestResponse<AuthzRoleVo> detail(@RequestParam("id") String id) throws Exception {
		AuthzRoleModel model = getAuthzRoleService().getModel(id);
		if(model == null) {
			return ApiRestResponse.empty(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzRoleVo.class));
	}
	
	@ApiOperation(value = "删除角色信息", notes = "删除角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "删除角色-名称：${roleid}", opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("role:delete")
	public ApiRestResponse<String> delRole(@RequestParam("id") String id) throws Exception {
		int total = getAuthzRoleService().delete(id);
		if(total == 1) {
			return success("role.delete.success", total);
		}
		if (total == -1){
			return fail("role.user.not-empty");
		}
		return fail("role.delete.fail", total);
	}

	@ApiOperation(value = "批量删除角色信息", notes = "批量删除角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam( paramType = "body", name = "ids", required = true, value = "角色ID", dataType = "java.util.List<String>")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "批量删除角色信息", opt = BusinessType.DELETE)
	@PostMapping("deleteByIds")
	@RequiresPermissions("role:delete")
	public ApiRestResponse<String> deleteByIds(@RequestBody List<String> ids) throws Exception {
		if (!CollectionUtils.isEmpty(ids)) {
			int total = getAuthzRoleService().batchDelete(ids);
			if(total > 0) {
				return success("role.delete.success", total);
			}
			if (total == -1){
				return fail("role.user.not-empty");
			}
			return fail("role.delete.fail", total);
		}
		return success("role.delete.success");
	}
	
	@ApiOperation(value = "角色已分配用户查询", notes = "分页查询角色已分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "已分配用户信息筛选条件", dataType = "AuthzRoleAllotUserPaginationVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色已分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("role:allocated")
	@ResponseBody
	public Result<AuthzUserVo> allocated(@Valid @RequestBody AuthzRoleAllotUserPaginationVo paginationVo){
		Page<AuthzUserModel> pageResult = getAuthzRoleService().getPagedAllocatedList(paginationVo);
		List<AuthzUserVo> retList = new ArrayList<AuthzUserVo>();
		for (AuthzUserModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserVo.class));
		}
		return new Result<AuthzUserVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "角色未分配用户查询", notes = "分页查询角色未分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "未分配用户信息筛选条件", dataType = "AuthzRoleAllotUserPaginationVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色未分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("role:unallocated")
	@ResponseBody
	public Result<AuthzUserVo> unallocated(@Valid @RequestBody AuthzRoleAllotUserPaginationVo paginationVo){
		Page<AuthzUserModel> pageResult = getAuthzRoleService().getPagedUnAllocatedList(paginationVo);
		List<AuthzUserVo> retList = new ArrayList<AuthzUserVo>();
		for (AuthzUserModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserVo.class));
		}
		return new Result<AuthzUserVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "给指定角色分配用户", notes = "给指定角色分配用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "角色分配的用户信息", dataType = "AuthzRoleAllotUserVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "给指定角色分配用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("role:allot")
	@ResponseBody
	public ApiRestResponse<String> allot(@Valid @RequestBody AuthzRoleAllotUserVo allotVo) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotVo, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doAllot(model);
		if(total > 0) {
			return success("role.allot.success", total); 
		}
		return fail("role.allot.fail", total);
	}
	
	@ApiOperation(value = "取消已分配给指定角色的用户", notes = "取消已分配给指定角色的用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "角色取消分配的用户信息", dataType = "AuthzRoleAllotUserVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "取消已分配给指定角色的用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("role:unallot")
	@ResponseBody
	public ApiRestResponse<String> unallot(@Valid @RequestBody AuthzRoleAllotUserVo allotVo) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotVo, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doUnAllot(model);
		if(total > 0) {
			return success("role.unallot.success", total); 
		}
		return fail("role.unallot.fail", total);
	}

	@ApiOperation(value = "角色人员-树形结构数据", notes = "角色与人员组成的树形结构数据")
	@GetMapping("utree")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzRoleUserVo>> userTree() throws Exception {
		
		// 所有角色信息
		List<AuthzRoleModel> roles = getAuthzRoleService().getRoles();
		List<AuthzUserModel> users = getAuthzUserService().getUserList();
		
		List<AuthzRoleUserVo> retList = new ArrayList<AuthzRoleUserVo>();
		for (AuthzRoleModel roleModel : roles) {
			AuthzRoleUserVo roleUserVo = getBeanMapper().map(roleModel, AuthzRoleUserVo.class);
			if(users != null) {
				List<AuthzUserVo> children = users.stream().filter(source -> {
					return StringUtils.equalsIgnoreCase(roleModel.getId(), source.getRoleId());
				}).map(source -> {
					return getBeanMapper().map(source, AuthzUserVo.class);
				}).collect(Collectors.toList());
				if(children != null) {
					roleUserVo.setChildren(children);
				}
			}
			retList.add(roleUserVo);
		}
		return ApiRestResponse.success(retList);
		
	}
	
	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

	public IAuthzUserService getAuthzUserService() {
		return authzUserService;
	}

}
