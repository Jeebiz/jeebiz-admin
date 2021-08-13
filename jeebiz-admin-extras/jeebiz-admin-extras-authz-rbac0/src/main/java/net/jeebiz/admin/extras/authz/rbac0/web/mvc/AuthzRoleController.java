/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.extras.authz.feature.setup.Constants;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserDetailModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleAllotUserVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleNewVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRolePaginationVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleRenewVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzRoleVo;
import net.jeebiz.admin.extras.authz.rbac0.web.vo.AuthzUserDetailVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

/**
 * 权限管理：角色管理
 */
@Api(tags = "权限管理：角色管理（Ok）")
@RestController
@RequestMapping(value = "/authz/role/")
public class AuthzRoleController extends BaseApiController {

	@Autowired
	protected IAuthzFeatureService authzFeatureService;
	@Autowired
	private IAuthzRoleService authzRoleService;//角色管理SERVICE
	
	@ApiOperation(value = "可用角色信息", notes = "查询全部可用角色信息")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzRoleVo.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询全部可用角色信息", opt = BusinessType.SELECT)
	@GetMapping("roles")
	@RequiresPermissions("role:list")
	@ResponseBody
	public Object roles(){
		List<AuthzRoleModel> roles = getAuthzRoleService().getRoles();
		List<AuthzRoleVo> retList = new ArrayList<AuthzRoleVo>();
		for (AuthzRoleModel roleModel : roles) {
			retList.add(getBeanMapper().map(roleModel, AuthzRoleVo.class));
		}
		return retList;
	}
	
	@ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "角色信息筛选条件", dataType = "AuthzRolePaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("role:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzRolePaginationVo paginationVo){
		
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
	@ResponseBody
	public Object newRole(@Valid @RequestBody AuthzRoleNewVo roleVo) throws Exception {
		
		if(CollectionUtils.isEmpty(roleVo.getPerms())) {
			return fail("role.new.need-perms");
		}
		int total = getAuthzRoleService().getCountByName(roleVo.getName());
		if(total > 0) {
			return fail("role.new.exists");
		}
		AuthzRoleModel model = getBeanMapper().map(roleVo, AuthzRoleModel.class);
		// 角色编码
		model.setKey(RandomStringUtils.random(20,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
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
	@ResponseBody
	public Object renewRole(@Valid @RequestBody AuthzRoleRenewVo roleVo) throws Exception { 
		if(CollectionUtils.isEmpty(roleVo.getPerms())) {
			return fail("role.renew.need-perms");
		}
		AuthzRoleModel model = getBeanMapper().map(roleVo, AuthzRoleModel.class);
		int total = getAuthzRoleService().getCount(model);
		if(total > 0) {
			return fail("role.new.exists");
		}
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
	@PostMapping("status")
	@RequiresPermissions("role:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzRoleService().setStatus(id, status);
		if(result == 1) {
			return success("role.status.success", result);
		}
		return fail("role.status.fail", result);
	}
	
	@ApiOperation(value = "角色信息详情", notes = "根据角色ID查询角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询角色", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("role:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		AuthzRoleModel model = getAuthzRoleService().getModel(id);
		if(model == null) {
			return fail("role.get.empty");
		}
		return getBeanMapper().map(model, AuthzRoleVo.class);
	}
	
	@ApiOperation(value = "删除角色信息", notes = "删除角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "删除角色-名称：${roleid}", opt = BusinessType.DELETE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("role:delete")
	@ResponseBody
	public Object delRole(@PathVariable("id") String id) throws Exception {
		int total = getAuthzRoleService().delete(id);
		if(total == 1) {
			return success("role.delete.success", total);
		}
		return fail("role.delete.fail", total);
	}
	
	@ApiOperation(value = "角色已分配用户查询", notes = "分页查询角色已分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "已分配用户信息筛选条件", dataType = "AuthzRolePaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色已分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allocated")
	@RequiresPermissions("role:allocated")
	@ResponseBody
	public Object allocated(@Valid @RequestBody AuthzRolePaginationVo paginationVo){
		
		AuthzRoleModel model = getBeanMapper().map(paginationVo, AuthzRoleModel.class);
		Page<AuthzUserDetailModel> pageResult = getAuthzRoleService().getPagedAllocatedList(model);
		List<AuthzUserDetailVo> retList = new ArrayList<AuthzUserDetailVo>();
		for (AuthzUserDetailModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserDetailVo.class));
		}
		return new Result<AuthzUserDetailVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "角色未分配用户查询", notes = "分页查询角色未分配用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "未分配用户信息筛选条件", dataType = "AuthzRolePaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色未分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallocated")
	@RequiresPermissions("role:unallocated")
	@ResponseBody
	public Object unallocated(@Valid @RequestBody AuthzRolePaginationVo paginationVo){
		
		AuthzRoleModel model = getBeanMapper().map(paginationVo, AuthzRoleModel.class);
		Page<AuthzUserDetailModel> pageResult = getAuthzRoleService().getPagedUnAllocatedList(model);
		List<AuthzUserDetailVo> retList = new ArrayList<AuthzUserDetailVo>();
		for (AuthzUserDetailModel detailModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(detailModel, AuthzUserDetailVo.class));
		}
		return new Result<AuthzUserDetailVo>(pageResult, retList);
	}
	
	@ApiOperation(value = "给指定角色分配用户", notes = "给指定角色分配用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "角色分配的用户信息", dataType = "AuthzRoleAllotUserVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "给指定角色分配用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("allot")
	@RequiresPermissions("role:allot")
	@ResponseBody
	public Object allot(@Valid @RequestBody AuthzRoleAllotUserVo allotVo) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotVo, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doAllot(model);
		return success("role.allot.success", total); 
	}
	
	@ApiOperation(value = "取消已分配给指定角色的用户", notes = "取消已分配给指定角色的用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "allotVo", value = "角色取消分配的用户信息", dataType = "AuthzRoleAllotUserVo")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "取消已分配给指定角色的用户，角色Id：${roleid}", opt = BusinessType.DELETE)
	@PostMapping("unallot")
	@RequiresPermissions("role:unallot")
	@ResponseBody
	public Object unallot(@Valid @RequestBody AuthzRoleAllotUserVo allotVo) throws Exception { 
		AuthzRoleAllotUserModel model = getBeanMapper().map(allotVo, AuthzRoleAllotUserModel.class);
		int total = getAuthzRoleService().doUnAllot(model);
		return success("role.unallot.success", total); 
	}

	@ApiOperation(value = "角色功能菜单查询", notes = "查询已分配给指定角色ID的功能菜单信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "roleId", required = false, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询已分配给指定角色ID的功能", opt = BusinessType.SELECT)
	@GetMapping("features/{roleId}")
	@RequiresAuthentication
	@ResponseBody
	public Object features(@PathVariable String roleId) throws Exception { 
		return ApiRestResponse.success(getAuthzRoleService().getFeatures(roleId));
	}
	
	@ApiOperation(value = "角色功能菜单-树形结构数据", notes = "根据角色ID查询角色拥有的功能菜单-树形结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "roleId", required = false, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询指定角色ID拥有的功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("features/tree/{roleId}")
	@RequiresAuthentication
	@ResponseBody
	public Object tree(@PathVariable String roleId){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(roleId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "角色功能菜单-树形结构数据", notes = "根据角色ID查询角色拥有的功能菜单-树形结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "roleId", required = true, value = "角色ID", dataType = "String"),
		@ApiImplicitParam( paramType = "path", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询指定角色ID拥有的功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("features/tree/{roleId}/{tag}")
	@RequiresAuthentication
	@ResponseBody
	public Object treeByTag(@PathVariable String roleId, @PathVariable String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(roleId);
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getTreeHandler(tag).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "角色功能菜单-扁平结构数据", notes = "根据角色ID查询角色拥有的功能菜单-扁平结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "roleId", required = true, value = "角色ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询指定角色ID拥有的功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@GetMapping("features/flat/{roleId}")
	@RequiresAuthentication
	@ResponseBody
	public Object flat(@RequestParam String roleId){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(roleId);
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler().handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "角色功能菜单-扁平结构数据", notes = "根据角色ID查询角色拥有的功能菜单-扁平结构数据")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "roleId", required = true, value = "角色ID", dataType = "String"),
		@ApiImplicitParam( paramType = "path", name = "tag", required = true, value = "响应数据处理实现对象注册名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ROLE, business = "查询指定角色ID拥有的功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@GetMapping("features/flat/{roleId}/{tag}")
	@RequiresAuthentication
	@ResponseBody
	public Object flatByTag(@PathVariable String roleId, @PathVariable String tag){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮：标记按钮选中状态
		List<AuthzFeatureOptModel> featureOptList = getAuthzRoleService().getFeatureOpts(roleId);
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureDataHandlerFactory.getFlatHandler(tag).handle(featureList, featureOptList));
	}
	
	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}

}
