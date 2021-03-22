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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.sessions.service.IOnlineSessionService;
import net.jeebiz.admin.extras.sessions.setup.Constants;
import net.jeebiz.admin.extras.sessions.web.vo.OnlineSessionVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

/**
 * http://jinnianshilongnian.iteye.com/blog/2047643
 */
@Api(tags = "会话管理:用于管理在线会话")
@ApiResponses({ 
	@ApiResponse(code = 0, message = "请求成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10001, message = "认证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10021, message = "授权失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10022, message = "Token缺失", response = ApiRestResponse.class),
	@ApiResponse(code = 10023, message = "Token已过期", response = ApiRestResponse.class),
	@ApiResponse(code = 10024, message = "Token已失效", response = ApiRestResponse.class),
	@ApiResponse(code = 10025, message = "Token错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10110, message = "不允许访问（功能未授权）", response = ApiRestResponse.class),
	@ApiResponse(code = 10111, message = "请求失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10112, message = "数据为空", response = ApiRestResponse.class),
	@ApiResponse(code = 10113, message = "参数类型不匹配", response = ApiRestResponse.class),
	@ApiResponse(code = 10114, message = "缺少矩阵变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10115, message = "缺少URI模板变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10116, message = "缺少Cookie变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10117, message = "缺少请求头", response = ApiRestResponse.class),
	@ApiResponse(code = 10118, message = "缺少参数", response = ApiRestResponse.class),
	@ApiResponse(code = 10119, message = "缺少请求对象", response = ApiRestResponse.class),
	@ApiResponse(code = 10120, message = "参数规则不满足", response = ApiRestResponse.class),
	@ApiResponse(code = 10121, message = "参数绑定错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10122, message = "参数解析错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10123, message = "参数验证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10201, message = "服务器：运行时异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10202, message = "服务器：空值异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10203, message = "服务器：数据类型转换异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10204, message = "服务器：IO异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10205, message = "服务器：未知方法异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10206, message = "服务器：非法参数异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10207, message = "服务器：数组越界异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10208, message = "服务器：网络异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping("/extras/sessions/")
@RequiresPermissions("session:*")
public class SessionStatusController extends BaseMapperController {
	
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
