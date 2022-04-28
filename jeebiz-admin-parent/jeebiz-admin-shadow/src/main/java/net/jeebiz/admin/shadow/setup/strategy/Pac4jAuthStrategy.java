package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.api.UserProfiles;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByAccountParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.springframework.security.authentication.Pac4jAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 第三方认证登录
 */
@Component
public class Pac4jAuthStrategy extends AbstractAuthStrategy<LoginByAccountParam> {

	@Autowired
	private CommonProperteis commonProperteis;
	
    @Override
    public AuthChannel getChannel() {
        return AuthChannel.PAC4J;
    }

    @Override
    public AuthBO<LoginByAccountParam> initInfo(Authentication token) throws AuthenticationException {
    	
    	Pac4jAuthenticationToken pac4jToken = (Pac4jAuthenticationToken) token;
    	CommonProfile profile = pac4jToken.getProfile();
    	
		AuthBO<LoginByAccountParam> authBO = AuthBO.<LoginByAccountParam>builder()
				// wxToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// wxToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(profile.getId())
				.nickname(profile.getDisplayName())
				.avatar(profile.getAttribute(UserProfiles.AVATAR, String.class))
				.gender(profile.getAttribute(UserProfiles.GENDER, Integer.class))
				.channel(this.getChannel())
				.build();
		
		LoginByAccountParam param = new LoginByAccountParam();
		param.setAccount(profile.getId());
		authBO.setParam(param);
        
		return authBO;
        
    }

    @Override
    protected Boolean needReg() {
        return commonProperteis.isRegisterSwitch();
    }
    
    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByAccountParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getAccount());
        return registerParam;
    }

    @Override
    protected boolean useUserCode() {
        return true;
    }

}
