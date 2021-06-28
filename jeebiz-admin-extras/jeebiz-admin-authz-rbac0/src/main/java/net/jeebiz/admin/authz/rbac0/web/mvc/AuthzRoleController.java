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

import hitool.core.lang3.RandomString;
import hitool.core.lang3.time.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.admin.authz.rbac0.setup.Constants;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleNewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRolePaginationDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleRenewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleStatusRenewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleUserDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
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
			return ApiRestResponse.fail(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(roleList);
	}
	
	@ApiOperation(value = "可用角色信息列表（对象属性）", notes = "查询全部可用角色信息")
	@GetMapping("roles")
	@RequiresPermissions("role:list")
	public ApiRestResponse<List<AuthzRoleDTO>> roles(){
		List<AuthzRoleModel> roles = getAuthzRoleService().getRoles();
		List<AuthzRoleDTO> retList = new ArrayList<AuthzRoleDTO>();
		for (AuthzRoleModel roleModel : roles) {
			AuthzRoleDTO roleDTO = getBeanMapper().map(roleModel, AuthzRoleDTO.class);
			roleDTO.setTime24(DateUtils.formatDateTime(roleModel.getCreateTime()));
			retList.add(roleDTO);
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "角色信息筛选条件", dataType = "AuthzRolePaginationDTO")
	})
	@PostMapping("list")
	//@RequiresPermissions("role:list")
	public Result<AuthzRoleDTO> list(@Valid @RequestBody AuthzRolePaginationDTO paginationDTO){
		
		AuthzRoleModel model = getBeanMapper().map(paginationDTO, AuthzRoleModel.class);
		Page<AuthzRoleModel> pageResult = getAuthzRoleService().getPagedList(model);
		List<AuthzRoleDTO> retList = new ArrayList<AuthzRoleDTO>();
		for (AuthzRoleModel roleModel : pageResult.getRecords()) {
			AuthzRoleDTO roleDTO = getBeanMapper().map(roleModel, AuthzRoleDTO.class);
			roleDTO.setTime24(DateUtils.formatDateTime(roleModel.getCreateTime()));
			retList.add(roleDTO);
		}
		
		return new Result<AuthzRoleDTO>(pageResult, retList);
	}
	
	@ApiOperation(value = "增加角色信息", notes = "增加角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "roleDTO", value = "角色信息", required = true, dataType = "AuthzRoleNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "新增角色-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("role:new")
	public ApiRestResponse<String> newRole(@Valid @RequestBody AuthzRoleNewDTO roleDTO) throws Exception {
		
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.new.need-perms");
		}
		int total = getAuthzRoleService().getCountByName(roleDTO.getName(), null);
		if(total > 0) {
			return fail("role.new.exists");
		}
		AuthzRoleModel model = getBeanMapper().map(roleDTO, AuthzRoleModel.class);
		// 角色编码
		model.setKey(RandomStringUtils.random(10,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
		// 角色类型（1:原生|2:继承|3:复制|4:自定义）
		model.setType("4");
		boolean result = getAuthzRoleService().save(model);
		if(result) {
			return success("role.new.success", result);
		}
		return fail("role.new.fail");
	}
	
	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "roleDTO", value = "角色信息", required = true, dataType = "AuthzRoleRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "修改角色-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("role:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzRoleRenewDTO roleDTO) throws Exception { 
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.renew.need-perms");
		}
		int total = getAuthzRoleService().getCountByName(roleDTO.getName(), roleDTO.getId());
		if(total > 0) {
			return fail("role.renew.exists");
		}
		AuthzRoleModel model = getBeanMapper().map(roleDTO, AuthzRoleModel.class);
		int result = getAuthzRoleService().update(model);
		if(result > 0) {
			return success("role.renew.success", result);
		}
		return fail("role.renew.fail");
	}
	
	@ApiOperation(value = "更新角色状态", notes = "更新角色状态")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewDTO", value = "角色信息", required = true, dataType = "AuthzRoleStatusRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "更新角色状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("role:status")
	public ApiRestResponse<String> status(@Valid @RequestBody AuthzRoleStatusRenewDTO renewDTO) throws Exception {
		int result = getAuthzRoleService().setStatus(renewDTO.getId(), renewDTO.getStatus());
		if(result == 1) {
			return success("role.status.success", result);
		}
		return fail("role.status.fail", result);
	}
	
	@ApiOperation(value = "角色信息详情", notes = "根据角色id查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "角色id", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresPermissions("role:detail")
	public ApiRestResponse<AuthzRoleDTO> detail(@RequestParam("id") String id) throws Exception {
		AuthzRoleModel model = getAuthzRoleService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzRoleDTO.class));
	}
	
	@ApiOperation(value = "删除角色信息", notes = "删除角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "角色id", dataType = "String")
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
			@ApiImplicitParam( paramType = "body", name = "ids", required = true, value = "角色id", dataType = "java.util.List<String>")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "批量删除角色信息", opt = BusinessType.DELETE)
	@PostMapping("deleteByIds")
	@RequiresPermissions("role:delete")
	public ApiRestResponse<String> deleteByIds(@RequestBody List<String> ids) throws Exception {
		if (!CollectionUtils.isEmpty(ids)) {
			boolean total = getAuthzRoleService().removeByIds(ids);
			if(total) {
				return success("role.delete.success", total);
			}
			return fail("role.delete.fail", total);
		}
		return success("role.delete.success");
	}
	
	@ApiOperation(value = "角色已分配用户查询", notes = "分页查询角色已分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "已分配用户信息筛选条件", dataType = "AuthzRoleAllotUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色已分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("role:allocated")
	@ResponseBody
	public Result<AuthzUserDTO> allocated(@Valid @RequestBody AuthzRoleAllotUserPaginationDTO paginationDTO){
		Page<AuthzUserModel> pageResult = getAuthzRoleService().getPagedAllocatedList(paginationDTO);
		List<AuthzUserDTO> retList = new ArrayList<AuthzUserDTO>();
		for (AuthzUserModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserDTO.class));
		}
		return new Result<AuthzUserDTO>(pageResult, retList);
	}
	
	@ApiOperation(value = "角色未分配用户查询", notes = "分页查询角色未分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "未分配用户信息筛选条件", dataType = "AuthzRoleAllotUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色未分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("role:unallocated")
	@ResponseBody
	public Result<AuthzUserDTO> unallocated(@Valid @RequestBody AuthzRoleAllotUserPaginationDTO paginationDTO){
		Page<AuthzUserModel> pageResult = getAuthzRoleService().getPagedUnAllocatedList(paginationDTO);
		List<AuthzUserDTO> retList = new ArrayList<AuthzUserDTO>();
		for (AuthzUserModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserDTO.class));
		}
		return new Result<AuthzUserDTO>(pageResult, retList);
	}
	
	@ApiOperation(value = "给指定角色分配用户", notes = "给指定角色分配用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色分配的用户信息", dataType = "AuthzRoleAllotUserDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "给指定角色分配用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("role:allot")
	@ResponseBody
	public ApiRestResponse<String> allot(@Valid @RequestBody AuthzRoleAllotUserDTO allotDTO) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotDTO, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doAllot(model);
		if(total > 0) {
			return success("role.allot.success", total); 
		}
		return fail("role.allot.fail", total);
	}
	
	@ApiOperation(value = "取消已分配给指定角色的用户", notes = "取消已分配给指定角色的用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色取消分配的用户信息", dataType = "AuthzRoleAllotUserDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "取消已分配给指定角色的用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("role:unallot")
	@ResponseBody
	public ApiRestResponse<String> unallot(@Valid @RequestBody AuthzRoleAllotUserDTO allotDTO) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotDTO, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doUnAllot(model);
		if(total > 0) {
			return success("role.unallot.success", total); 
		}
		return fail("role.unallot.fail", total);
	}

	@ApiOperation(value = "角色人员-树形结构数据", notes = "角色与人员组成的树形结构数据")
	@GetMapping("utree")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzRoleUserDTO>> userTree() throws Exception {
		
		// 所有角色信息
		List<AuthzRoleModel> roles = getAuthzRoleService().getRoles();
		List<AuthzUserModel> users = getAuthzUserService().getUserList();
		
		List<AuthzRoleUserDTO> retList = new ArrayList<AuthzRoleUserDTO>();
		for (AuthzRoleModel roleModel : roles) {
			AuthzRoleUserDTO roleUserDTO = getBeanMapper().map(roleModel, AuthzRoleUserDTO.class);
			if(users != null) {
				List<AuthzUserDTO> children = users.stream().filter(source -> {
					return StringUtils.equalsIgnoreCase(roleModel.getId(), source.getRoleId());
				}).map(source -> {
					return getBeanMapper().map(source, AuthzUserDTO.class);
				}).collect(Collectors.toList());
				if(children != null) {
					roleUserDTO.setChildren(children);
				}
			}
			retList.add(roleUserDTO);
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
