package net.jeebiz.admin.shadow.setup.strategy;

import com.dingtalk.api.response.OapiUserGetResponse;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByThirdParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.dingtalk.authentication.DingTalkTmpCodeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 微信（公共号、服务号）登录
 */
@Component
public class DingtalkMpAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

	@Autowired
	private CommonProperteis commonProperteis;
	
    @Override
    public AuthChannel getChannel() {
        return AuthChannel.DINGTALK_MP;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {
    	
    	DingTalkTmpCodeAuthenticationToken ddToken = (DingTalkTmpCodeAuthenticationToken) token;
		OapiUserGetResponse userInfo = ddToken.getUserInfo();
		
		AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
				// ddToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// ddToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(ddToken.getOpenid())
				.nickname(userInfo.getNickname())
				.avatar(userInfo.getAvatar())
				.userCode(userInfo.getJobnumber())
				.phone(userInfo.getMobile())
				.email(userInfo.getEmail())
				.channel(this.getChannel())
				.build();
		
		LoginByThirdParam param = new LoginByThirdParam();
		param.setOpenid(ddToken.getOpenid());
		param.setUnionid(ddToken.getUnionid());
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
