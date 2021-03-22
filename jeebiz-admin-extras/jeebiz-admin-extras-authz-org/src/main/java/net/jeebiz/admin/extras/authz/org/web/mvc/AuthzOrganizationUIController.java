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
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "组织机构管理：UI跳转")
@Controller
@RequestMapping("/authz/org/ui/")
public class AuthzOrganizationUIController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzCompanyService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("authz-org:list")
	public String list(@ApiIgnore Model uiModel) {
		return "html/authz/org/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("authz-org:new")
	public String newCompany(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzCompanyService().getPairValues(""));
		return "html/authz/org/new";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "父级单位ID", dataType = "String")
	})
	@GetMapping("child")
	@RequiresPermissions("authz-org:new")
	public String child(@RequestParam String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("parent", id);
		uiModel.addAttribute("orgs", getAuthzCompanyService().getPairValues(""));
		return "html/authz/org/child";
	}
    
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "单位ID", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("org:renew")
	public String renew(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzCompanyService().getPairValues(id));
		uiModel.addAttribute("model", getAuthzCompanyService().getModel(id));
		return "html/authz/org/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "组卷策略ID", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-org:detail")
	public String detail(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("orgs", getAuthzCompanyService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzCompanyService().getModel(id));
		return "html/authz/org/detail";
	}

	public IAuthzOrganizationService getAuthzCompanyService() {
		return authzCompanyService;
	}

}
