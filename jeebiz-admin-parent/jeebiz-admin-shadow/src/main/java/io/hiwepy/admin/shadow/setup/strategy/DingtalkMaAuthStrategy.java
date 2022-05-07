package io.hiwepy.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkMaLoginRequest;
import org.apache.shiro.spring.boot.dingtalk.exception.DingTalkAuthenticationServiceException;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkMaAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;

import io.hiwepy.admin.shadow.bo.AuthBO;
import io.hiwepy.admin.shadow.setup.CommonProperteis;
import io.hiwepy.admin.shadow.web.param.LoginByThirdParam;
import io.hiwepy.admin.shadow.web.param.RegisterParam;

/**
 * 微信（小程序）登录
 */
@Component
public class DingtalkMaAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

	@Autowired
	private CommonProperteis commonProperteis;
    @Autowired
    private DingTalkTemplate dingTalkTemplate;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.DINGTALK_MA;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(AuthenticationToken token) throws AuthenticationException {

    	DingTalkMaAuthenticationToken ddToken = (DingTalkMaAuthenticationToken) token;
        DingTalkMaLoginRequest loginRequest = (DingTalkMaLoginRequest) ddToken.getPrincipal();

        try {

            OapiUserGetuserinfoResponse response = dingTalkTemplate.opsForUser().getUserinfoByCode(loginRequest.getAuthCode(), loginRequest.getAccessToken());
            if(!response.isSuccess()) {
                throw new DingTalkAuthenticationServiceException(response.getErrmsg());
            }
            // 根据UserId 获取用户信息
            OapiUserGetResponse userInfoResponse = dingTalkTemplate.opsForAccount().getUserByUserid(response.getUserid(), loginRequest.getAccessToken());
            if(!userInfoResponse.isSuccess()) {
                throw new DingTalkAuthenticationServiceException(userInfoResponse.getErrmsg());
            }

            AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
                    // userInfoResponse.getOpenId(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                    // userInfoResponse.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                    .account(response.getUserid())
                    .nickname(userInfoResponse.getNickname())
                    .avatar(userInfoResponse.getAvatar())
                    .userCode(userInfoResponse.getJobnumber())
                    .phone(userInfoResponse.getMobile())
                    .email(userInfoResponse.getEmail())
                    .channel(this.getChannel())
                    .build();

            LoginByThirdParam param = new LoginByThirdParam();
            param.setOpenid(userInfoResponse.getOpenId());
            param.setUnionid(userInfoResponse.getUnionid());
            authBO.setParam(param);

            return authBO;

        } catch (ApiException e) {
            throw new DingTalkAuthenticationServiceException(e.getErrMsg(), e);
        }

    }

    @Override
    protected Boolean needReg(AuthBO<LoginByThirdParam> authBO) {
        return commonProperteis.isRegisterSwitch();
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByThirdParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getOpenid());
        return registerParam;
    }

    @Override
    protected boolean useUserCode() {
        return true;
    }

}
