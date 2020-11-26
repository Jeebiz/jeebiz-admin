/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.thirdparty.setup.Constants;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzThirdpartyVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMaBindVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMaCode2SessionVo;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzWeixinMaConfigVo;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;

/**
 * 第三方账号登录：微信（小程序）认证登录
 */
@Api(tags = "第三方账号登录：微信（小程序）认证登录")
@RestController
@RequestMapping("/authz/thirdpt/weixin/ma/")
public class AuthzWeixinMaController extends BaseMapperController {

	@Autowired
	protected WxMaService wxMaService;
	@Autowired
	protected IAuthzThirdpartyService authzThirdpartyService;
	
	@ApiOperation(value = "微信（小程序）登录第1步：获取Jsapi配置", notes = "构造小程序前端配置初始化需要的参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "url", required = true, value = "当前访问的URL", dataType = "String"),
	})
	@GetMapping("config")
	public ApiRestResponse<AuthzWeixinMaConfigVo> maConfig( @RequestParam("url")  String url) throws WxErrorException, NoSuchAlgorithmException {
		
		try {
			url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
		
        AuthzWeixinMaConfigVo configVo = new AuthzWeixinMaConfigVo();
        
        // 创建调用jsapi时所需要的签名.
        WxJsapiSignature jsapiSignature = getWxMaService().getJsapiService().createJsapiSignature(url);
        
        configVo.setAppId(jsapiSignature.getAppId());
        configVo.setNonceStr(jsapiSignature.getNonceStr());
        configVo.setSignature(jsapiSignature.getSignature());
        configVo.setTimestamp(jsapiSignature.getTimestamp());
        
        return ApiRestResponse.success(configVo);
	}
	
	@ApiOperation(value = "微信（小程序）登录第2步：jscode 换取 用户唯一标识 OpenID 和 会话密钥 session_key", notes = "调用 code2Session 接口，换取 用户唯一标识 OpenID 和 会话密钥 session_key")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "jscode", required = true, value = "临时登录凭证code", dataType = "String"),
	})
	@GetMapping("code2Session")
	@ResponseBody 
	public ApiRestResponse<AuthzWeixinMaCode2SessionVo> code2Session(@Valid @RequestParam("jscode") String jsCode) throws Exception { 
		// 调用 code2Session 接口，换取 用户唯一标识 OpenID 和 会话密钥 session_key
		WxMaJscode2SessionResult sessionResult = getWxMaService().jsCode2SessionInfo(jsCode);
		// 对象转换
		AuthzWeixinMaCode2SessionVo code2SessionVo = getBeanMapper().map(sessionResult, AuthzWeixinMaCode2SessionVo.class);
		// 检查是否已经绑定
		code2SessionVo.setBind(getAuthzThirdpartyService().getCountByOpenId(sessionResult.getOpenid()) > 0);
		// 返回结果
		return ApiRestResponse.success(code2SessionVo); 
	}
	
	@ApiOperation(value = "微信（小程序）登录第3步：绑定微信登录", notes = "微信小程序登录绑定（用于当前登录账户的绑定，认证绑定请使用过滤器）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "bindVo", value = "绑定信息", dataType = "AuthzWeixinMaBindVo")
	})
	@PostMapping("binding")
	@ResponseBody
	public ApiRestResponse<AuthzThirdpartyVo> binding(@Valid @RequestBody AuthzWeixinMaBindVo bindVo) throws Exception { 
		bindVo.setType(ThirdpartyType.WXMA);
		AuthzThirdpartyVo model = getAuthzThirdpartyService().binding(bindVo);
		if(model != null) {
			return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("authz.weixin.binding.success"), model);
		}
		return success("authz.weixin.binding.fail");
	}
	
	@ApiOperation(value = "微信（小程序）登录第4步： 解除登录绑定", notes = "删除账号绑定的微信认证登录")
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除账号绑定的微信认证登录", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		int count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.WXMA, principal.getUserid());
		if(count == 0) {
			return fail("authz.weixin.unbind.not-found"); 
		}
		int total = getAuthzThirdpartyService().unbindByUid(ThirdpartyType.WXMA, principal.getUserid());
		if(total > 0) {
			return success("authz.weixin.unbind.success", total); 
		}
		return fail("authz.weixin.unbind.fail"); 
	}
	
    public IAuthzThirdpartyService getAuthzThirdpartyService() {
		return authzThirdpartyService;
	}
    
    public WxMaService getWxMaService() {
		return wxMaService;
	}
	
}