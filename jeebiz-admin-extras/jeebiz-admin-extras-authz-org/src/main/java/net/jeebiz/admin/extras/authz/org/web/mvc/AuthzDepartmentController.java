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
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "组织机构：部门信息维护")
@ApiResponses({ 
	@ApiResponse(code = 0, message = "请求成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10001, message = "认证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10021, message = "授权失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10022, message = "Token缺失", response = ApiRestResponse.class),
	@ApiResponse(code = 10023, message = "Token已过期", response = ApiRestResponse.class),
	@ApiResponse(code = 10024, message = "Token已失效", response = ApiRestResponse.class),
	@ApiResponse(code = 10025, message = "Token错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10110, message = "不允许访问（功能未授权）", response = ApiRestResponse.class),
	@ApiResponse(code = 10111, message = "请求失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10112, message = "数据为空", response = ApiRestResponse.class),
	@ApiResponse(code = 10113, message = "参数类型不匹配", response = ApiRestResponse.class),
	@ApiResponse(code = 10114, message = "缺少矩阵变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10115, message = "缺少URI模板变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10116, message = "缺少Cookie变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10117, message = "缺少请求头", response = ApiRestResponse.class),
	@ApiResponse(code = 10118, message = "缺少参数", response = ApiRestResponse.class),
	@ApiResponse(code = 10119, message = "缺少请求对象", response = ApiRestResponse.class),
	@ApiResponse(code = 10120, message = "参数规则不满足", response = ApiRestResponse.class),
	@ApiResponse(code = 10121, message = "参数绑定错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10122, message = "参数解析错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10123, message = "参数验证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10201, message = "服务器：运行时异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10202, message = "服务器：空值异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10203, message = "服务器：数据类型转换异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10204, message = "服务器：IO异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10205, message = "服务器：未知方法异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10206, message = "服务器：非法参数异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10207, message = "服务器：数组越界异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10208, message = "服务器：网络异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping(value = "/authz/org/dept/")
public class AuthzDepartmentController extends BaseMapperController {

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
			return ApiRestResponse.empty(getMessage("authz.dept.not-found"));
		}
		return getBeanMapper().map(model, AuthzDepartmentVo.class);
	}

	public IAuthzDepartmentService getAuthzDepartmentService() {
		return authzDepartmentService;
	}
	
}
