/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.shadow.web.mvc;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.feature.dao.entities.FeatureEntity;
import net.jeebiz.admin.authz.feature.dao.entities.FeatureOptEntity;
import net.jeebiz.admin.authz.feature.service.IFeatureOptService;
import net.jeebiz.admin.authz.feature.service.IFeatureService;
import net.jeebiz.admin.authz.feature.setup.Constants;
import net.jeebiz.admin.shadow.utils.FeatureTreeUtils;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping(value = "/extras/feature/layui/")
public class FeatureLayuiController extends BaseApiController{

	@Autowired
	protected IFeatureService featureService;
	@Autowired
	protected IFeatureOptService featureOptService;

	@ApiOperation(value = "功能菜单-树形结构数据（全部数据）", notes = "查询功能菜单树形结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@PostMapping("tree")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<Map<String, Object>>> tree(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOpts();
		/*
		{
		  "code": 0,
		  "msg": "获取成功",
		  "data": {
		    "trees":[
		    	{"name": "用户管理", "value": "xsgl", "checked": true},
		    	{"name": "用户组管理", "value": "sbgl", "checked": true, "list": [
		    		{"name": "角色管理", "value": "sbgl-sbsjlb-1", "checked": true, "list":[
		    			{"name": "添加角色", "value": "sbgl-sbsjlb-dj-1", "checked": true},
		    			{"name": "角色列表", "value": "sbgl-sbsjlb-yl-1", "checked": false}
		    		]},
		        {"name": "管理员管理", "value": "sbgl-sbsjlb-2", "checked": true, "list":[
		          {"name": "添加管理员", "value": "sbgl-sbsjlb-dj-2", "checked": true},
		          {"name": "管理员列表", "value": "sbgl-sbsjlb-yl-2", "checked": false},
		          {"name": "管理员管理", "value": "sbgl-sbsjlb-3", "checked": true}
		        ]}
		      ]}
		    ]
		  }
		/}
		 */
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureTreeUtils.getAuthTreeList(featureList, featureOptList));
	}

	@ApiOperation(value = "功能菜单-扁平结构数据（全部数据）", notes = "查询功能菜单扁平结构数据")
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public ApiRestResponse<List<Map<String, Object>>> flat(){
		// 所有的功能菜单
		List<FeatureEntity> featureList = getFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOpts();
		/*
		{
		  "code": 0,
		  "msg": "获取成功",
		  "data": {
		    "trees":[
		    	{"name": "用户管理", "value": "xsgl", "checked": true},
		    	{"name": "用户组管理", "value": "sbgl", "checked": true, "list": [
		    		{"name": "角色管理", "value": "sbgl-sbsjlb-1", "checked": true, "list":[
		    			{"name": "添加角色", "value": "sbgl-sbsjlb-dj-1", "checked": true},
		    			{"name": "角色列表", "value": "sbgl-sbsjlb-yl-1", "checked": false}
		    		]},
		        {"name": "管理员管理", "value": "sbgl-sbsjlb-2", "checked": true, "list":[
		          {"name": "添加管理员", "value": "sbgl-sbsjlb-dj-2", "checked": true},
		          {"name": "管理员列表", "value": "sbgl-sbsjlb-yl-2", "checked": false},
		          {"name": "管理员管理", "value": "sbgl-sbsjlb-3", "checked": true}
		        ]}
		      ]}
		    ]
		  }
		/}
		 */
		// 返回各级菜单 + 对应的功能权限数据
		return ApiRestResponse.success(FeatureTreeUtils.getAuthTreeList(featureList, featureOptList));
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

	public void setFeatureOptService(IFeatureOptService featureOptService) {
		this.featureOptService = featureOptService;
	}

}
