/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.login.web.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.apache.shiro.spring.boot.weixin.ShiroWxMpAuthcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hitool.core.lang3.RandomString;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import net.jeebiz.admin.authz.login.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.login.setup.Constants;
import net.jeebiz.admin.authz.login.setup.ThirdpartyType;
import net.jeebiz.admin.authz.login.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzWeixinMpBindDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzWeixinMpCode2AccessTokenDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzWeixinMpConfigDTO;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 第三方账号登录：微信（公共号、服务号）认证登录
 * @author wandl
 */
@Api(tags = "第三方账号登录：微信（公共号、服务号）认证登录")
@Controller
@RequestMapping("/authz/thirdpt/weixin/mp/")
public class AuthzWeixinMpController extends BaseMapperController {

	protected RandomString randomString = new RandomString();

	@Autowired
	protected WxMpService wxMpService;
	@Autowired
	protected IAuthzThirdpartyService authzThirdpartyService;
	@Autowired
	protected ShiroWxMpAuthcProperties wxMpAuthcProperties;

	/**
	 * https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
	 * @return
	 */
	@ApiOperation(value = "微信（公共号、服务号）：网页授权", notes = "构造公共号/服务号第三方使用网站应用授权登录的url")
	@GetMapping("oauth2")
	public String oauth2() {
		String authorizeUrl = getWxMpService().getOAuth2Service().buildAuthorizationUrl(wxMpAuthcProperties.getRedirectUrl(), "snsapi_userinfo", randomString.nextString());
	    return "redirect:"+ authorizeUrl;
	}

	/**
	 * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Official_Accounts/official_account_website_authorization.html
	 * @param code
	 * @return
	 * @throws WxErrorException
	 */
	@ApiOperation(value = "微信（公共号、服务号）：通过code换取accesstoken", notes = "通过code换取accesstoken")
	@GetMapping("code2Token")
	@ResponseBody
	public ApiRestResponse<AuthzWeixinMpCode2AccessTokenDTO> authorize(@RequestParam("code") String code,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "lang", required = false) String lang) throws WxErrorException {

		AuthzWeixinMpCode2AccessTokenDTO code2TokenDTO = new AuthzWeixinMpCode2AccessTokenDTO();

		WxOAuth2AccessToken oAuth2AccessToken = getWxMpService().getOAuth2Service().getAccessToken(code);

		code2TokenDTO.setAccessToken(oAuth2AccessToken);
		code2TokenDTO.setBind(getAuthzThirdpartyService().getCountByOpenId(oAuth2AccessToken.getOpenId()) > 0);
		code2TokenDTO.setOpenid(oAuth2AccessToken.getOpenId());
		code2TokenDTO.setUnionid(oAuth2AccessToken.getUnionId());
		code2TokenDTO.setUserInfo(getWxMpService().getOAuth2Service().getUserInfo(oAuth2AccessToken, StringUtils.defaultString(lang, "zh_CN")));

        return ApiRestResponse.success(code2TokenDTO);
	}

	/*
	 * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
	 */
	@ApiOperation(value = "微信（开放平台）：网页授权", notes = "构造公共号/服务号第三方使用网站应用授权登录的url")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "redirect", required = true, value = "重定向的URI", dataType = "String")
	})
	@GetMapping("qrconnect")
	public String connectUrl(@RequestParam("redirect") String redirectURI) {
		try {
			redirectURI = URLDecoder.decode(redirectURI, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
        String qrConnectUrl = getWxMpService().buildQrConnectUrl(redirectURI, "snsapi_userinfo", randomString.nextString());
	    return "redirect:"+ qrConnectUrl;
	}

	/**
	 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/One-time_subscription_info.html
	 * @return
	 */
	@ApiOperation(value = "微信（公共号、服务号）：模板消息订阅授权", notes = "开发者可以通过一次性订阅消息授权让微信用户授权第三方移动应用（接入说明）或公众号，获得发送一次订阅消息给到授权微信用户的机会。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "redirect", required = true, value = "重定向的URI", dataType = "String")
	})
	@GetMapping("subscribe")
	public String subscribeUrl(@RequestParam("redirect") String redirectURI) {
        String subscribeUrl = getWxMpService().getSubscribeMsgService().subscribeMsgAuthorizationUrl(redirectURI, 100, randomString.nextString());
	    return "redirect:"+ subscribeUrl;
	}

	@ApiOperation(value = "微信（公共号、服务号）登录第2步：获取配置", notes = "构造公共号/服务号前端配置初始化需要的参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "url", required = true, value = "当前访问的URL", dataType = "String")
	})
	@GetMapping("config")
	@ResponseBody
	public ApiRestResponse<AuthzWeixinMpConfigDTO> maConfig( @RequestParam("url")  String url) throws WxErrorException {

		AuthzWeixinMpConfigDTO configDTO = new AuthzWeixinMpConfigDTO();

		// 创建调用jsapi时所需要的签名.
        WxJsapiSignature jsapiSignature = getWxMpService().createJsapiSignature(url);
        configDTO.setAppId(jsapiSignature.getAppId());
        configDTO.setNonceStr(jsapiSignature.getNonceStr());
        configDTO.setSignature(jsapiSignature.getSignature());
        configDTO.setTimestamp(jsapiSignature.getTimestamp());

        return ApiRestResponse.success(configDTO);
	}

	@ApiOperation(value = "微信（公共号、服务号）登录第3步：绑定微信登录", notes = "微信登录绑定（用于当前登录账户的绑定，认证绑定请使用过滤器）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "bindDTO", value = "绑定信息", dataType = "AuthzWeixinMpBindDTO")
	})
	@PostMapping("binding")
	@ResponseBody
	public ApiRestResponse<AuthzThirdpartyDTO> binding(@Valid @RequestBody AuthzWeixinMpBindDTO bindDTO, @ApiIgnore HttpServletRequest request) throws Exception {
		bindDTO.setType(ThirdpartyType.WXMP);
		AuthzThirdpartyDTO model = getAuthzThirdpartyService().binding(request, bindDTO);
		if(model != null) {
			return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("authz.thirdparty.binding.success"), model);
		}
		return success("authz.weixin.binding.fail");
	}

	@ApiOperation(value = "微信（公共号、服务号）登录第4步： 解除登录绑定", notes = "删除账号绑定的微信认证登录")
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除账号绑定的微信认证登录", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		Long count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.WXMP, principal.getUserid());
		if(count == 0) {
			return fail("authz.weixin.unbind.not-found");
		}
		int total = getAuthzThirdpartyService().unbindByUid(ThirdpartyType.WXMP, principal.getUserid());
		if(total > 0) {
			return success("authz.weixin.unbind.success", total);
		}
		return fail("authz.weixin.unbind.fail");
	}

    public IAuthzThirdpartyService getAuthzThirdpartyService() {
		return authzThirdpartyService;
	}

    public WxMpService getWxMpService() {
		return wxMpService;
	}

}
