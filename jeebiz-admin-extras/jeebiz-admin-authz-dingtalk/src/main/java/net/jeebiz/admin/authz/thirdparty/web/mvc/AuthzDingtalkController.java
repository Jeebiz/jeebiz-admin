/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.mvc;
import java.security.NoSuchAlgorithmException;

import org.apache.shiro.spring.boot.ShiroDingTalkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.thirdparty.web.vo.AuthzDingtalkConfigVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseMapperController;

/**
* 第三方账号登录：授权维护
*/
@Api(tags = "第三方账号登录：钉钉认证登录（Ok）")
@RestController
@RequestMapping("/authz/thirdpt/dingtalk/")
@Validated
public class AuthzDingtalkController extends BaseMapperController {

	@Autowired
    private ShiroDingTalkProperties properties;
   
	@ApiOperation(value = "钉钉登录第1步：获取配置", notes = "构造小程序前端配置初始化需要的参数")
	@GetMapping("config")
	public ApiRestResponse<AuthzDingtalkConfigVo> ddConfig() throws NoSuchAlgorithmException {
		
		AuthzDingtalkConfigVo configVo = new AuthzDingtalkConfigVo();
       configVo.setCorpId(properties.getCorpId());
       
       return ApiRestResponse.success(configVo);
	}
	
}
