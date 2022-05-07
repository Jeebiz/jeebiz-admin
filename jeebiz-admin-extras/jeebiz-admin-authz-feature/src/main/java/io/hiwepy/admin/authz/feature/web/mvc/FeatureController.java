/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
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

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.admin.authz.feature.enums.FeatureNodeType;
import io.hiwepy.admin.authz.feature.service.IFeatureOptService;
import io.hiwepy.admin.authz.feature.service.IFeatureService;
import io.hiwepy.admin.authz.feature.setup.Constants;
import io.hiwepy.admin.authz.feature.strategy.FeatureStrategyRouter;
import io.hiwepy.admin.authz.feature.web.dto.FeatureDTO;
import io.hiwepy.admin.authz.feature.web.dto.FeatureNewDTO;
import io.hiwepy.admin.authz.feature.web.dto.FeatureRenewDTO;
import io.hiwepy.admin.authz.feature.web.dto.FeatureTreeNode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping("feature")
public class FeatureController extends BaseMapperController{

	@Autowired
	private IFeatureService featureService;
	@Autowired
	private IFeatureOptService featureOptService;
	@Autowired
	private FeatureStrategyRouter featureStrategyRouter;
	@Autowired
	private RedisOperationTemplate redisOperation;

	@ApiOperation(value = "功能菜单（全部数据）", notes = "查询功能菜单列表")
	@GetMapping("list")
	@RequiresAuthentication
	public ApiRestResponse<List<FeatureDTO>> list(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		List<FeatureDTO> featureDTOList = Lists.newArrayList();
		for (FeatureEntity model : featureList) {
			featureDTOList.add(getBeanMapper().map(model, FeatureDTO.class));
		}
		return ApiRestResponse.success(featureDTOList);
	}

	@ApiOperation(value = "功能菜单-树形结构数据（全部菜单数据）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("nav")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> nav(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList));
	}

	@ApiOperation(value = "功能菜单-树形结构数据（包含功能操作按钮）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@GetMapping("tree")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> tree(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
		//return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
	}

	@ApiOperation(value = "功能菜单-扁平结构数据（包含功能操作按钮）", notes = "查询功能菜单扁平结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<FeatureTreeNode>> flat(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOpts();

		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList, featureOptList));
	}

	@ApiOperation(value = "增加功能菜单信息", notes = "增加功能菜单信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "FeatureNewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "新增功能菜单-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("feature:new")
	@ResponseBody
	public ApiRestResponse<String> feature(@Valid @RequestBody FeatureNewDTO featureDTO) throws Exception {
		Long count = getFeatureService().getCountByCode(featureDTO.getCode(), null);
		if(count > 0) {
			return fail("feature.new.code-exists");
		}
		FeatureEntity model = getBeanMapper().map(featureDTO, FeatureEntity.class);
		/**
		 * 菜单类型(1:原生|2:自定义)
		 */
		model.setType("2");
		boolean total = getFeatureService().save(model);
		if(total) {
			// 删除菜单缓存
			redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
			return success("feature.new.success", total);
		}
		return fail("feature.new.fail", total);
	}

	@ApiOperation(value = "修改功能菜单信息", notes = "修改功能菜单信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "FeatureRenewDTO")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "修改功能菜单-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("feature:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody FeatureRenewDTO featureDTO) throws Exception {
		FeatureEntity model = getBeanMapper().map(featureDTO, FeatureEntity.class);
		boolean total = getFeatureService().updateById(model);
		if(total) {
			// 删除菜单缓存
			redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
			return success("feature.renew.success", total);
		}
		return fail("feature.renew.fail", total);
	}

	@ApiOperation(value = "查询功能菜单信息", notes = "根据功能菜单id查询功能菜单信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "query", name = "id", required = true, value = "功能菜单id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单-id：${featureid}", opt = BusinessType.SELECT)
	@PostMapping("detail")
	@RequiresPermissions("feature:detail")
	@ResponseBody
	public ApiRestResponse<FeatureDTO> detail(@RequestParam("id") String id) throws Exception {
		FeatureEntity model = getFeatureService().getFeature(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("feature.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, FeatureDTO.class)) ;
	}

	@ApiOperation(value = "删除功能菜单信息", notes = "删除功能菜单信息")
	@ApiImplicitParams({
		@ApiImplicitParam( paramType = "path", name = "id", required = true, value = "功能菜单id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "删除功能菜单-名称：${featureid}", opt = BusinessType.DELETE)
	@PostMapping("delete")
	@RequiresPermissions("feature:delete")
	@ResponseBody
	public ApiRestResponse<String> delFeature(@RequestParam String id) throws Exception {

		Long count = getFeatureService().getCountByParent(id);
		if(count > 0) {
			return fail("feature.delete.child-exists");
		}

		boolean total = getFeatureService().removeById(id);
		if(total) {
			// 删除菜单缓存
			redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
			return success("feature.delete.success", total);
		}
		return fail("feature.delete.fail", total);
	}

	public IFeatureService getFeatureService() {
		return featureService;
	}

	public void setFeatureService(IFeatureService featureService) {
		this.featureService = featureService;
	}

	public IFeatureOptService getFeatureOptService() {
		return featureOptService;
	}

}
