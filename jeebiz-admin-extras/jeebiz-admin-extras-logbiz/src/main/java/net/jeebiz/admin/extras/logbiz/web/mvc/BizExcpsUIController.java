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
import net.jeebiz.admin.extras.logbiz.service.IBizExcpService;
import net.jeebiz.boot.api.webmvc.BaseController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "系统异常日志UI跳转")
@ApiIgnore
@Controller
@RequestMapping("/extras/logs/excp/ui/")
public class BizExcpsUIController extends BaseController {

	@Autowired
	private IBizExcpService bizExcpService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("logs-excp:list")
    public String list(HttpServletRequest request, Model model) {
    	return "html/extras/logs/excp/list";
    }
	
	public IBizExcpService getBizExcpService() {
		return bizExcpService;
	}

	public void setBizExcpService(IBizExcpService bizExcpService) {
		this.bizExcpService = bizExcpService;
	}
	
}
