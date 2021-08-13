/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.apache.shiro.spring.boot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzDepartmentService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "部门管理：部门信息维护")
@RestController
@RequestMapping(value = "/authz/dept/")
public class AuthzDepartmentController1 extends BaseApiController {

	@Autowired
	private IAuthzDepartmentService authzDepartmentService;

	@ApiOperation(value = "根据分组分页查询部门信息", notes = "根据分组分页查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzDepartmentPaginationVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "根据分组分页查询部门信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-dept:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzDepartmentPaginationVo paginationVo) throws Exception {
		
		AuthzDepartmentModel model = getBeanMapper().map(paginationVo, AuthzDepartmentModel.class);
		
		Page<AuthzDepartmentModel> pageResult = getAuthzDepartmentService().getPagedList(model);
		List<AuthzDepartmentVo> retList = Lists.newArrayList();
		for (AuthzDepartmentModel departmentModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(departmentModel, AuthzDepartmentVo.class));
		}
		
		return new Result<AuthzDepartmentVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "根据分组查询部门信息", notes = "根据分组查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "group", value = "部门信息分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "根据分组查询部门信息", opt = BusinessType.SELECT)
	@PostMapping("pairs")
	@RequiresPermissions("authz-dept:list")
	@ResponseBody
	public Object pairs(@RequestParam String group) throws Exception {
		return getAuthzDepartmentService().getPairValues(group);
	}
	
	@ApiOperation(value = "创建部门信息", notes = "增加一个新的部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "deptVo", value = "部门信息传输对象", required = true, dataType = "AuthzDepartmentNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "创建部门信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-dept:new")
	@ResponseBody
	public Object newDept(@Valid @RequestBody AuthzDepartmentNewVo deptVo) throws Exception {
		AuthzDepartmentModel model = getBeanMapper().map(deptVo, AuthzDepartmentModel.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzDepartmentService().insert(model);
		if(result > 0) {
			return success("authz.dept.new.success", result);
		}
		return fail("authz.dept.new.fail", result);
	}
	
	@ApiOperation(value = "更新部门信息", notes = "更新部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "deptVo", value = "部门信息", required = true, dataType = "AuthzDepartmentRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "更新部门信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-dept:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzDepartmentRenewVo deptVo) throws Exception {
		AuthzDepartmentModel model = getBeanMapper().map(deptVo, AuthzDepartmentModel.class);
		int result = getAuthzDepartmentService().update(model);
		if(result == 1) {
			return success("authz.dept.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.renew.fail", result);
	}
	
	@ApiOperation(value = "更新部门信息状态", notes = "更新部门信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "部门信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "部门信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "更新部门信息状态", opt = BusinessType.UPDATE)
	@PostMapping(value = "status")
	@RequiresPermissions(value = {"authz-dept:enable", "authz-dept:disable"}, logical = Logical.OR)
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzDepartmentService().setStatus(id, status);
		if(result == 1) {
			return success("authz.dept.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.status.fail", result);
	}
	
	@ApiOperation(value = "删除部门信息", notes = "删除部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "部门信息ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "删除部门信息", opt = BusinessType.UPDATE)
	@PostMapping("delete")
	@RequiresPermissions("authz-dept:delete")
	@ResponseBody
	public Object delete(@RequestParam String ids) throws Exception {
		// 执行部门信息删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int result = getAuthzDepartmentService().batchDelete(idList);
		if(result > 0) {
			return success("authz.dept.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.delete.fail", result);
	}
	
	@ApiOperation(value = "查询部门信息", notes = "根据ID查询部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "部门信息ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "查询部门信息", opt = BusinessType.SELECT)
	@PostMapping("detail/{id}")
	@RequiresPermissions(value = {"authz-dept:list" ,"authz-dept:detail" }, logical = Logical.OR)
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		
		AuthzDepartmentModel model = getAuthzDepartmentService().getModel(id);
		if( model == null) {
			
		}
		return getBeanMapper().map(model, AuthzDepartmentVo.class);
		
	}

	public IAuthzDepartmentService getAuthzDepartmentService() {
		return authzDepartmentService;
	}
	
}
