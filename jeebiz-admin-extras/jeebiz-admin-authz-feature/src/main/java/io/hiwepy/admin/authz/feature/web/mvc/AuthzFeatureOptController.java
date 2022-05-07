/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.web.mvc;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.admin.authz.feature.service.IAuthzFeatureOptService;
import io.hiwepy.admin.authz.feature.setup.Constants;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureOptDTO;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureOptNewDTO;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureOptRenewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;

@Api(tags = "功能操作：数据维护（Ok）")
@RestController
@RequestMapping(value = "feature/opt")
public class AuthzFeatureOptController extends BaseMapperController{

	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;
	@Autowired
	private RedisOperationTemplate redisOperation;

	@ApiOperation(value = "增加功能操作代码信息", notes = "增加功能操作代码信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "optDTO", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = Constants.Biz.AUTHZ_FEATURE_OPT_NEW, opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("opt:new")
	@ResponseBody
	public ApiRestResponse<String> newOpt(@Valid @RequestBody AuthzFeatureOptNewDTO optDTO) throws Exception {
		int count = getAuthzFeatureOptService().getOptCountByName(optDTO.getName(), optDTO.getFeatureId(), null);
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		AuthzFeatureOptEntity model = getBeanMapper().map(optDTO, AuthzFeatureOptEntity.class);
		boolean total = getAuthzFeatureOptService().save(model);
		if(total) {
			// 删除菜单缓存
			getRedisOperation().del(Constants.AUTHZ_FEATURE_CACHE);
			return success("opt.new.success", total);
		}
		return fail("opt.new.fail", total);
	}

	@ApiOperation(value = "修改功能操作代码", notes = "修改功能操作代码信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "optDTO", value = "功能操作代码信息", required = true, dataType = "AuthzFeatureOptRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = Constants.Biz.AUTHZ_FEATURE_OPT_RENEW, opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("opt:renew")
	@ResponseBody
	public ApiRestResponse<String> renewOpt(@Valid @RequestBody AuthzFeatureOptRenewDTO optDTO) throws Exception {
		int count = getAuthzFeatureOptService().getOptCountByName(optDTO.getName(), optDTO.getFeatureId(), optDTO.getId());
		if(count > 0) {
			return fail("opt.new.name-exists");
		}
		AuthzFeatureOptEntity model = getBeanMapper().map(optDTO, AuthzFeatureOptEntity.class);
		boolean total = getAuthzFeatureOptService().updateById(model);
		if(total) {
			// 删除菜单缓存
			getRedisOperation().del(Constants.AUTHZ_FEATURE_CACHE);
			return success("opt.renew.success", total);
		}
		return fail("opt.renew.fail", total);
	}

	@ApiOperation(value = "查询功能操作代码信息", notes = "根据功能操作代码id查询功能操作代码信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "功能操作代码id", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresPermissions("opt:detail")
	@ResponseBody
	public ApiRestResponse<AuthzFeatureOptDTO> detail(@RequestParam("id") String id) throws Exception {
		AuthzFeatureOptEntity model = getAuthzFeatureOptService().getFeatureOpt(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("opt.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, AuthzFeatureOptDTO.class));
	}

	@ApiOperation(value = "删除功能操作代码信息", notes = "删除功能操作代码信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能操作代码id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE_OPT, business = Constants.Biz.AUTHZ_FEATURE_OPT_DETAIL, opt = BusinessType.DELETE)
	@GetMapping("delete")
	@RequiresPermissions("opt:delete")
	@ResponseBody
	public ApiRestResponse<String> delOpt(@RequestParam("id") String id) throws Exception {
		boolean total = getAuthzFeatureOptService().removeById(id);
		if(total) {
			// 删除菜单缓存
			getRedisOperation().del(Constants.AUTHZ_FEATURE_CACHE);
			return success("opt.delete.success", total);
		}
		return success("opt.delete.fail", total);
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
}