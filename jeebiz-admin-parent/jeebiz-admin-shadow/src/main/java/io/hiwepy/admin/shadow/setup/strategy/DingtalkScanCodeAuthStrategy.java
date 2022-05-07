package io.hiwepy.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkScanCodeLoginRequest;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkScanCodeAuthenticationToken;
import org.apache.shiro.spring.boot.jwt.JwtPayloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;

import io.hiwepy.admin.shadow.bo.AuthBO;
import io.hiwepy.admin.shadow.setup.CommonProperteis;
import io.hiwepy.admin.shadow.web.param.LoginByThirdParam;
import io.hiwepy.admin.shadow.web.param.RegisterParam;

/**
 * 钉钉扫码登录第三方网站
 * https://open.dingtalk.com/document/orgapp-server/scan-qr-code-to-log-on-to-third-party-websites
 */
@Component
public class DingtalkScanCodeAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

	@Autowired
	private CommonProperteis commonProperteis;
    @Autowired
    private JwtPayloadRepository jwtPayloadRepository;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.DINGTALK_SCAN_CODE;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(AuthenticationToken token) throws AuthenticationException {

        DingTalkScanCodeAuthenticationToken ddToken = (DingTalkScanCodeAuthenticationToken) token;
		OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo = ddToken.getUserInfo();

		
		AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
				// ddToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// ddToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(ddToken.getOpenid())
				.nickname(userInfo.getNick())
				.channel(this.getChannel())
				.build();

		LoginByThirdParam param = new LoginByThirdParam();
		param.setOpenid(ddToken.getOpenid());
		param.setUnionid(ddToken.getUnionid());
        DingTalkScanCodeLoginRequest loginRequest = (DingTalkScanCodeLoginRequest) ddToken.getPrincipal();
        param.setToken(loginRequest.getToken());
		authBO.setParam(param);
		return authBO;

    }

    @Override
    protected Boolean needReg(AuthBO<LoginByThirdParam> authBO) {
        String token = authBO.getParam().getToken();
        return commonProperteis.isRegisterSwitch()|| StringUtils.hasText(token);
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByThirdParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getOpenid());
        String token = authBO.getParam().getToken();
        if(StringUtils.hasText(token)){
            String userId = jwtPayloadRepository.getPayload(authBO.getParam().getToken(),false).getUid();
            registerParam.setUserId(userId);
        }
        return registerParam;
    }

    @Override
    protected boolean useUserCode() {
        return true;
    }

}
