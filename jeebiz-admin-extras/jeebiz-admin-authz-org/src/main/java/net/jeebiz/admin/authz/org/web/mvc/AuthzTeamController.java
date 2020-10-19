package net.jeebiz.admin.authz.org.web.mvc;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.org.dao.entities.AuthzTeamModel;
import net.jeebiz.admin.authz.org.service.IAuthzTeamService;
import net.jeebiz.admin.authz.org.setup.Constants;
import net.jeebiz.admin.authz.org.web.vo.AuthzTeamNewVo;
import net.jeebiz.admin.authz.org.web.vo.AuthzTeamPaginationVo;
import net.jeebiz.admin.authz.org.web.vo.AuthzTeamRenewVo;
import net.jeebiz.admin.authz.org.web.vo.AuthzTeamVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.CollectionUtils;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "组织机构：团队信息维护")
@RestController
@RequestMapping(value = "/authz/org/team/")
public class AuthzTeamController extends BaseApiController {
	
	@Autowired
	private IAuthzTeamService authzTeamService;

	@ApiOperation(value = "分页查询团队信息", notes = "分页查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzTeamPaginationVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "分页查询团队信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-team:list")
	public Result<AuthzTeamVo> list(@Valid @RequestBody AuthzTeamPaginationVo paginationVo) throws Exception {
		
		AuthzTeamModel model = getBeanMapper().map(paginationVo, AuthzTeamModel.class);
		Page<AuthzTeamModel> pageResult = getAuthzTeamService().getPagedList(model);
		List<AuthzTeamVo> retList = Lists.newArrayList();
		for (AuthzTeamModel teamModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(teamModel, AuthzTeamVo.class));
		}
		
		return new Result<AuthzTeamVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "团队信息：数据列表集合", notes = "根据部门ID编码查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门ID编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("list")
	@RequiresAuthentication
	public ApiRestResponse<List<AuthzTeamVo>> list(@RequestParam(required = false) String deptId) throws Exception {
		List<AuthzTeamModel> resultList = getAuthzTeamService().getModelList(deptId);
		if( CollectionUtils.isEmpty(resultList)) {
			return ApiRestResponse.empty(getMessage("authz.team.not-found"));
		}
		List<AuthzTeamVo> retList = Lists.newArrayList();
		for (AuthzTeamModel model : resultList) {
			retList.add(getBeanMapper().map(model, AuthzTeamVo.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "团队信息：键值对集合", notes = "根据部门ID编码查询团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门ID编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresAuthentication
	public ApiRestResponse<List<PairModel>> pairs(@RequestParam(required = false) String deptId) throws Exception {
		return ApiRestResponse.success(getAuthzTeamService().getPairValues(deptId));
	}
	
	@ApiOperation(value = "创建团队信息", notes = "增加一个新的团队信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "teamVo", value = "团队信息", required = true, dataType = "AuthzTeamNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "创建团队信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-team:new")
	public ApiRestResponse<String> team(@Valid @RequestBody AuthzTeamNewVo teamVo) throws Exception {
		
		int count1 = getAuthzTeamService().getTeamCountByName(teamVo.getName(), teamVo.getDeptId(), null);
		if(count1 > 0) {
			return fail("authz.team.new.name-exists");
		}
		AuthzTeamModel model = getBeanMapper().map(teamVo, AuthzTeamModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUid(principal.getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzTeamService().insert(model);
		if(result > 0) {
			return success("authz.team.new.success", result);
		}
		return fail("authz.team.new.fail", result);
	}
	
	@ApiOperation(value = "更新团队信息", notes = "更新团队信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "teamVo", value = "团队信息", required = true, dataType = "AuthzTeamRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "更新团队信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-team:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody AuthzTeamRenewVo teamVo) throws Exception {
		int count1 = getAuthzTeamService().getTeamCountByName(teamVo.getName(), teamVo.getDeptId(), teamVo.getId());
		if(count1 > 0) {
			return fail("authz.team.renew.name-exists");
		}
		AuthzTeamModel model = getBeanMapper().map(teamVo, AuthzTeamModel.class);
		int result = getAuthzTeamService().update(model);
		if(result == 1) {
			return success("authz.team.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.team.renew.fail", result);
	}
	
	@ApiOperation(value = "更新团队信息状态", notes = "更新团队信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "团队信息ID", dataType = "String"),
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
		@ApiImplicitParam(name = "id", value = "团队信息ID", required = true, dataType = "String")
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
		int result = getAuthzTeamService().delete(id);
		if(result > 0) {
			return success("authz.team.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.team.delete.fail", result);
	}
	
	@ApiOperation(value = "查询团队信息", notes = "根据ID查询团队信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "团队信息ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("authz-team:detail")
	public ApiRestResponse<AuthzTeamVo> detail(@RequestParam("id") String id) throws Exception { 
		AuthzTeamModel model = getAuthzTeamService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("authz.team.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzTeamVo.class));
	}

	public IAuthzTeamService getAuthzTeamService() {
		return authzTeamService;
	}

	public void setAuthzTeamService(IAuthzTeamService authzTeamService) {
		this.authzTeamService = authzTeamService;
	}

}
