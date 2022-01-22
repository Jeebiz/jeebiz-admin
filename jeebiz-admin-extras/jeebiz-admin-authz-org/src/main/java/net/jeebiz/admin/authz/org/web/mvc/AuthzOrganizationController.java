package net.jeebiz.admin.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import net.jeebiz.admin.authz.org.web.dto.*;
import org.apache.commons.lang3.StringUtils;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.admin.authz.org.setup.Constants;
import net.jeebiz.admin.authz.org.utils.OrgUtils;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "组织机构：机构信息维护")
@RestController
@RequestMapping(value = "/authz/org/")
public class AuthzOrganizationController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;

	@ApiOperation(value = "分页查询机构信息", notes = "分页查询机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzOrganizationPaginationDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "分页查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-org:list")
	public Result<AuthzOrganizationDTO> list(@Valid @RequestBody AuthzOrganizationPaginationDTO paginationDTO) throws Exception {
		
		AuthzOrganizationModel model = getBeanMapper().map(paginationDTO, AuthzOrganizationModel.class);
		Page<AuthzOrganizationModel> pageResult = getAuthzOrganizationService().getPagedList(model);
		List<AuthzOrganizationDTO> retList = Lists.newArrayList();
		for (AuthzOrganizationModel orgModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(orgModel, AuthzOrganizationDTO.class));
		}
		
		return new Result<AuthzOrganizationDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "机构信息：键值对集合", notes = "查询机构信息列表")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-org:list")
	public ApiRestResponse<List<PairModel>> pairs() throws Exception {
		List<PairModel> pairValues = getAuthzOrganizationService().getPairValues("");
		return ApiRestResponse.success(pairValues);
	}
	
	
	@ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "orgDTO", value = "机构信息传输对象", required = true, dataType = "AuthzOrganizationNewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-org:new")
	public ApiRestResponse<String> newOrg(@Valid @RequestBody AuthzOrganizationNewDTO orgDTO) throws Exception {
		
		Long count1 = getAuthzOrganizationService().getCountByCode(orgDTO.getCode(), null);
		if(count1 > 0) {
			return fail("authz.org.new.code-exists");
		}
		Long count2 = getAuthzOrganizationService().getCountByName(orgDTO.getName(), null);
		if(count2 > 0) {
			return fail("authz.org.new.name-exists");
		}
		int count3 = getAuthzOrganizationService().getRootCount();
		if(count3 == 1 && StringUtils.equalsIgnoreCase("0", orgDTO.getCode())) {
			return fail("authz.org.new.root-exists");
		}
		AuthzOrganizationModel model = getBeanMapper().map(orgDTO, AuthzOrganizationModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setCreator(principal.getUserid());
		// 新增一条数据库配置记录
		boolean result = getAuthzOrganizationService().save(model);
		if(result) {
			return success("authz.org.new.success", result);
		}
		return fail("authz.org.new.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息", notes = "更新机构信息")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-org:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzOrganizationRenewDTO orgDTO) throws Exception {
		
		// 查询历史记录
		Long count1 = getAuthzOrganizationService().getCountByCode(orgDTO.getCode(), orgDTO.getId());
		if(count1 > 0) {
			return fail("authz.org.renew.code-exists");
		}
		Long count2 = getAuthzOrganizationService().getCountByName(orgDTO.getName(), orgDTO.getId());
		if(count2 > 0) {
			return fail("authz.org.renew.name-exists");
		}
		
		AuthzOrganizationModel model = getBeanMapper().map(orgDTO, AuthzOrganizationModel.class);
		boolean result = getAuthzOrganizationService().updateById(model);
		if(result) {
			return success("authz.org.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.renew.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息状态", notes = "更新机构信息状态")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("authz-org:status")
	public ApiRestResponse<String> status(@Valid @RequestBody AuthzOrganizationStatusDTO satusDTO) throws Exception {
		int result = getAuthzOrganizationService().setStatus(satusDTO.getId(), satusDTO.getStatus());
		if(result == 1) {
			return success("authz.org.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.status.fail", result);
	}
	
	@ApiOperation(value = "删除机构信息", notes = "删除机构信息")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "删除机构信息", opt = BusinessType.UPDATE)
	@PostMapping("delete")
	@RequiresPermissions("authz-org:delete")
	public ApiRestResponse<String> delete(@Valid @RequestBody AuthzOrganizationDeleteDTO deleteDTO) throws Exception {
		
		Long count1 = getAuthzOrganizationService().getCountByParent(deleteDTO.getId());
		if(count1 > 0 ) {
			return fail("authz.org.delete.child-exists");
		}
		int count2 = getAuthzOrganizationService().getDeptCount(deleteDTO.getId());
		if(count2 > 0 ) {
			return fail("authz.org.delete.dept-exists");
		}
		
		// 执行机构信息删除操作
		boolean result = getAuthzOrganizationService().removeById(deleteDTO.getId());
		if(result) {
			return success("authz.org.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.delete.fail", result);
	}
	
	@ApiOperation(value = "根据id查询机构信息", notes = "根据id查询机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "机构信息id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("authz-org:detail")
	public ApiRestResponse<AuthzOrganizationDTO> detail(@RequestParam("id") String id) throws Exception { 
		AuthzOrganizationModel model = getAuthzOrganizationService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("authz.org.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzOrganizationDTO.class));
	}

	@ApiOperation(value = "组织机构-树形结构数据（全部数据）", notes = "查询组织机构树形结构数据")
	@GetMapping("tree")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzOrganizationTreeDTO>> tree(){
		// 所有的组织机构
		List<AuthzOrganizationModel> orgList = getAuthzOrganizationService().getOrgList();
		// 返回组织机构树形结构
		return ApiRestResponse.success(OrgUtils.getOrgTreeList(orgList));
	}
	
	public IAuthzOrganizationService getAuthzOrganizationService() {
		return authzOrganizationService;
	}

	public void setAuthzOrganizationService(IAuthzOrganizationService authzOrganizationService) {
		this.authzOrganizationService = authzOrganizationService;
	}

}
