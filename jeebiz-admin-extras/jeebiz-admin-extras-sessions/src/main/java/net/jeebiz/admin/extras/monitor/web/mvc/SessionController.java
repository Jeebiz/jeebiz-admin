/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.monitor.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.monitor.service.IOnlineSessionService;
import net.jeebiz.admin.extras.monitor.setup.Constants;
import net.jeebiz.admin.extras.monitor.web.dto.OnlineSessionDTO;
import net.jeebiz.admin.extras.monitor.web.param.SessionKickoutParam;
import net.jeebiz.admin.extras.monitor.web.param.SessionQueryParam;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * http://jinnianshilongnian.iteye.com/blog/2047643
 */
@Api(tags = "会话管理：在线会话管理")
@Validated
@RestController
@RequestMapping("/session/")
public class SessionController extends BaseApiController {

	@Autowired
	private IOnlineSessionService onlineSessionService;

	@ApiOperation(value = "在线会话列表", notes = "分页查询在线会话信息")
	@BusinessLog(module = Constants.EXTRAS_SESSIONS, business = "分页查询在线会话信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("session:list")
	public ApiRestResponse<List<OnlineSessionDTO>> list(@Valid @RequestBody SessionQueryParam queryParam,
			@RequestHeader(XHeaders.X_APP_ID) String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "zh") String languageCode,
			@ApiIgnore HttpServletRequest request) throws Exception {
		List<OnlineSessionDTO> sessions =  getOnlineSessionService().getActiveSessions(queryParam, appId, appChannel, appVersion, languageCode, request);
		return ApiRestResponse.success(sessions);
	}

	@ApiOperation(value = "会话强制离线", notes = "强制在线会话离线")
	@BusinessLog(module = Constants.EXTRAS_SESSIONS, business = "强制在线会话离线", opt = BusinessType.INSERT)
    @PostMapping("kickout")
	@RequiresPermissions("session:kickout")
    public ApiRestResponse<String> kickout(@Valid @RequestBody SessionKickoutParam kickoutParam,
			@RequestHeader(XHeaders.X_APP_ID) String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "zh") String languageCode,
			@ApiIgnore HttpServletRequest request) {
        try {
			getOnlineSessionService().kickout(kickoutParam, appId, appChannel, appVersion, languageCode, request);
			return success("session.kickout.success");
		} catch (Exception e) {
			return fail("session.kickout.fail");
		}
    }

	public IOnlineSessionService getOnlineSessionService() {
		return onlineSessionService;
	}

	public void setOnlineSessionService(IOnlineSessionService onlineSessionService) {
		this.onlineSessionService = onlineSessionService;
	}

}
