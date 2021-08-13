/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.logbiz.service.IBizLogService;
import net.jeebiz.boot.api.web.BaseController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "功能操作日志UI跳转")
@ApiIgnore
@RestController
@RequestMapping("/extras/logs/biz/ui/")
public class BizLogsUIController extends BaseController {

	@Autowired
	private IBizLogService bizLogService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("logs-biz:list")
    public String list(HttpServletRequest request, Model model) {
    	return "html/extras/logs/excp/list";
    }
	
	public IBizLogService getBizLogService() {
		return bizLogService;
	}

	public void setBizLogService(IBizLogService bizLogService) {
		this.bizLogService = bizLogService;
	}
	
}
