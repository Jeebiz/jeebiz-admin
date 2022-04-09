/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.web.mvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
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
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserProfileEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserRoleEntity;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserProfileService;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.admin.authz.rbac0.setup.Constants;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserAllotRoleDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserNewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserPaginationDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserProfileDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserPwdResetDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserRenewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserResetDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 权限管理：用户管理
 */
@Api(tags = "权限管理：用户管理（Ok）")
@RestController
@RequestMapping(value = "/authz/user/")
@Slf4j
public class AuthzUserController extends BaseMapperController {

	@Autowired
	private IAuthzUserService authzUserService;
	@Autowired
	private IAuthzUserProfileService authzUserProfileService;
	@Autowired
	private IAuthzRoleService authzRoleService;

	@ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "AuthzUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("user:list")
	public Result<AuthzUserDTO> list(@Valid @RequestBody AuthzUserPaginationDTO paginationDTO){

		AuthzUserEntity model = getBeanMapper().map(paginationDTO, AuthzUserEntity.class);
		Page<AuthzUserEntity> pageResult = getAuthzUserService().getPagedList(model);
		List<AuthzUserDTO> retList = new ArrayList<AuthzUserDTO>();
		for (AuthzUserEntity userModel : pageResult.getRecords()) {
			AuthzUserDTO userDTO = getBeanMapper().map(userModel, AuthzUserDTO.class);
			userDTO.setTime24(userModel.getCreateTime());
			retList.add(userDTO);
		}

		return new Result<AuthzUserDTO>(pageResult, retList);
	}

	@ApiOperation(value = "指定用户详情", notes = "根据用户id查询用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "用户id", dataType = "String")
	})
	@GetMapping("detailById")
	@RequiresPermissions("user:detail")
	public ApiRestResponse<AuthzUserDTO> detailById(@RequestParam String id) throws Exception {
		AuthzUserEntity model = getAuthzUserService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzUserDTO.class));
	}

	@ApiOperation(value = "登录用户详情", notes = "根据认证信息中的用户id查询用户详情")
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<AuthzUserDTO> detail() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserEntity model = getAuthzUserService().getById(principal.getUserid());
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		AuthzUserDTO userDTO = getBeanMapper().map(model, AuthzUserDTO.class);
		AuthzUserProfileEntity profileModel = getAuthzUserProfileService().getProfile(principal.getUserid());
		if(Objects.nonNull(profileModel)) {
			userDTO.setProfile(getBeanMapper().map(profileModel, AuthzUserProfileDTO.class));
		}
		return ApiRestResponse.success(userDTO);
	}

	@ApiOperation(value = "登录用户信息", notes = "根据认证信息中的用户id查询用户详情")
	@GetMapping("profile")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<AuthzUserProfileDTO> profile() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		AuthzUserProfileEntity model = getAuthzUserProfileService().getProfile(principal.getUserid());
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzUserProfileDTO.class));
	}

	@ApiOperation(value = "增加用户信息", notes = "增加用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "AuthzUserNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
    @RequiresPermissions("user:new")
	public ApiRestResponse<String> newUser(@Valid @RequestBody AuthzUserNewDTO userDTO, @ApiIgnore HttpServletRequest request) throws Exception {

		Long total = getAuthzUserService().getCountByName(userDTO.getUsername(), null);
		if(total > 0) {
			return fail("user.new.exists");
		}
		Long total2 = getAuthzUserService().getCountByCode(userDTO.getUcode(), null);
		if(total2 > 0) {
			return fail("user.new.ucode.exists");
		}

		AuthzUserEntity model = getBeanMapper().map(userDTO, AuthzUserEntity.class);

		String appId = request.getHeader(XHeaders.X_APP_ID);
		String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
		String appVersion = request.getHeader(XHeaders.X_APP_VERSION);

		log.info(XHeaders.X_APP_ID + "：{}", appId);
		log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
		log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);

		model.setAppId(appId);
		model.setAppChannel(appChannel);
		model.setAppVer(appVersion);

		boolean result = getAuthzUserService().save(model);
		if(result) {
			return success("user.new.success", result);
		}
		return fail("user.new.fail", result);
	}

	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "AuthzUserRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("user:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzUserRenewDTO userDTO) throws Exception {
		Long total2 = getAuthzUserService().getCountByCode(userDTO.getUcode(), userDTO.getId());
		if(total2 > 0) {
			return fail("user.new.ucode.exists");
		}
		AuthzUserEntity model = getBeanMapper().map(userDTO, AuthzUserEntity.class);
		int result = getAuthzUserService().update(model);
		if(result > 0) {
			return success("user.renew.success", result);
		}
		return fail("user.renew.fail", result);
	}

	@ApiOperation(value = "更新用户状态", notes = "更新用户状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "用户id", dataType = "String"),
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
		@ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid}", opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("user:delete")
	public ApiRestResponse<String> delUser(@RequestParam("id") String id) throws Exception {
		int total = getAuthzUserService().delete(id);
		if(total > 0) {
			return success("user.delete.success", total);
		}
		return fail("user.delete.fail", total);
	}

	@ApiOperation(value = "批量删除用户信息", notes = "批量删除用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "用户id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid} ", opt = BusinessType.DELETE)
	@GetMapping("deleteByIds")
	@RequiresPermissions("user:delete")
	public ApiRestResponse<String> delUsersByIds(@RequestParam String ids) throws Exception {
		// 执行用户id删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean total = getAuthzUserService().removeByIds(idList);
		if(total) {
			return success("user.delete.success", total);
		}
		return fail("user.delete.fail", total);
	}

	@ApiOperation(value = "分页查询用户已分配角色信息", notes = "分页查询用户已分配角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "已分配角色信息筛选条件", dataType = "AuthzUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户已分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("user:allocated")
	@ResponseBody
	public Result<AuthzRoleDTO> allocated(@Valid @RequestBody AuthzUserPaginationDTO paginationDTO){

		AuthzUserEntity model = getBeanMapper().map(paginationDTO, AuthzUserEntity.class);
		Page<AuthzRoleEntity> pageResult = getAuthzUserService().getPagedAllocatedList(model);
		List<AuthzRoleDTO> retList = new ArrayList<AuthzRoleDTO>();
		for (AuthzRoleEntity userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, AuthzRoleDTO.class));
		}
		return new Result<AuthzRoleDTO>(pageResult, retList);
	}

	@ApiOperation(value = "分页查询用户未分配角色信息", notes = "分页查询用户未分配角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "未分配角色信息筛选条件", dataType = "AuthzUserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户未分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("user:unallocated")
	@ResponseBody
	public Result<AuthzRoleDTO> unallocated(@Valid @RequestBody AuthzUserPaginationDTO paginationDTO){

		AuthzUserEntity model = getBeanMapper().map(paginationDTO, AuthzUserEntity.class);
		Page<AuthzRoleEntity> pageResult = getAuthzUserService().getPagedUnAllocatedList(model);
		List<AuthzRoleDTO> retList = new ArrayList<AuthzRoleDTO>();
		for (AuthzRoleEntity userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, AuthzRoleDTO.class));
		}
		return new Result<AuthzRoleDTO>(pageResult, retList);
	}

	@ApiOperation(value = "给指定用户分配角色", notes = "给指定用户分配角色")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户分配的角色信息", dataType = "AuthzUserAllotRoleDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "给指定用户分配角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("user:allot")
	@ResponseBody
	public ApiRestResponse<String> allot(@Valid @RequestBody AuthzUserAllotRoleDTO allotDTO) throws Exception {
		AuthzUserRoleEntity model = getBeanMapper().map(allotDTO, AuthzUserRoleEntity.class);
		int total = getAuthzUserService().doAllot(model);
		return success("user.allot.success", total);
	}

	@ApiOperation(value = "取消已分配给指定用户的角色", notes = "取消已分配给指定用户的角色")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户取消分配的角色信息", dataType = "AuthzUserAllotRoleDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "取消已分配给指定用户的角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("user:unallot")
	@ResponseBody
	public ApiRestResponse<String> unallot(@Valid @RequestBody AuthzUserAllotRoleDTO allotDTO) throws Exception {
		AuthzUserRoleEntity model = getBeanMapper().map(allotDTO, AuthzUserRoleEntity.class);
		int total = getAuthzUserService().doUnAllot(model);
		return success("user.unallot.success", total);
	}

	@ApiOperation(value = "重置信息：当前登录用户", notes = "重置当前登录用户信息")
	@PostMapping("reset/info")
	@RequiresAuthentication
	public ApiRestResponse<String> resetInfo(@Valid @RequestBody AuthzUserResetDTO infoDTO  ) throws Exception {
		// 密码加密
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getAuthzUserProfileService().resetInfo(principal.getUserid(), infoDTO);
		if(total > 0) {
			return success("user.reset.info.success", total);
		}
		return fail("user.reset.info.fail", total);
	}

	@ApiOperation(value = "重置密码：当前登录用户", notes = "重置当前登录用户密码")
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置密码", opt = BusinessType.UPDATE)
	@PostMapping("reset/pwd")
	@RequiresAuthentication
	public ApiRestResponse<String> resetPwdSelf(@Valid @RequestBody AuthzUserPwdResetDTO pwdDTO ) throws Exception {
		// 密码加密
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getAuthzUserService().resetPwd(principal.getUserid(), pwdDTO.getOldPassword(), pwdDTO.getPassword());
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
	public ApiRestResponse<List<AuthzRoleDTO>> roles(){
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 	用户角色信息集合
		List<AuthzRoleEntity> roles = getAuthzUserService().getRoles(principal.getUserid());
		List<AuthzRoleDTO> retList = new ArrayList<AuthzRoleDTO>();
		for (AuthzRoleEntity roleModel : roles) {
			retList.add(getBeanMapper().map(roleModel, AuthzRoleDTO.class));
		}
		return ApiRestResponse.success(retList);
	}

	public IAuthzUserService getAuthzUserService() {
		return authzUserService;
	}

	public IAuthzUserProfileService getAuthzUserProfileService() {
		return authzUserProfileService;
	}

	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

}
