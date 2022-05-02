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

import net.jeebiz.admin.authz.rbac0.dao.entities.RoleEntity;
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
import net.jeebiz.admin.authz.rbac0.dao.entities.UserAccountEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.UserProfileEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.UserRoleEntity;
import net.jeebiz.admin.authz.rbac0.service.IRoleService;
import net.jeebiz.admin.authz.rbac0.service.IUserProfileService;
import net.jeebiz.admin.authz.rbac0.service.IUserAccountService;
import net.jeebiz.admin.authz.rbac0.setup.Constants;
import net.jeebiz.admin.authz.rbac0.web.dto.RoleDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserAllotRoleDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserNewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserPaginationDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserProfileDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserPwdResetDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserRenewDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.UserResetDTO;
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
public class UserController extends BaseMapperController {

	@Autowired
	private IUserAccountService authzUserService;
	@Autowired
	private IUserProfileService authzUserProfileService;
	@Autowired
	private IRoleService authzRoleService;

	@ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "UserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("user:list")
	public Result<UserDTO> list(@Valid @RequestBody UserPaginationDTO paginationDTO){

		UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
		Page<UserAccountEntity> pageResult = getUserService().getPagedList(model);
		List<UserDTO> retList = new ArrayList<UserDTO>();
		for (UserAccountEntity userModel : pageResult.getRecords()) {
			UserDTO userDTO = getBeanMapper().map(userModel, UserDTO.class);
			userDTO.setTime24(userModel.getCreateTime());
			retList.add(userDTO);
		}

		return new Result<UserDTO>(pageResult, retList);
	}

	@ApiOperation(value = "指定用户详情", notes = "根据用户id查询用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "用户id", dataType = "String")
	})
	@GetMapping("detailById")
	@RequiresPermissions("user:detail")
	public ApiRestResponse<UserDTO> detailById(@RequestParam String id) throws Exception {
		UserAccountEntity model = getUserService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, UserDTO.class));
	}

	@ApiOperation(value = "登录用户详情", notes = "根据认证信息中的用户id查询用户详情")
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<UserDTO> detail() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		UserAccountEntity model = getUserService().getById(principal.getUserid());
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		UserDTO userDTO = getBeanMapper().map(model, UserDTO.class);
		UserProfileEntity profileModel = getUserProfileService().getById(principal.getUserid());
		if(Objects.nonNull(profileModel)) {
			userDTO.setProfile(getBeanMapper().map(profileModel, UserProfileDTO.class));
		}
		return ApiRestResponse.success(userDTO);
	}

	@ApiOperation(value = "登录用户信息", notes = "根据认证信息中的用户id查询用户详情")
	@GetMapping("profile")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<UserProfileDTO> profile() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		UserProfileEntity model = getUserProfileService().getById(principal.getUserid());
		if(model == null) {
			return ApiRestResponse.fail(getMessage("user.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, UserProfileDTO.class));
	}

	@ApiOperation(value = "增加用户信息", notes = "增加用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "UserNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
    @RequiresPermissions("user:new")
	public ApiRestResponse<String> newUser(@Valid @RequestBody UserNewDTO userDTO, @ApiIgnore HttpServletRequest request) throws Exception {

		Long total = getUserService().getCountByName(userDTO.getAccount(), null);
		if(total > 0) {
			return fail("user.new.exists");
		}
		Long total2 = getUserService().getCountByCode(userDTO.getUcode(), null);
		if(total2 > 0) {
			return fail("user.new.ucode.exists");
		}

		UserAccountEntity model = getBeanMapper().map(userDTO, UserAccountEntity.class);

		String appId = request.getHeader(XHeaders.X_APP_ID);
		String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
		String appVersion = request.getHeader(XHeaders.X_APP_VERSION);

		log.info(XHeaders.X_APP_ID + "：{}", appId);
		log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
		log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);

		model.setAppId(appId);
		model.setAppChannel(appChannel);
		model.setAppVer(appVersion);

		boolean result = getUserService().save(model);
		if(result) {
			return success("user.new.success", result);
		}
		return fail("user.new.fail", result);
	}

	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "UserRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("user:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody UserRenewDTO userDTO) throws Exception {
		Long total2 = getUserService().getCountByCode(userDTO.getUcode(), userDTO.getId());
		if(total2 > 0) {
			return fail("user.new.ucode.exists");
		}
		UserAccountEntity model = getBeanMapper().map(userDTO, UserAccountEntity.class);
		int result = getUserService().update(model);
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
		int result = getUserService().setStatus(id, status);
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
		int total = getUserService().delete(id);
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
		boolean total = getUserService().removeByIds(idList);
		if(total) {
			return success("user.delete.success", total);
		}
		return fail("user.delete.fail", total);
	}

	@ApiOperation(value = "分页查询用户已分配角色信息", notes = "分页查询用户已分配角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "已分配角色信息筛选条件", dataType = "UserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户已分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("user:allocated")
	@ResponseBody
	public Result<RoleDTO> allocated(@Valid @RequestBody UserPaginationDTO paginationDTO){

		UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
		Page<RoleEntity> pageResult = getUserService().getPagedAllocatedList(model);
		List<RoleDTO> retList = new ArrayList<RoleDTO>();
		for (RoleEntity userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, RoleDTO.class));
		}
		return new Result<RoleDTO>(pageResult, retList);
	}

	@ApiOperation(value = "分页查询用户未分配角色信息", notes = "分页查询用户未分配角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "未分配角色信息筛选条件", dataType = "UserPaginationDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "分页查询用户未分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("user:unallocated")
	@ResponseBody
	public Result<RoleDTO> unallocated(@Valid @RequestBody UserPaginationDTO paginationDTO){

		UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
		Page<RoleEntity> pageResult = getUserService().getPagedUnAllocatedList(model);
		List<RoleDTO> retList = new ArrayList<RoleDTO>();
		for (RoleEntity userModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(userModel, RoleDTO.class));
		}
		return new Result<RoleDTO>(pageResult, retList);
	}

	@ApiOperation(value = "给指定用户分配角色", notes = "给指定用户分配角色")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户分配的角色信息", dataType = "UserAllotRoleDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "给指定用户分配角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("user:allot")
	@ResponseBody
	public ApiRestResponse<String> allot(@Valid @RequestBody UserAllotRoleDTO allotDTO) throws Exception {
		UserRoleEntity model = getBeanMapper().map(allotDTO, UserRoleEntity.class);
		int total = getUserService().doAllot(model);
		return success("user.allot.success", total);
	}

	@ApiOperation(value = "取消已分配给指定用户的角色", notes = "取消已分配给指定用户的角色")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户取消分配的角色信息", dataType = "UserAllotRoleDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_USER, business = "取消已分配给指定用户的角色，用户Id：${userid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("user:unallot")
	@ResponseBody
	public ApiRestResponse<String> unallot(@Valid @RequestBody UserAllotRoleDTO allotDTO) throws Exception {
		UserRoleEntity model = getBeanMapper().map(allotDTO, UserRoleEntity.class);
		int total = getUserService().doUnAllot(model);
		return success("user.unallot.success", total);
	}

	@ApiOperation(value = "重置信息：当前登录用户", notes = "重置当前登录用户信息")
	@PostMapping("reset/info")
	@RequiresAuthentication
	public ApiRestResponse<String> resetInfo(@Valid @RequestBody UserResetDTO infoDTO  ) throws Exception {
		// 密码加密
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getUserProfileService().resetInfo(principal.getUserid(), infoDTO);
		if(total > 0) {
			return success("user.reset.info.success", total);
		}
		return fail("user.reset.info.fail", total);
	}

	@ApiOperation(value = "重置密码：当前登录用户", notes = "重置当前登录用户密码")
	@BusinessLog(module = Constants.AUTHZ_USER, business = "设置密码", opt = BusinessType.UPDATE)
	@PostMapping("reset/pwd")
	@RequiresAuthentication
	public ApiRestResponse<String> resetPwdSelf(@Valid @RequestBody UserPwdResetDTO pwdDTO ) throws Exception {
		// 密码加密
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		int total = getUserService().resetPwd(principal.getUserid(), pwdDTO.getOldPassword(), pwdDTO.getPassword());
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
		return ApiRestResponse.success(getUserService().getPermissions(principal.getUserid()));
	}

	@ApiOperation(value = "角色信息列表：当前登录用户", notes = "查询当前用户全部可用角色信息")
	@GetMapping("roles")
	@RequiresAuthentication
	public ApiRestResponse<List<RoleDTO>> roles(){
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 	用户角色信息集合
		List<RoleEntity> roles = getUserService().getRoles(principal.getUserid());
		List<RoleDTO> retList = new ArrayList<RoleDTO>();
		for (RoleEntity roleModel : roles) {
			retList.add(getBeanMapper().map(roleModel, RoleDTO.class));
		}
		return ApiRestResponse.success(retList);
	}

	public IUserAccountService getUserService() {
		return authzUserService;
	}

	public IUserProfileService getUserProfileService() {
		return authzUserProfileService;
	}

	public IRoleService getRoleService() {
		return authzRoleService;
	}

}
