package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzDepartmentService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzDepartmentVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "组织机构：部门信息维护")
@RestController
@RequestMapping(value = "/authz/org/dept/")
public class AuthzDepartmentController extends BaseApiController {

	@Autowired
	private IAuthzDepartmentService authzDepartmentService;
	
	@ApiOperation(value = "分页查询部门信息", notes = "分页查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzDepartmentPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "分页查询部门信息", opt = BusinessType.SELECT)
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
	
	@ApiOperation(value = "根据机构ID查询部门信息", notes = "根据机构ID查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构ID编码", required = true, dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "查询部门信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-dept:list")
	@ResponseBody
	public Object pairs(@RequestParam String orgId) throws Exception {
		return getAuthzDepartmentService().getPairValues(orgId);
	}
	
	@ApiOperation(value = "创建部门信息", notes = "增加一个新的部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "deptVo", value = "部门信息", required = true, dataType = "AuthzDepartmentNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "创建部门信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-dept:new")
	@ResponseBody
	public Object newDept(@Valid @RequestBody AuthzDepartmentNewVo deptVo) throws Exception {
		
		int count1 = getAuthzDepartmentService().getCountByCode(deptVo.getCode(), deptVo.getOrgId(), null);
		if(count1 > 0) {
			return fail("authz.dept.new.code-exists");
		}
		int count2 = getAuthzDepartmentService().getCountByName(deptVo.getName(), deptVo.getOrgId(), null);
		if(count2 > 0) {
			return fail("authz.dept.new.name-exists");
		}
		
		AuthzDepartmentModel model = getBeanMapper().map(deptVo, AuthzDepartmentModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzDepartmentService().insert(model);
		if(result > 0) {
			return success("authz.dept.new.success", result);
		}
		return fail("authz.dept.new.fail", result);
	}
	
	@ApiOperation(value = "更新部门信息", notes = "更新部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "deptVo", value = "部门信息", required = true, dataType = "AuthzDepartmentRenewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "更新部门信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-dept:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzDepartmentRenewVo deptVo) throws Exception {
		
		int count1 = getAuthzDepartmentService().getCountByCode(deptVo.getCode(), deptVo.getOrgId(), deptVo.getId());
		if(count1 > 0) {
			return fail("authz.dept.renew.code-exists");
		}
		int count2 = getAuthzDepartmentService().getCountByName(deptVo.getName(), deptVo.getOrgId(), deptVo.getId());
		if(count2 > 0) {
			return fail("authz.dept.renew.name-exists");
		}
		
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
	@PostMapping("status")
	@RequiresPermissions("authz-dept:status")
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
		@ApiImplicitParam(paramType = "path", name = "id", value = "部门信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "删除部门信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-dept:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		
		int count1 = getAuthzDepartmentService().getCountByParent(id);
		if(count1 > 0 ) {
			return fail("authz.dept.delete.child-exists");
		}
		int count2 = getAuthzDepartmentService().getStaffCount(id);
		if(count2 > 0 ) {
			return fail("authz.dept.delete.staff-exists");
		}
		
		int result = getAuthzDepartmentService().delete(id);
		if(result > 0) {
			return success("authz.dept.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.delete.fail", result);
	}
	
	@ApiOperation(value = "查询部门信息", notes = "根据ID查询部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", required = true, value = "部门信息ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzDepartmentVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "查询部门信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-dept:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzDepartmentModel model = getAuthzDepartmentService().getModel(id);
		if( model == null) {
			return fail("authz.dept.not-found");
		}
		return getBeanMapper().map(model, AuthzDepartmentVo.class);
	}

	public IAuthzDepartmentService getAuthzDepartmentService() {
		return authzDepartmentService;
	}
	
}
