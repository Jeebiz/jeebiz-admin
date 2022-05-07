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
import io.hiwepy.admin.authz.org.dao.entities.AuthzTeamModel;
import io.hiwepy.admin.authz.org.service.IAuthzTeamService;
import io.hiwepy.admin.authz.org.setup.Constants;
import io.hiwepy.admin.authz.org.web.dto.AuthzTeamDTO;
import io.hiwepy.admin.authz.org.web.dto.AuthzTeamNewDTO;
import io.hiwepy.admin.authz.org.web.dto.AuthzTeamPaginationDTO;
import io.hiwepy.admin.authz.org.web.dto.AuthzTeamRenewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "组织机构：团队信息维护")
@RestController
@RequestMapping(value = "/authz/org/team/")
public class AuthzTeamController extends BaseApiController {
	
	@Autowired
	private IAuthzTeamService authzTeamService;

	@ApiOperation(value = "分页查询团队信息", notes = "分页查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzTeamPaginationDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "分页查询团队信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-team:list")
	public Result<AuthzTeamDTO> list(@Valid @RequestBody AuthzTeamPaginationDTO paginationDTO) throws Exception {
		
		AuthzTeamModel model = getBeanMapper().map(paginationDTO, AuthzTeamModel.class);
		Page<AuthzTeamModel> pageResult = getAuthzTeamService().getPagedList(model);
		List<AuthzTeamDTO> retList = Lists.newArrayList();
		for (AuthzTeamModel teamModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(teamModel, AuthzTeamDTO.class));
		}
		
		return new Result<AuthzTeamDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "团队信息：数据列表集合", notes = "根据部门id编码查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("list")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzTeamDTO>> list(@RequestParam(required = false) String deptId) throws Exception {
		List<AuthzTeamModel> resultList = getAuthzTeamService().getModelList(deptId);
		if( CollectionUtils.isEmpty(resultList)) {
			return ApiRestResponse.fail(getMessage("authz.team.not-found"));
		}
		List<AuthzTeamDTO> retList = Lists.newArrayList();
		for (AuthzTeamModel model : resultList) {
			retList.add(getBeanMapper().map(model, AuthzTeamDTO.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "团队信息：键值对集合", notes = "根据部门id编码查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresAuthentication
	public ApiRestResponse<List<PairModel>> pairs(@RequestParam(required = false) String deptId) throws Exception {
		return ApiRestResponse.success(getAuthzTeamService().getPairValues(deptId));
	}
	
	@ApiOperation(value = "创建团队信息", notes = "增加一个新的团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "teamDTO", value = "团队信息", required = true, dataType = "AuthzTeamNewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "创建团队信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-team:new")
	public ApiRestResponse<String> team(@Valid @RequestBody AuthzTeamNewDTO teamDTO) throws Exception {
		
		int count1 = getAuthzTeamService().getTeamCountByName(teamDTO.getName(), teamDTO.getDeptId(), null);
		if(count1 > 0) {
			return fail("authz.team.new.name-exists");
		}
		AuthzTeamModel model = getBeanMapper().map(teamDTO, AuthzTeamModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setCreator(principal.getUserid());
		// 新增一条数据库配置记录
		boolean result = getAuthzTeamService().save(model);
		if(result) {
			return success("authz.team.new.success", result);
		}
		return fail("authz.team.new.fail", result);
	}
	
	@ApiOperation(value = "更新团队信息", notes = "更新团队信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "teamDTO", value = "团队信息", required = true, dataType = "AuthzTeamRenewDTO"),
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "更新团队信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-team:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzTeamRenewDTO teamDTO) throws Exception {
		int count1 = getAuthzTeamService().getTeamCountByName(teamDTO.getName(), teamDTO.getDeptId(), teamDTO.getId());
		if(count1 > 0) {
			return fail("authz.team.renew.name-exists");
		}
		AuthzTeamModel model = getBeanMapper().map(teamDTO, AuthzTeamModel.class);
		boolean result = getAuthzTeamService().updateById(model);
		if(result) {
			return success("authz.team.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.team.renew.fail", result);
	}
	
	@ApiOperation(value = "更新团队信息状态", notes = "更新团队信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "团队信息id", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "团队信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "更新团队信息状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("authz-team:status")
	public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzTeamService().setStatus(id, status);
		if(result == 1) {
			return success("authz.team.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.team.status.fail", result);
	}
	
	@ApiOperation(value = "删除团队信息", notes = "删除团队信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "团队信息id", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "删除团队信息", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("authz-team:delete")
	public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {
		
		int count = getAuthzTeamService().getStaffCount(id);
		if(count > 0 ) {
			return fail("authz.team.delete.staff-exists");
		}
		// 执行团队信息删除操作
		boolean result = getAuthzTeamService().removeById(id);
		if(result) {
			return success("authz.team.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.team.delete.fail", result);
	}
	
	@ApiOperation(value = "查询团队信息", notes = "根据id查询团队信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "团队信息id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("authz-team:detail")
	public ApiRestResponse<AuthzTeamDTO> detail(@RequestParam("id") String id) throws Exception { 
		AuthzTeamModel model = getAuthzTeamService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("authz.team.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzTeamDTO.class));
	}

	public IAuthzTeamService getAuthzTeamService() {
		return authzTeamService;
	}

	public void setAuthzTeamService(IAuthzTeamService authzTeamService) {
		this.authzTeamService = authzTeamService;
	}

}
