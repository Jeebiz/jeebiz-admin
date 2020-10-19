/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.thirdparty.setup.Constants;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzThirdpartyVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMpBindVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMpCode2AccessTokenVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMpConfigVo;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.RandomString;
import net.jeebiz.boot.api.web.BaseMapperController;

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
	public ApiRestResponse<AuthzWeixinMpCode2AccessTokenVo> authorize(@RequestParam("code") String code,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "lang", required = false) String lang) throws WxErrorException {
		
		AuthzWeixinMpCode2AccessTokenVo code2TokenVo = new AuthzWeixinMpCode2AccessTokenVo();
		
		WxMpOAuth2AccessToken oAuth2AccessToken = getWxMpService().getOAuth2Service().getAccessToken(code);
		
		code2TokenVo.setAccessToken(oAuth2AccessToken);
		code2TokenVo.setBind(getAuthzThirdpartyService().getCountByOpenId(oAuth2AccessToken.getOpenId()) > 0);
		code2TokenVo.setOpenid(oAuth2AccessToken.getOpenId());
		code2TokenVo.setUnionid(oAuth2AccessToken.getUnionId());
		code2TokenVo.setUserInfo(getWxMpService().getOAuth2Service().getUserInfo(oAuth2AccessToken, StringUtils.defaultString(lang, "zh_CN")));
		
        return ApiRestResponse.success(code2TokenVo);
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
	public ApiRestResponse<AuthzWeixinMpConfigVo> maConfig( @RequestParam("url")  String url) throws WxErrorException {
		
		AuthzWeixinMpConfigVo configVo = new AuthzWeixinMpConfigVo();
		
		// 创建调用jsapi时所需要的签名.
        WxJsapiSignature jsapiSignature = getWxMpService().createJsapiSignature(url);
        configVo.setAppId(jsapiSignature.getAppId());
        configVo.setNonceStr(jsapiSignature.getNonceStr());
        configVo.setSignature(jsapiSignature.getSignature());
        configVo.setTimestamp(jsapiSignature.getTimestamp());
        
        return ApiRestResponse.success(configVo);
	}
	
	@ApiOperation(value = "微信（公共号、服务号）登录第3步：绑定微信登录", notes = "微信登录绑定（用于当前登录账户的绑定，认证绑定请使用过滤器）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "bindVo", value = "绑定信息", dataType = "AuthzWeixinMpBindVo")
	})
	@PostMapping("binding")
	@ResponseBody
	public ApiRestResponse<AuthzThirdpartyVo> binding(@Valid @RequestBody AuthzWeixinMpBindVo bindVo) throws Exception { 
		bindVo.setType(ThirdpartyType.WXMP);
		AuthzThirdpartyVo model = getAuthzThirdpartyService().binding(bindVo);
		if(model != null) {
			return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("authz.thirdparty.binding.success"), model);
		}
		return success("authz.thirdparty.binding.fail");
	}
	
	@ApiOperation(value = "微信（公共号、服务号）登录第4步： 解除登录绑定", notes = "删除账号绑定的微信认证登录")
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除账号绑定的微信认证登录", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		int count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.WXMP, principal.getUserid());
		if(count == 0) {
			return fail("authz.thirdparty.unbind.not-found"); 
		}
		int total = getAuthzThirdpartyService().unbindByUid(ThirdpartyType.WXMP, principal.getUserid());
		if(total > 0) {
			return success("authz.thirdparty.unbind.success", total); 
		}
		return fail("authz.thirdparty.unbind.fail"); 
	}
	
    public IAuthzThirdpartyService getAuthzThirdpartyService() {
		return authzThirdpartyService;
	}
    
    public WxMpService getWxMpService() {
		return wxMpService;
	}
	
}
