/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.mvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.jeebiz.admin.extras.authz.org.service.IAuthzDepartmentService;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.admin.extras.authz.org.service.IAuthzPostService;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "岗位管理：UI跳转")
@Controller
@RequestMapping("/authz/post/ui/")
public class AuthzPostUIController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;
	@Autowired
	private IAuthzDepartmentService authzDepartmentService;
	@Autowired
	private IAuthzPostService authzPostService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("authz-post:list")
	public String list(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		return "html/mhk/authz/position/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("authz-post:new")
	public String position(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		return "html/mhk/authz/position/new";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "岗位ID编号", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("authz-post:renew")
	public String renew(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzPostService().getModel(id));
		return "html/mhk/authz/position/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "岗位ID编号", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-post:detail")
	public String detail(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzPostService().getModel(id));
		return "html/mhk/authz/position/detail";
	}

	public IAuthzOrganizationService getAuthzOrganizationService() {
		return authzOrganizationService;
	}

	public IAuthzDepartmentService getAuthzDepartmentService() {
		return authzDepartmentService;
	}

	public IAuthzPostService getAuthzPostService() {
		return authzPostService;
	}

}
