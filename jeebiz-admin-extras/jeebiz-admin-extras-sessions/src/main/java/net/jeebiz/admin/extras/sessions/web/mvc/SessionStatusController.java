/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.web.mvc;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.sessions.service.IOnlineSessionService;
import net.jeebiz.admin.extras.sessions.setup.Constants;
import net.jeebiz.admin.extras.sessions.web.vo.OnlineSessionVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

/**
 * http://jinnianshilongnian.iteye.com/blog/2047643
 */
@Api(tags = "会话管理:用于管理在线会话")
@RestController
@RequestMapping("/extras/sessions/")
@RequiresPermissions("session:*")
public class SessionStatusController extends BaseApiController {
	
	@Autowired
	private IOnlineSessionService onlineSessionService;
	
	@ApiOperation(value = "我的应用列表", notes = "分页查询已维护的应用基本信息、订阅服务量")
	@BusinessLog(module = Constants.EXTRAS_SESSIONS, business = "分页查询数据源信息", opt = BusinessType.SELECT)
    @RequestMapping()  
    public String list(Model model) {
		List<OnlineSessionVo> sessions =  getOnlineSessionService().getActiveSessions();  
        model.addAttribute("sessions", sessions);  
        model.addAttribute("sesessionCount", sessions.size());  
        return "sessions/list";  
    }
	
	@ApiOperation(value = "我的应用列表", notes = "分页查询已维护的应用基本信息、订阅服务量")
	@BusinessLog(module = Constants.EXTRAS_SESSIONS, business = "分页查询数据源信息", opt = BusinessType.SELECT)
	@PostMapping(value = "list")
	@ResponseBody
	public Object list() throws Exception {
		List<OnlineSessionVo> sessions =  getOnlineSessionService().getActiveSessions();  
		return new Result<OnlineSessionVo>(sessions);
	}
    
	@ApiOperation(value = "创建我的应用", notes = "增加我的应用信息： 应用名称、开发语言、部署地址等")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "appVo", value = "应用信息传输对象", dataType = "MyApplicationVo") 
	})
	@BusinessLog(module = Constants.EXTRAS_SESSIONS, business = "创建新数据源", opt = BusinessType.INSERT)
    @RequestMapping("/{sessionId}/forceLogout")  
    public String forceLogout(@PathVariable("sessionId") String sessionId,   
        RedirectAttributes redirectAttributes) {  
    	getOnlineSessionService().forceLogout(sessionId);
        redirectAttributes.addFlashAttribute("msg", "强制退出成功！");  
        return "redirect:/sessions";  
    }

	public IOnlineSessionService getOnlineSessionService() {
		return onlineSessionService;
	}

	public void setOnlineSessionService(IOnlineSessionService onlineSessionService) {
		this.onlineSessionService = onlineSessionService;
	}

}
