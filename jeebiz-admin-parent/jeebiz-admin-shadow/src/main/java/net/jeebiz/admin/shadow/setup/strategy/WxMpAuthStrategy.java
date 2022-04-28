package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByThirdParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.weixin.authentication.WxMpAuthenticationToken;
import org.springframework.security.boot.weixin.authentication.WxMpLoginRequest;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

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
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {
    	
		WxMpAuthenticationToken wxToken = (WxMpAuthenticationToken) token;
		WxOAuth2UserInfo userInfo = wxToken.getUserInfo();
		
		AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
				// wxToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// wxToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(wxToken.getOpenid())
				.nickname(userInfo.getNickname())
				.avatar(userInfo.getHeadImgUrl())
				.country(userInfo.getCountry())
				.province(userInfo.getProvince())
				.city(userInfo.getCity())
				.gender(userInfo.getSex())
				.channel(this.getChannel())
				.build();
		
		WxMpLoginRequest loginRequest = (WxMpLoginRequest) wxToken.getPrincipal();
		
		// 用户名不为空，表示交互上绑定已知的用户，否则自动创建用户
		if(StringUtils.isNotBlank(loginRequest.getUsername())) {
			//userModel.setAlias(userInfo.getNickname());
			//userModel.setAvatar();
			//userModel.setPhone();
			
		}
		
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
