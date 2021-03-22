/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.mvc;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.inform.service.IInformService;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "消息通知：UI跳转")
@Controller
@RequestMapping("/extras/inform/ui/")
public class InformUIController extends BaseMapperController {
	
	@Autowired
	private IInformService informService;
	 
	@ApiIgnore
	@GetMapping("list")
	@RequiresAuthentication
	public String list(@ApiIgnore Model uiModel) {
		
		uiModel.addAttribute("stats", getInformService().getStats(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid()));
		return "html/extras/inform/list";
	}
	
	@ApiIgnore
	@GetMapping("detail/{id}")
	@RequiresAuthentication
	public String detail(@PathVariable("id") String id, @ApiIgnore Model model) {
		model.addAttribute("inform", getInformService().getModel(id));
		model.addAttribute("id", id);
		return "html/extras/inform/detail";
	}

	public IInformService getInformService() {
		return informService;
	}

	public void setInformService(IInformService informService) {
		this.informService = informService;
	}
	
}
