/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.web.mvc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.authz.rbac0.service.IRoleService;
import io.hiwepy.admin.authz.rbac0.service.IUserAccountService;
import io.hiwepy.admin.authz.rbac0.setup.Constants;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleAllotUserDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleNewDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RolePaginationDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleRenewDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleStatusRenewDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleUserDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.UserDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

/**
 * 权限管理：角色管理
 */
@Api(tags = "权限管理：角色管理（Ok）")
@Validated
@RestController
@RequestMapping(value = "/authz/role/")
public class RoleController extends BaseApiController {

	protected RandomString randomString = new RandomString(20);

	@Autowired
	private IRoleService authzRoleService;
	@Autowired
	private IUserAccountService authzUserService;

	@ApiOperation(value = "可用角色信息列表（键值对）", notes = "获取当前所有可用的角色信息")
	@GetMapping("pairs")
    @RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> list() throws Exception {
		List<PairModel> roleList = getRoleService().getPairList();
		if(roleList == null) {
			return ApiRestResponse.fail(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(roleList);
	}

	@ApiOperation(value = "可用角色信息列表（对象属性）", notes = "查询全部可用角色信息")
	@GetMapping("roles")
	@RequiresPermissions("role:list")
	public ApiRestResponse<List<RoleDTO>> roles(){
		List<RoleEntity> roles = getRoleService().getRoles();
		List<RoleDTO> retList = new ArrayList<RoleDTO>();
		for (RoleEntity roleModel : roles) {
			RoleDTO roleDTO = getBeanMapper().map(roleModel, RoleDTO.class);
			roleDTO.setTime24(roleModel.getCreateTime());
			retList.add(roleDTO);
		}
		return ApiRestResponse.success(retList);
	}

	@ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "角色信息筛选条件", dataType = "RolePaginationDTO")
	})
	@PostMapping("list")
	//@RequiresPermissions("role:list")
	public Result<RoleDTO> list(@Valid @RequestBody RolePaginationDTO paginationDTO){

		RoleEntity model = getBeanMapper().map(paginationDTO, RoleEntity.class);
		Page<RoleEntity> pageResult = getRoleService().getPagedList(model);
		List<RoleDTO> retList = new ArrayList<RoleDTO>();
		for (RoleEntity roleModel : pageResult.getRecords()) {
			RoleDTO roleDTO = getBeanMapper().map(roleModel, RoleDTO.class);
			roleDTO.setTime24(roleModel.getCreateTime());
			retList.add(roleDTO);
		}

		return new Result<RoleDTO>(pageResult, retList);
	}

	@ApiOperation(value = "增加角色信息", notes = "增加角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "roleDTO", value = "角色信息", required = true, dataType = "RoleNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "新增角色-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("role:new")
	public ApiRestResponse<String> newRole(@Valid @RequestBody RoleNewDTO roleDTO) throws Exception {
		/*
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.new.need-perms");
		}*/
		Long total = getRoleService().getCountByName(roleDTO.getName(), null);
		if(total > 0) {
			return fail("role.new.exists");
		}
		RoleEntity model = getBeanMapper().map(roleDTO, RoleEntity.class);
		// 角色编码
		model.setKey(RandomStringUtils.random(10,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
		// 角色类型（1:原生|2:继承|3:复制|4:自定义）
		model.setType("4");
		boolean result = getRoleService().save(model);
		if(result) {
			return success("role.new.success", result);
		}
		return fail("role.new.fail");
	}

	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "roleDTO", value = "角色信息", required = true, dataType = "RoleRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "修改角色-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("role:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody RoleRenewDTO roleDTO) throws Exception {
		/*
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.renew.need-perms");
		}*/
		Long total = getRoleService().getCountByName(roleDTO.getName(), roleDTO.getId());
		if(total > 0) {
			return fail("role.renew.exists");
		}
		RoleEntity model = getBeanMapper().map(roleDTO, RoleEntity.class);
		int result = getRoleService().update(model);
		if(result > 0) {
			return success("role.renew.success", result);
		}
		return fail("role.renew.fail");
	}

	@ApiOperation(value = "更新角色状态", notes = "更新角色状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "renewDTO", value = "角色信息", required = true, dataType = "RoleStatusRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "更新角色状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("role:status")
	public ApiRestResponse<String> status(@Valid @RequestBody RoleStatusRenewDTO renewDTO) throws Exception {
		int result = getRoleService().setStatus(renewDTO.getId(), renewDTO.getStatus());
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
	public ApiRestResponse<RoleDTO> detail(@RequestParam("id") String id) throws Exception {
		RoleEntity model = getRoleService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("role.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, RoleDTO.class));
	}

	@ApiOperation(value = "删除角色信息", notes = "删除角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "角色id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "删除角色-名称：${roleid}", opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("role:delete")
	public ApiRestResponse<String> delRole(@RequestParam("id") String id) throws Exception {
		int total = getRoleService().delete(id);
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
			boolean total = getRoleService().removeByIds(ids);
			if(total) {
				return success("role.delete.success", total);
			}
			return fail("role.delete.fail", total);
		}
		return success("role.delete.success");
	}

	@ApiOperation(value = "角色已分配用户查询", notes = "分页查询角色已分配用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "已分配用户信息筛选条件", dataType = "RoleAllotUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色已分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("role:allocated")
	@ResponseBody
	public Result<UserDTO> allocated(@Valid @RequestBody RoleAllotUserPaginationDTO paginationDTO){
		Page<UserAccountEntity> pageResult = getRoleService().getPagedAllocatedList(paginationDTO);
		List<UserDTO> retList = new ArrayList<UserDTO>();
		for (UserAccountEntity detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, UserDTO.class));
		}
		return new Result<UserDTO>(pageResult, retList);
	}

	@ApiOperation(value = "角色未分配用户查询", notes = "分页查询角色未分配用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "未分配用户信息筛选条件", dataType = "RoleAllotUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色未分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("role:unallocated")
	@ResponseBody
	public Result<UserDTO> unallocated(@Valid @RequestBody RoleAllotUserPaginationDTO paginationDTO){
		Page<UserAccountEntity> pageResult = getRoleService().getPagedUnAllocatedList(paginationDTO);
		List<UserDTO> retList = new ArrayList<UserDTO>();
		for (UserAccountEntity detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, UserDTO.class));
		}
		return new Result<UserDTO>(pageResult, retList);
	}

	@ApiOperation(value = "给指定角色分配用户", notes = "给指定角色分配用户")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色分配的用户信息", dataType = "RoleAllotUserDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "给指定角色分配用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("role:allot")
	@ResponseBody
	public ApiRestResponse<String> allot(@Valid @RequestBody RoleAllotUserDTO allotDTO) throws Exception {
		int total = getRoleService().doAllot(allotDTO);
		if(total > 0) {
			return success("role.allot.success", total);
		}
		return fail("role.allot.fail", total);
	}

	@ApiOperation(value = "取消已分配给指定角色的用户", notes = "取消已分配给指定角色的用户")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色取消分配的用户信息", dataType = "RoleAllotUserDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "取消已分配给指定角色的用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("role:unallot")
	@ResponseBody
	public ApiRestResponse<String> unallot(@Valid @RequestBody RoleAllotUserDTO allotDTO) throws Exception {
		int total = getRoleService().doUnAllot(allotDTO);
		if(total > 0) {
			return success("role.unallot.success", total);
		}
		return fail("role.unallot.fail", total);
	}

	@ApiOperation(value = "角色人员-树形结构数据", notes = "角色与人员组成的树形结构数据")
	@GetMapping("utree")
	@RequiresAuthentication
	public ApiRestResponse<List<RoleUserDTO>> userTree() throws Exception {

		// 所有角色信息
		List<RoleEntity> roles = getRoleService().getRoles();
		List<UserAccountEntity> users = getUserService().getUserList();

		List<RoleUserDTO> retList = new ArrayList<RoleUserDTO>();
		for (RoleEntity roleModel : roles) {
			RoleUserDTO roleUserDTO = getBeanMapper().map(roleModel, RoleUserDTO.class);
			if(users != null) {
				List<UserDTO> children = users.stream().filter(source -> {
					return StringUtils.equalsIgnoreCase(roleModel.getId(), source.getRoleId());
				}).map(source -> {
					return getBeanMapper().map(source, UserDTO.class);
				}).collect(Collectors.toList());
				if(children != null) {
					roleUserDTO.setChildren(children);
				}
			}
			retList.add(roleUserDTO);
		}
		return ApiRestResponse.success(retList);

	}

	public IRoleService getRoleService() {
		return authzRoleService;
	}

	public IUserAccountService getUserService() {
		return authzUserService;
	}

}
