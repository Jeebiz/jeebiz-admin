/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.logbiz.service.IAuthzLogService;
import net.jeebiz.boot.api.web.BaseController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "认证授权日志UI跳转")
@ApiIgnore
@Controller
@RequestMapping("/extras/logs/authz/ui/")
public class AuthzLogsUIController extends BaseController {

	@Autowired
	private IAuthzLogService loginLogService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("logs-authz:list")
    public String list(HttpServletRequest request, Model model) {
    	return "html/extras/logs/authz/list";
    }

	public IAuthzLogService getLoginLogService() {
		return loginLogService;
	}

	public void setLoginLogService(IAuthzLogService loginLogService) {
		this.loginLogService = loginLogService;
	}
	
}
