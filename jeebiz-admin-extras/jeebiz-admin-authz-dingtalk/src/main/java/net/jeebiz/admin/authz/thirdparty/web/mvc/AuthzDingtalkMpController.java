/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.login.web.mvc;

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

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse.UserInfo;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.dingtalk.spring.boot.DingTalkProperties;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.dingtalk.spring.boot.bean.JsapiTicketSignature;
import com.taobao.api.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.login.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.login.setup.Constants;
import net.jeebiz.admin.authz.login.setup.ThirdpartyType;
import net.jeebiz.admin.authz.login.web.dto.AuthzDingtalkBindDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzDingtalkCode2SessionDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzDingtalkMpConfigDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzThirdpartyDTO;
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
@RequestMapping("/authz/thirdpt/dingtalk/mp/")
@Validated
public class AuthzDingtalkMpController extends BaseMapperController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected DingTalkTemplate dingTalkTemplate;
	@Autowired
	protected DingTalkProperties properties;
	@Autowired
	protected IAuthzThirdpartyService authzThirdpartyService;

	@ApiOperation(value = "钉钉（微应用）：获取H5微应用jsapi权限配置参数", notes = "获取H5微应用jsapi权限配置参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "appId", required = true, value = "期望获取配置的微应用appId或AgentId（解决多应用情况）", dataType = "String"),
		@ApiImplicitParam(name = "url", required = true, value = "当前网页的URL，不包含#及其后面部分", dataType = "String")
	})
	@GetMapping("config")
	public ApiRestResponse<AuthzDingtalkMpConfigDTO> ddConfig(@Valid @RequestParam("appId") String appId,
			@RequestParam("url")  String url) {

		try {

			String appSecret = dingTalkTemplate.getAppSecret(appId);
			// 获取access_token
			String accessToken = dingTalkTemplate.getAccessToken(appId, appSecret);

			AuthzDingtalkMpConfigDTO configDTO = new AuthzDingtalkMpConfigDTO();

			// 创建调用jsapi时所需要的签名.

	        JsapiTicketSignature jsapiSignature = dingTalkTemplate.opsForJsapi().createSignature(url, appId, accessToken);
	        configDTO.setAgentId(jsapiSignature.getAgentId());
	        configDTO.setNonceStr(jsapiSignature.getNonceStr());
	        configDTO.setSignature(jsapiSignature.getSignature());
	        configDTO.setTimestamp(jsapiSignature.getTimestamp());
	        configDTO.setUrl(jsapiSignature.getUrl());
			configDTO.setCorpId(properties.getCorpId());

	        return ApiRestResponse.success(configDTO);
		} catch (ApiException e) {
			e.printStackTrace();
			return ApiRestResponse.of(e.getErrCode(), e.getErrMsg());
		}
	}

	@ApiOperation(value = "钉钉（小程序）登录第2步：code 换取 用户唯一标识 Openid 和 会话密钥 session_key", notes = "调用 code2Session 接口，换取 用户唯一标识 Openid 和 会话密钥 session_key")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginTmpCode", required = true, value = "临时登录凭证loginTmpCode", dataType = "String"),
	})
	@GetMapping("code2Session")
	@ResponseBody
	public ApiRestResponse<AuthzDingtalkCode2SessionDTO> code2Session(@Valid @RequestParam("key") String appKey, @RequestParam("loginTmpCode") String loginTmpCode) throws Exception {

		String appSecret = dingTalkTemplate.getAppSecret(appKey);
		// 获取access_token
		String accessToken = dingTalkTemplate.getAccessToken(appKey, appSecret);

		// 第三方应用钉钉扫码登录：通过临时授权码Code获取用户信息，临时授权码只能使用一次
		OapiSnsGetuserinfoBycodeResponse response = dingTalkTemplate.opsForSns().getUserinfoByTmpCode(loginTmpCode, appKey, appSecret);
		/*{
		    "errcode": 0,
		    "errmsg": "ok",
		    "user_info": {
		        "nick": "张三",
		        "openid": "liSii8KCxxxxx",
		        "unionid": "7Huu46kk"
		    }
		}*/
		if (logger.isDebugEnabled()) {
			logger.debug(response.getBody());
		}

		if(!response.isSuccess()) {
			return ApiRestResponse.of(response.getErrorCode(), response.getErrmsg());
		}

		UserInfo userInfo = response.getUserInfo();

		// 根据unionid获取userid
		OapiUserGetUseridByUnionidResponse unionidResponse = dingTalkTemplate.opsForAccount().getUseridByUnionid(userInfo.getUnionid(), accessToken);
		if(!unionidResponse.isSuccess()) {
			logger.error(unionidResponse.getBody());
			return ApiRestResponse.of(unionidResponse.getErrorCode(), unionidResponse.getErrmsg());
		}

		// 根据UserId 获取用户信息
		OapiUserGetResponse userInfoResponse = dingTalkTemplate.opsForAccount().getUserByUserid(unionidResponse.getUserid(), accessToken);
		if(!userInfoResponse.isSuccess()) {
			logger.error(userInfoResponse.getBody());
			return ApiRestResponse.of(userInfoResponse.getErrorCode(), userInfoResponse.getErrmsg());
		}

		AuthzDingtalkCode2SessionDTO code2SessionDTO = new AuthzDingtalkCode2SessionDTO();
		code2SessionDTO.setUnionid(userInfo.getUnionid());
		code2SessionDTO.setOpenid(userInfo.getOpenid());
		code2SessionDTO.setUserid(userInfoResponse.getUserid());
		code2SessionDTO.setUserInfo(userInfoResponse);
		code2SessionDTO.setBind(getAuthzThirdpartyService().getCountByOpenId(userInfo.getOpenid()) > 0);

		return ApiRestResponse.success(code2SessionDTO);
	}

	@ApiOperation(value = "钉钉（微应用）登录第3步：绑定钉钉登录", notes = "钉钉小程序登录绑定（用于当前登录账户的绑定，认证绑定请使用过滤器）")
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

	@ApiOperation(value = "钉钉（微应用）登录第4步： 解除登录绑定", notes = "删除账号绑定的钉钉认证登录")
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除账号绑定的钉钉认证登录", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind() throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		Long count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.WXMA, principal.getUserid());
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
