/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.mvc;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptNewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptRenewVo;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureOptVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;

@Api(tags = "功能操作：数据维护（Ok）")
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
@RequestMapping(value = "/extras/feature/opt/")
public class AuthzFeatureOptController extends BaseMapperController{

	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;
	
	@ApiOperation(value = "增加功能操作代码信息", notes = "增加功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "optVo", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptNewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "新增功能操作代码-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("opt:new")
	@ResponseBody
	public Object newOpt(@Valid @RequestBody AuthzFeatureOptNewVo optVo) throws Exception {
		
		int count = getAuthzFeatureOptService().getOptCountByName(optVo.getName(), optVo.getFeatureId(), null);
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		
		AuthzFeatureOptModel model = getBeanMapper().map(optVo, AuthzFeatureOptModel.class);
		int total = getAuthzFeatureOptService().insert(model);
		if(total > 0) {
			return success("opt.new.success", total);
		}
		return fail("opt.new.fail", total);
	}
	
	@ApiOperation(value = "修改功能操作代码", notes = "修改功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "optVo", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptRenewVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "修改功能操作代码-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("opt:renew")
	@ResponseBody
	public Object renewOpt(@Valid @RequestBody AuthzFeatureOptRenewVo optVo) throws Exception {
		
		int count = getAuthzFeatureOptService().getOptCountByName(optVo.getName(), optVo.getFeatureId(), optVo.getId());
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		
		AuthzFeatureOptModel model = getBeanMapper().map(optVo, AuthzFeatureOptModel.class);
		int total = getAuthzFeatureOptService().update(model);
		if(total > 0) {
			return success("opt.renew.success", total);
		}
		return fail("opt.renew.fail", total);
	}
	
	@ApiOperation(value = "查询功能操作代码信息", notes = "根据功能操作代码ID查询功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能操作代码ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzFeatureOptVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "查询功能操作代码-ID：${optid}", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("opt:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		AuthzFeatureOptModel model = getAuthzFeatureOptService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("opt.get.empty"));
		}
		return getBeanMapper().map(model, AuthzFeatureOptVo.class);
	}
	
	@ApiOperation(value = "删除功能操作代码信息", notes = "删除功能操作代码信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能操作代码ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = "删除功能操作代码-名称：${optid}", opt = BusinessType.DELETE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("opt:delete")
	@ResponseBody
	public Object delOpt(@PathVariable String id) throws Exception { 
		int total = getAuthzFeatureOptService().delete(id);
		if(total > 0) {
			return success("opt.delete.success", total);
		}
		return success("opt.delete.fail", total);
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public void setAuthzFeatureOptService(IAuthzFeatureOptService authzFeatureOptService) {
		this.authzFeatureOptService = authzFeatureOptService;
	}
	
}
