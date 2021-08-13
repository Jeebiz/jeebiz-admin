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
import net.jeebiz.admin.extras.basedata.service.IKeyGroupService;
import net.jeebiz.admin.extras.basedata.service.IKeyValueService;
import net.jeebiz.boot.api.web.BaseController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "基础数据：UI跳转")
@Controller
@RequestMapping("/extras/basedata/keyvalue/ui/")
public class KeyValueUIController extends BaseController  {
	
	@Autowired
	private IKeyGroupService keyGroupService;
	@Autowired
	private IKeyValueService keyValueService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("keyvalue:list")
	public String list(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("groupList", getKeyGroupService().getPairValues());
		return "html/extras/basedata/keyvalue/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("keyvalue:new")
	public String keyvalue(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("groupList", getKeyGroupService().getPairValues());
		return "html/extras/basedata/keyvalue/new";
	}

	public IKeyGroupService getKeyGroupService() {
		return keyGroupService;
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
