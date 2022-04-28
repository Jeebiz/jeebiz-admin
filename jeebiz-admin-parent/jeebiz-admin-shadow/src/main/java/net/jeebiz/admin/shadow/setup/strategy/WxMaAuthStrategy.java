package net.jeebiz.admin.shadow.setup.strategy;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByThirdParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import org.apache.shiro.spring.boot.weixin.authc.WxMaLoginRequest;
import org.apache.shiro.spring.boot.weixin.token.WxMaAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 微信（小程序）登录
 */
@Component
public class WxMaAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

	@Autowired
	private CommonProperteis commonProperteis;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.WEIXIN_MA;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(AuthenticationToken token) throws AuthenticationException {

		WxMaAuthenticationToken wxToken = (WxMaAuthenticationToken) token;
    	WxMaLoginRequest loginRequest = (WxMaLoginRequest) wxToken.getPrincipal();
		// 获取用户手机号信息
		WxMaPhoneNumberInfo phoneNumberInfo = wxToken.getPhoneNumberInfo();
		// 获取用户信息
		WxMaUserInfo userInfo = loginRequest.getUserInfo();

		AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
				// wxToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// wxToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(wxToken.getOpenid())
				.nickname(userInfo.getNickName())
				.avatar(userInfo.getAvatarUrl())
				.country(userInfo.getCountry())
				.province(userInfo.getProvince())
				.phone(phoneNumberInfo != null ? phoneNumberInfo.getPhoneNumber() : "")
				.city(userInfo.getCity())
				.gender(userInfo.getGender() == "1" ? 1: 0)
				.channel(this.getChannel())
				.build();

		LoginByThirdParam param = new LoginByThirdParam();
		param.setOpenid(wxToken.getOpenid());
		param.setUnionid(wxToken.getUnionid());
		authBO.setParam(param);

		return authBO;

    }

    @Override
    protected Boolean needReg() {
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
