/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.mvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.basedata.service.ISettingsService;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "系统参数设置：UI跳转")
@Controller
@RequestMapping("/extras/basedata/settings/ui/")
public class SettingsUIController extends BaseMapperController {
	
	@Autowired
	private ISettingsService settingsService;
	 
	@ApiIgnore
	@GetMapping("index")
	@RequiresPermissions("settings:list")
	public String index(@ApiIgnore Model uiModel) {
		return "html/extras/basedata/settings/index";
	}
	
	@ApiIgnore
	@GetMapping("frontend")
	@RequiresPermissions("settings:frontend")
	public String frontend(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("list", getSettingsService().getModelList("frontend"));
		return "html/extras/basedata/settings/frontend";
	}
	
	@ApiIgnore
	@GetMapping("backend")
	@RequiresPermissions("settings:backtend")
	public String backend(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("list", getSettingsService().getModelList("backtend"));
		return "html/extras/basedata/settings/backend";
	}
    
	@ApiIgnore
	@GetMapping("email")
	@RequiresPermissions("settings:email")
	public String email(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("list", getSettingsService().getModelList("email"));
		return "html/extras/basedata/settings/email";
	}

	public ISettingsService getSettingsService() {
		return settingsService;
	}

	public void setSettingsService(ISettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
}
