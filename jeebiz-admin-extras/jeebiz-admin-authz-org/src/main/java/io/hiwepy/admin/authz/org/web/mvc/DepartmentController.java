package io.hiwepy.admin.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.org.dao.entities.DepartmentModel;
import io.hiwepy.admin.authz.org.service.IDepartmentService;
import io.hiwepy.admin.authz.org.setup.Constants;
import io.hiwepy.admin.authz.org.web.dto.DepartmentDTO;
import io.hiwepy.admin.authz.org.web.dto.DepartmentNewDTO;
import io.hiwepy.admin.authz.org.web.dto.DepartmentPaginationDTO;
import io.hiwepy.admin.authz.org.web.dto.DepartmentRenewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "组织机构：部门信息维护")
@RestController
@RequestMapping(value = "/authz/org/dept/")
public class DepartmentController extends BaseApiController {

	@Autowired
	private IDepartmentService authzDepartmentService;

	@ApiOperation(value = "分页查询部门信息", notes = "分页查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "DepartmentPaginationDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "分页查询部门信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-dept:list")
	public Result<DepartmentDTO> list(@Valid @RequestBody DepartmentPaginationDTO paginationDTO) throws Exception {
		
		DepartmentModel model = getBeanMapper().map(paginationDTO, DepartmentModel.class);
		
		Page<DepartmentModel> pageResult = getDepartmentService().getPagedList(model);
		List<DepartmentDTO> retList = Lists.newArrayList();
		for (DepartmentModel departmentModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(departmentModel, DepartmentDTO.class));
		}
		
		return new Result<DepartmentDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "部门信息：数据列表集合", notes = "根据机构id查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构id编码", dataType = "String")
	})
	@GetMapping("list")
	@RequiresAuthentication
	public Object list(@RequestParam(required = false) String orgId) throws Exception {
		
		List<DepartmentModel> resultList = getDepartmentService().getModelList(orgId);
		if( CollectionUtils.isEmpty(resultList)) {
			return ApiRestResponse.fail(getMessage("authz.dept.not-found"));
		}
		List<DepartmentDTO> retList = Lists.newArrayList();
		for (DepartmentModel model : resultList) {
			retList.add(getBeanMapper().map(model, DepartmentDTO.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "部门信息：键值对集合", notes = "根据机构id查询部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构id编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "查询部门信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-dept:list")
	public ApiRestResponse<List<PairModel>> pairs(@RequestParam String orgId) throws Exception {
		List<PairModel> pairValues = getDepartmentService().getPairValues(orgId);
		return ApiRestResponse.success(pairValues);
	}
	
	@ApiOperation(value = "创建部门信息", notes = "增加一个新的部门信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "deptDTO", value = "部门信息", required = true, dataType = "DepartmentNewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "创建部门信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-dept:new")
	public ApiRestResponse<String> newDept(@Valid @RequestBody DepartmentNewDTO deptDTO) throws Exception {
		
		int count1 = getDepartmentService().getCountByCode(deptDTO.getCode(), deptDTO.getOrgId(), null);
		if(count1 > 0) {
			return fail("authz.dept.new.code-exists");
		}
		int count2 = getDepartmentService().getCountByName(deptDTO.getName(), deptDTO.getOrgId(), null);
		if(count2 > 0) {
			return fail("authz.dept.new.name-exists");
		}
		
		DepartmentModel model = getBeanMapper().map(deptDTO, DepartmentModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setCreator(principal.getUserid());
		// 新增一条数据库配置记录
		boolean result = getDepartmentService().save(model);
		if(result) {
			return success("authz.dept.new.success", result);
		}
		return fail("authz.dept.new.fail", result);
	}
	
	@ApiOperation(value = "更新部门信息", notes = "更新部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "deptDTO", value = "部门信息", required = true, dataType = "DepartmentRenewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "更新部门信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-dept:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody DepartmentRenewDTO deptDTO) throws Exception {
		
		int count1 = getDepartmentService().getCountByCode(deptDTO.getCode(), deptDTO.getOrgId(), deptDTO.getId());
		if(count1 > 0) {
			return fail("authz.dept.renew.code-exists");
		}
		int count2 = getDepartmentService().getCountByName(deptDTO.getName(), deptDTO.getOrgId(), deptDTO.getId());
		if(count2 > 0) {
			return fail("authz.dept.renew.name-exists");
		}
		
		DepartmentModel model = getBeanMapper().map(deptDTO, DepartmentModel.class);
		boolean result = getDepartmentService().updateById(model);
		if(result) {
			return success("authz.dept.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.renew.fail", result);
	}
	
	@ApiOperation(value = "更新部门信息状态", notes = "更新部门信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "部门信息id", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "部门信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "更新部门信息状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("authz-dept:status")
	public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getDepartmentService().setStatus(id, status);
		if(result == 1) {
			return success("authz.dept.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.status.fail", result);
	}
	
	@ApiOperation(value = "删除部门信息", notes = "删除部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "部门信息id", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "删除部门信息", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("authz-dept:delete")
	public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {
		
		Long count1 = getDepartmentService().getCountByParent(id);
		if(count1 > 0 ) {
			return fail("authz.dept.delete.child-exists");
		}
		int count2 = getDepartmentService().getStaffCount(id);
		if(count2 > 0 ) {
			return fail("authz.dept.delete.staff-exists");
		}
		
		boolean result = getDepartmentService().removeById(id);
		if(result) {
			return success("authz.dept.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.dept.delete.fail", result);
	}
	
	@ApiOperation(value = "查询部门信息", notes = "根据id查询部门信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", required = true, value = "部门信息id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_DEPT, business = "查询部门信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("authz-dept:detail")
	public ApiRestResponse<DepartmentDTO> detail(@RequestParam("id") String id) throws Exception { 
		DepartmentModel model = getDepartmentService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("authz.dept.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, DepartmentDTO.class));
	}

	public IDepartmentService getDepartmentService() {
		return authzDepartmentService;
	}
	
}
