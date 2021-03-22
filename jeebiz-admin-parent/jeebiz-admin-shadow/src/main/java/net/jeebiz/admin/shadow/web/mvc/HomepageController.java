/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 系统默认的重定向地址
 */
@Controller
public class HomepageController {
 
	@ApiIgnore
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		return "redirect:/index";
	}
	
}
