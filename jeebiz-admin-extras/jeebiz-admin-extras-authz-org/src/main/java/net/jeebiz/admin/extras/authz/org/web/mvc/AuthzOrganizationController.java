package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "组织机构：机构信息维护")
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
@RequestMapping(value = "/authz/org/")
public class AuthzOrganizationController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;

	@ApiOperation(value = "分页查询机构信息", notes = "分页查询机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzOrganizationPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "分页查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-org:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzOrganizationPaginationVo paginationVo) throws Exception {
		
		AuthzOrganizationModel model = getBeanMapper().map(paginationVo, AuthzOrganizationModel.class);
		Page<AuthzOrganizationModel> pageResult = getAuthzOrganizationService().getPagedList(model);
		List<AuthzOrganizationVo> retList = Lists.newArrayList();
		for (AuthzOrganizationModel orgModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(orgModel, AuthzOrganizationVo.class));
		}
		
		return new Result<AuthzOrganizationVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "查询机构信息列表", notes = "查询机构信息列表")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-org:list")
	@ResponseBody
	public Object pairs() throws Exception {
		return getAuthzOrganizationService().getPairValues("");
	}
	
	@ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "orgVo", value = "机构信息传输对象", required = true, dataType = "AuthzOrganizationNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-org:new")
	@ResponseBody
	public Object newOrg(@Valid @RequestBody AuthzOrganizationNewVo orgVo) throws Exception {
		
		int count1 = getAuthzOrganizationService().getCountByCode(orgVo.getCode(), null);
		if(count1 > 0) {
			return fail("authz.org.new.code-exists");
		}
		int count2 = getAuthzOrganizationService().getCountByName(orgVo.getName(), null);
		if(count2 > 0) {
			return fail("authz.org.new.name-exists");
		}
		int count3 = getAuthzOrganizationService().getRootCount();
		if(count3 == 1 && StringUtils.equalsIgnoreCase("0", orgVo.getParent())) {
			return fail("authz.org.new.root-exists");
		}
		AuthzOrganizationModel model = getBeanMapper().map(orgVo, AuthzOrganizationModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzOrganizationService().insert(model);
		if(result > 0) {
			return success("authz.org.new.success", result);
		}
		return fail("authz.org.new.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息", notes = "更新机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "orgVo", value = "机构信息", required = true, dataType = "AuthzOrganizationRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-org:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzOrganizationRenewVo orgVo) throws Exception {
		
		// 查询历史记录
		int count1 = getAuthzOrganizationService().getCountByCode(orgVo.getCode(), orgVo.getId());
		if(count1 > 0) {
			return fail("authz.org.renew.code-exists");
		}
		int count2 = getAuthzOrganizationService().getCountByName(orgVo.getName(), orgVo.getId());
		if(count2 > 0) {
			return fail("authz.org.renew.name-exists");
		}
		
		AuthzOrganizationModel model = getBeanMapper().map(orgVo, AuthzOrganizationModel.class);
		int result = getAuthzOrganizationService().update(model);
		if(result == 1) {
			return success("authz.org.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.renew.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息状态", notes = "更新机构信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "机构信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "机构信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息状态", opt = BusinessType.UPDATE)
	@GetMapping(value = "status")
	@RequiresPermissions("authz-org:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzOrganizationService().setStatus(id, status);
		if(result == 1) {
			return success("authz.org.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.status.fail", result);
	}
	
	@ApiOperation(value = "删除机构信息", notes = "删除机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", value = "机构信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "删除机构信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-org:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		
		int count1 = getAuthzOrganizationService().getCountByParent(id);
		if(count1 > 0 ) {
			return fail("authz.org.delete.child-exists");
		}
		int count2 = getAuthzOrganizationService().getDeptCount(id);
		if(count2 > 0 ) {
			return fail("authz.org.delete.dept-exists");
		}
		
		// 执行机构信息删除操作
		int result = getAuthzOrganizationService().delete(id);
		if(result > 0) {
			return success("authz.org.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.delete.fail", result);
	}
	
	@ApiOperation(value = "根据ID查询机构信息", notes = "根据ID查询机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "机构信息ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzOrganizationVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-org:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzOrganizationModel model = getAuthzOrganizationService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("authz.org.not-found"));
		}
		return getBeanMapper().map(model, AuthzOrganizationVo.class);
	}

	public IAuthzOrganizationService getAuthzOrganizationService() {
		return authzOrganizationService;
	}

	public void setAuthzOrganizationService(IAuthzOrganizationService authzOrganizationService) {
		this.authzOrganizationService = authzOrganizationService;
	}

}
