package io.hiwepy.admin.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

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
import io.hiwepy.admin.authz.org.dao.entities.OrganizationEntity;
import io.hiwepy.admin.authz.org.service.IOrganizationService;
import io.hiwepy.admin.authz.org.setup.Constants;
import io.hiwepy.admin.authz.org.utils.OrgUtils;
import io.hiwepy.admin.authz.org.web.dto.OrganizationDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationDeleteDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationNewDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationPaginationDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationRenewDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationStatusDTO;
import io.hiwepy.admin.authz.org.web.dto.OrganizationTreeDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "组织机构：机构信息维护")
@RestController
@RequestMapping(value = "/authz/org/")
public class OrganizationController extends BaseMapperController {
	
	@Autowired
	private IOrganizationService authzOrganizationService;

	@ApiOperation(value = "分页查询机构信息", notes = "分页查询机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "OrganizationPaginationDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "分页查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-org:list")
	public Result<OrganizationDTO> list(@Valid @RequestBody OrganizationPaginationDTO paginationDTO) throws Exception {
		
		OrganizationEntity model = getBeanMapper().map(paginationDTO, OrganizationEntity.class);
		Page<OrganizationEntity> pageResult = getOrganizationService().getPagedList(model);
		List<OrganizationDTO> retList = Lists.newArrayList();
		for (OrganizationEntity orgModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(orgModel, OrganizationDTO.class));
		}
		
		return new Result<OrganizationDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "机构信息：键值对集合", notes = "查询机构信息列表")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-org:list")
	public ApiRestResponse<List<PairModel>> pairs() throws Exception {
		List<PairModel> pairValues = getOrganizationService().getPairValues("");
		return ApiRestResponse.success(pairValues);
	}
	
	
	@ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "orgDTO", value = "机构信息传输对象", required = true, dataType = "OrganizationNewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-org:new")
	public ApiRestResponse<String> newOrg(@Valid @RequestBody OrganizationNewDTO orgDTO) throws Exception {
		
		Long count1 = getOrganizationService().getCountByCode(orgDTO.getCode(), null);
		if(count1 > 0) {
			return fail("authz.org.new.code-exists");
		}
		Long count2 = getOrganizationService().getCountByName(orgDTO.getName(), null);
		if(count2 > 0) {
			return fail("authz.org.new.name-exists");
		}
		int count3 = getOrganizationService().getRootCount();
		if(count3 == 1 && StringUtils.equalsIgnoreCase("0", orgDTO.getCode())) {
			return fail("authz.org.new.root-exists");
		}
		OrganizationEntity model = getBeanMapper().map(orgDTO, OrganizationEntity.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setCreator(principal.getUserid());
		// 新增一条数据库配置记录
		boolean result = getOrganizationService().save(model);
		if(result) {
			return success("authz.org.new.success", result);
		}
		return fail("authz.org.new.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息", notes = "更新机构信息")
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-org:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody OrganizationRenewDTO orgDTO) throws Exception {
		
		// 查询历史记录
		Long count1 = getOrganizationService().getCountByCode(orgDTO.getCode(), orgDTO.getId());
		if(count1 > 0) {
			return fail("authz.org.renew.code-exists");
		}
		Long count2 = getOrganizationService().getCountByName(orgDTO.getName(), orgDTO.getId());
		if(count2 > 0) {
			return fail("authz.org.renew.name-exists");
		}
		
		OrganizationEntity model = getBeanMapper().map(orgDTO, OrganizationEntity.class);
		boolean result = getOrganizationService().updateById(model);
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
	public ApiRestResponse<String> status(@Valid @RequestBody OrganizationStatusDTO satusDTO) throws Exception {
		int result = getOrganizationService().setStatus(satusDTO.getId(), satusDTO.getStatus());
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
	public ApiRestResponse<String> delete(@Valid @RequestBody OrganizationDeleteDTO deleteDTO) throws Exception {
		
		Long count1 = getOrganizationService().getCountByParent(deleteDTO.getId());
		if(count1 > 0 ) {
			return fail("authz.org.delete.child-exists");
		}
		int count2 = getOrganizationService().getDeptCount(deleteDTO.getId());
		if(count2 > 0 ) {
			return fail("authz.org.delete.dept-exists");
		}
		
		// 执行机构信息删除操作
		boolean result = getOrganizationService().removeById(deleteDTO.getId());
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
	public ApiRestResponse<OrganizationDTO> detail(@RequestParam("id") String id) throws Exception { 
		OrganizationEntity model = getOrganizationService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("authz.org.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, OrganizationDTO.class));
	}

	@ApiOperation(value = "组织机构-树形结构数据（全部数据）", notes = "查询组织机构树形结构数据")
	@GetMapping("tree")
	@RequiresAuthentication
	public ApiRestResponse<List<OrganizationTreeDTO>> tree(){
		// 所有的组织机构
		List<OrganizationEntity> orgList = getOrganizationService().getOrgList();
		// 返回组织机构树形结构
		return ApiRestResponse.success(OrgUtils.getOrgTreeList(orgList));
	}
	
	public IOrganizationService getOrganizationService() {
		return authzOrganizationService;
	}

	public void setOrganizationService(IOrganizationService authzOrganizationService) {
		this.authzOrganizationService = authzOrganizationService;
	}

}
