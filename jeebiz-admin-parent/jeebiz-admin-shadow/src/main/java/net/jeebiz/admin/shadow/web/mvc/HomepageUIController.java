/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.service.IHomepageService;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzRoleService;

/**
 * 系统默认的重定向地址
 */
@Controller
public class HomepageUIController {

	@Autowired
	private IHomepageService demoService;
	@Autowired
	private IAuthzFeatureService authzFeatureService;
	@Autowired
	private IAuthzRoleService authzRoleService;//角色管理SERVICE
	@Autowired
	private IAuthzFeatureOptService authzFeatureOptService;
	
	/**
	 *  登录成功后的默认重定向地址：可重写返回的路径进行业务系统定制
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		
		AuthzLoginModel user = SubjectUtils.getPrincipal(AuthzLoginModel.class);
		model.addAttribute("user", user);
		// 所有的功能菜单
		//List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 当前用户登录角色所拥有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzRoleService().getFeatures(user.getRoleid());
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		model.addAttribute("features", FeatureDataHandlerFactory.getTreeHandler("tree").handle(featureList, featureOptList));
		
		return "html/index"; 
	}

	@RequestMapping("/")
	public String index2(HttpServletRequest request, Model model) {
		return "redirect:/index";
	}
	
	@RequestMapping("/console")
	public String console(HttpServletRequest request, Model model) {
		return "html/home/console"; 
	}
	
    @RequestMapping("/homepage1")
    String homepage1(){
        return "html/home/homepage1";
    }

    
    @RequestMapping("/homepage2")
    String homepage2(){
        return "html/home/homepage2";
    }

	public IHomepageService getDemoService() {
		return demoService;
	}

	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}

	public IAuthzRoleService getAuthzRoleService() {
		return authzRoleService;
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}
	
}
