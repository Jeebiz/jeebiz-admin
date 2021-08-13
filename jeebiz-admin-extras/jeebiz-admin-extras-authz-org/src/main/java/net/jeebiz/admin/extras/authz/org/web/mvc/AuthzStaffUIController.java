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
import net.jeebiz.admin.extras.authz.org.service.IAuthzStaffService;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "组织机构人员管理：UI跳转")
@Controller
@RequestMapping("/authz/staff/ui/")
public class AuthzStaffUIController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;
	@Autowired
	private IAuthzDepartmentService authzDepartmentService;
	@Autowired
	private IAuthzPostService authzPostService;
	@Autowired
	private IAuthzStaffService authzStaffService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("authz-staff:list")
	public String list(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("posts", getAuthzPostService().getPairValues(""));
		return "html/authz/staff/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("authz-staff:new")
	public String newStaff(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("posts", getAuthzPostService().getPairValues(""));
		return "html/authz/staff/new";
	}
    
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "员工ID编码", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("authz-staff:renew")
	public String renew(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("posts", getAuthzPostService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzStaffService().getModel(id));
		return "html/authz/staff/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "员工ID编码", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-staff:detail")
	public String detail(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzOrganizationService().getPairValues(""));
		uiModel.addAttribute("depts", getAuthzDepartmentService().getPairValues(""));
		uiModel.addAttribute("posts", getAuthzPostService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzStaffService().getModel(id));
		return "html/authz/staff/detail";
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

	public IAuthzStaffService getAuthzStaffService() {
		return authzStaffService;
	}

}
