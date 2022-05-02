package net.jeebiz.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.weixin.authc.WxMpLoginRequest;
import org.apache.shiro.spring.boot.weixin.token.WxMpAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByThirdParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;

/**
 * 微信（公共号、服务号）登录
 */
@Component
public class WxMpAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

	@Autowired
	private CommonProperteis commonProperteis;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.WEIXIN_MP;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(AuthenticationToken token) throws AuthenticationException {

		WxMpAuthenticationToken wxToken = (WxMpAuthenticationToken) token;
		WxMpLoginRequest loginRequest = (WxMpLoginRequest) wxToken.getPrincipal();
		WxOAuth2UserInfo userInfo = loginRequest.getUserInfo();

		AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
				// loginRequest.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// loginRequest.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(loginRequest.getOpenid())
				.nickname(userInfo.getNickname())
				.avatar(userInfo.getHeadImgUrl())
				.country(userInfo.getCountry())
				.province(userInfo.getProvince())
				.city(userInfo.getCity())
				.gender(userInfo.getSex())
				.channel(this.getChannel())
				.build();

		LoginByThirdParam param = new LoginByThirdParam();
		param.setOpenid(loginRequest.getOpenid());
		param.setUnionid(loginRequest.getUnionid());
		authBO.setParam(param);

		return authBO;

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
