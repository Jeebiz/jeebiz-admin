/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.spring.boot.DingTalkProperties;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.thirdparty.setup.Constants;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzDingtalkBindDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzDingtalkCode2SessionDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzDingtalkConfigDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 第三方账号登录：授权维护
 */
@Api(tags = "第三方账号登录：钉钉认证登录（Ok）")
@RestController
@RequestMapping("/authz/thirdpt/dingtalk/ma/")
@Validated
public class AuthzDingtalkMaController extends BaseMapperController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected DingTalkTemplate dingTalkTemplate;
	@Autowired
	protected DingTalkProperties properties;
	@Autowired
	protected IAuthzThirdpartyService authzThirdpartyService;
	
	@ApiOperation(value = "钉钉登录第1步：获取配置", notes = "构造小程序前端配置初始化需要的参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "key", required = true, value = "临时登录凭证code", dataType = "String"),
		@ApiImplicitParam(name = "url", required = true, value = "当前访问的URL", dataType = "String")
	})
	@GetMapping("config")
	public ApiRestResponse<AuthzDingtalkConfigDTO> ddConfig(@Valid @RequestParam("key") String appKey, @RequestParam("url")  String url) {
		
		try {
			
			String appSecret = dingTalkTemplate.getAppSecret(appKey);
			// 获取access_token
			String accessToken = dingTalkTemplate.getAccessToken(appKey, appSecret);
			
			AuthzDingtalkConfigDTO configDTO = new AuthzDingtalkConfigDTO();
			
			dingTalkTemplate.opsForJsapi().createSignature(url, appKey, accessToken);
			
			configDTO.setCorpId(properties.getCorpId());
			
			return ApiRestResponse.success(configDTO);
		} catch (ApiException e) {
			e.printStackTrace();
			return ApiRestResponse.of(e.getErrCode(), e.getErrMsg());
		}
	}
	
	@ApiOperation(value = "钉钉（小程序）登录第2步：code 换取 用户唯一标识 OpenID 和 会话密钥 session_key", notes = "调用 code2Session 接口，换取 用户唯一标识 OpenID 和 会话密钥 session_key")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", required = true, value = "临时登录凭证code", dataType = "String"),
	})
	@GetMapping("code2Session")
	@ResponseBody 
	public ApiRestResponse<AuthzDingtalkCode2SessionDTO> code2Session(@Valid @RequestParam("key") String appKey, @RequestParam("code") String code) throws Exception {
		
		String appSecret = dingTalkTemplate.getAppSecret(appKey);
		// 获取access_token
		String accessToken = dingTalkTemplate.getAccessToken(appKey, appSecret);
		
		OapiUserGetuserinfoResponse response = dingTalkTemplate.opsForAccount().getUserinfoBycode(code, accessToken);
		
		/*{
		    "userid": "****",
		    "sys_level": 1,
		    "errmsg": "ok",
		    "is_sys": true,
		    "errcode": 0
		}*/
		if (logger.isDebugEnabled()) {
			logger.debug(response.getBody());
		}
		
		if(!response.isSuccess()) {
			return ApiRestResponse.of(response.getErrorCode(), response.getErrmsg());
		}
		
		OapiUserGetResponse userInfoResponse = dingTalkTemplate.opsForAccount().getUserByUserid(response.getUserid(), accessToken);
		if(!userInfoResponse.isSuccess()) {
			logger.debug(response.getBody());
			return ApiRestResponse.of(response.getErrorCode(), response.getErrmsg());
		}
		
		AuthzDingtalkCode2SessionDTO code2SessionDTO = new AuthzDingtalkCode2SessionDTO();
		code2SessionDTO.setUserid(response.getUserid());
		code2SessionDTO.setDeviceId(response.getDeviceId());
		code2SessionDTO.setUserInfo(userInfoResponse);
		code2SessionDTO.setUnionid(userInfoResponse.getUnionid());
		code2SessionDTO.setOpenid(userInfoResponse.getOpenId());
		code2SessionDTO.setBind(getAuthzThirdpartyService().getCountByOpenId(userInfoResponse.getOpenId()) > 0);

		return ApiRestResponse.success(code2SessionDTO); 
	}
	
	@ApiOperation(value = "钉钉（小程序）登录第3步：绑定钉钉登录", notes = "钉钉小程序登录绑定（用于当前登录账户的绑定，认证绑定请使用过滤器）")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "bindDTO", value = "绑定信息", dataType = "AuthzWeixinMaBindDTO")
	})
	@PostMapping("binding")
	@ResponseBody
	public ApiRestResponse<AuthzThirdpartyDTO> binding(@Valid @RequestBody AuthzDingtalkBindDTO bindDTO, @ApiIgnore HttpServletRequest request) throws Exception { 
		AuthzThirdpartyDTO model = getAuthzThirdpartyService().binding(request, bindDTO);
		if(model != null) {
			return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("authz.dingtalk.binding.success"), model);
		}
		return success("authz.dingtalk.binding.fail");
	}
	
	@ApiOperation(value = "钉钉（小程序）登录第4步： 解除登录绑定", notes = "删除账号绑定的钉钉认证登录")
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除账号绑定的钉钉认证登录", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		int count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.WXMA, principal.getUserid());
		if(count == 0) {
			return fail("authz.dingtalk.unbind.not-found"); 
		}
		int total = getAuthzThirdpartyService().unbindByUid(ThirdpartyType.WXMA, principal.getUserid());
		if(total > 0) {
			return success("authz.dingtalk.unbind.success", total); 
		}
		return fail("authz.dingtalk.unbind.fail"); 
	}
	
	public DingTalkTemplate getDingTalkTemplate() {
		return dingTalkTemplate;
	}
	
	public IAuthzThirdpartyService getAuthzThirdpartyService() {
		return authzThirdpartyService;
	}
	
}
