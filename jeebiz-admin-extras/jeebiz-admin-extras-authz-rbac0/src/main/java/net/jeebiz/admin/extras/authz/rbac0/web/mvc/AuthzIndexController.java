/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.utils.HttpStatus;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 权限管理：系统首页
 */
@Api(tags = "权限管理：系统首页（Ok）")
@ApiResponses({ 
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class)
})
@RestController
public class AuthzIndexController {
	
	/**
	 * JWT是否过期的访问方法：处理逻辑已经在过滤器中实现，这里只负责输出有效情况下的信息
	 * @param request
	 * @return
	 */
	@ApiIgnore
	@RequestMapping(value = "expiry")
	@ResponseBody
	public Object expiry(HttpServletRequest request) {
		// 响应成功状态信息
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", "success");
		data.put("message", "JWT within validity period.");
		return data;
	}
 
}
