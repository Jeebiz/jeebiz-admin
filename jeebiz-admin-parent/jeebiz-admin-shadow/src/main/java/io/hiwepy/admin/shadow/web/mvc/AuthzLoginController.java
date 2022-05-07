/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.web.mvc;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.biz.authc.exception.IncorrectCaptchaException;
import org.apache.shiro.biz.authc.exception.InvalidAccountException;
import org.apache.shiro.biz.authc.exception.InvalidCaptchaException;
import org.apache.shiro.biz.authc.exception.NoneCaptchaException;
import org.apache.shiro.biz.authc.exception.NoneRoleException;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.apache.shiro.biz.web.filter.authc.PostLoginRequest;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.shadow.dao.entities.AuthzLoginModel;
import io.hiwepy.admin.shadow.service.IAuthzLoginService;
import io.hiwepy.admin.shadow.setup.Constants;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiIdempotent;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 权限管理：用户登录
 */
@Api(tags = "权限管理：用户登录（Ok）")
@Controller
@RequestMapping(value = "/authz/login/")
public class AuthzLoginController extends BaseController {

	private static final String DEFAULT_ERROr_key_ATTRIBUTE_name = "shiroLoginFailure";

	@Autowired
	private IAuthzLoginService authzLoginService;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "获取RSA公钥", notes = "用于登录功能获取RSA公钥")
	@ApiIdempotent(expireMillis = 20000)
	@GetMapping("publicKey")
	@ResponseBody
	public JSONObject getPublicKey(@ApiIgnore HttpServletRequest request) throws Exception {

		RSAPublicKey publicKey = getAuthzLoginService().genPublicKey(request);

		byte[] modulus = publicKey.getModulus().toByteArray();
		byte[] exponent = publicKey.getPublicExponent().toByteArray();

		JSONObject json = new JSONObject();
		json.put("modulus", Base64.encodeToString(modulus));
		json.put("exponent", Base64.encodeToString(exponent));
		//Thread.sleep(200000);
		return json;
	}

	@ApiOperation(value = "用户登录（无状态会话）", notes = "用户登录（无状态会话）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "login", value = "用户登录参数对象", dataType = "PostLoginRequest") 
	})
	@BusinessLog(module = Constants.AUTHZ_LOGIN, business = "用户登录", opt = BusinessType.LOGIN)
	@PostMapping("stateless")
	@ResponseBody
	public Object stateless(@RequestBody PostLoginRequest login ,@ApiIgnore HttpServletRequest request, @ApiIgnore Model model) {
		
		// 直接响应登录成功的提醒
		if (SubjectUtils.isAuthenticated()) {
			// 响应成功状态信息
			return ApiRestResponse.success("Login Success.");
		}

		// 响应成功状态信息
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", STATUS_FAIL);
			
		String ERROR_VALUE = (String) request.getAttribute(DEFAULT_ERROr_key_ATTRIBUTE_name);
		
		// 已经超出了重试限制，需要进行提醒
		if (StringUtils.equals(NoneCaptchaException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.captcha.required"));
			data.put("captcha", "required");
		}
		// 验证码错误
		else if (StringUtils.equals(IncorrectCaptchaException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.captcha.incorrect"));
			data.put("captcha", "required");
		}
		// 验证码失效
		else if (StringUtils.equals(InvalidCaptchaException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.captcha.invalid"));
			data.put("captcha", "required");
		}
		// 账号或密码为空
		else if (StringUtils.equals(UnknownAccountException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.account.empty"));
		}
		// 账户或密码错误
		else if (StringUtils.equals(InvalidAccountException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.account.invalid"));
		}
		// 账户没有启用
		else if (StringUtils.equals(DisabledAccountException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.account.disabled"));
		}
		// 该用户无所属角色，禁止登录
		else if (StringUtils.equals(NoneRoleException.class.getName(), ERROR_VALUE)) {
			data.put("message", getMessage("login.account.nonerole"));
		}
		else if(StringUtils.isNotEmpty(ERROR_VALUE)) {
        	data.put("message", "Authentication Failure.");
        }
		
		return data;
	}
	
	@ApiOperation(value = "切换角色", notes = "切换角色")
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleid", value = "角色id", dataType = "String") })
	@GetMapping("switchRole")
	public String switchRole(String roleid) {
		try {

			AuthzLoginModel principal = SubjectUtils.getPrincipal(AuthzLoginModel.class);
			Session session = SubjectUtils.getSession();
			
			
			//SubjectUtils.getSubject().runAs(principals);
			
			if (StringUtils.isNotBlank(roleid) && (!StringUtils.equals(roleid, principal.getRoleid()))) {
				/*// 切换当前的角色信息
				getUser().setJsdm(jsdm);

				// 刷新shiro缓存
				AccountRealm shiroRealm = ServiceFactory.getService(DefaultAccountRealm.class);
				shiroRealm.clearAuthorizationCache();*/
				// 刷新shiro缓存
				// 删除用户数据范围标识
				session.removeAttribute("");
			}
		} catch (Exception e) {
			logException(this, e);
		}
		return "redirect:/index";
	}

	public IAuthzLoginService getAuthzLoginService() {
		return authzLoginService;
	}

	public void setAuthzLoginService(IAuthzLoginService authzLoginService) {
		this.authzLoginService = authzLoginService;
	}

}
