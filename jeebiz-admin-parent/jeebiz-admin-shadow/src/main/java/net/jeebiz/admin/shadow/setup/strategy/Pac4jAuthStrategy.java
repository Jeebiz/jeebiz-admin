package net.jeebiz.admin.shadow.setup.strategy;

import java.util.Optional;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.pac4j.core.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.buji.pac4j.token.Pac4jToken;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByAccountParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;

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
    public AuthBO<LoginByAccountParam> initInfo(AuthenticationToken token) throws AuthenticationException {

    	Pac4jToken pac4jToken = (Pac4jToken) token;
    	
    	Optional<UserProfile> optional = (Optional<UserProfile>) pac4jToken.getPrincipal();
    	if(!optional.isPresent()) {
    		
    	}
    	
    	
    	UserProfile profile = optional.get();

		AuthBO<LoginByAccountParam> authBO = AuthBO.<LoginByAccountParam>builder()
				// wxToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
				// wxToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
				.account(profile.getId())
				//.avatar(String.valueOf(profile.getAttribute(UserProfiles.AVATAR)))
				//.gender(Integer.parseInt(profile.getAttribute(UserProfiles.GENDER).toString()))
				.channel(this.getChannel())
				.build();

		LoginByAccountParam param = new LoginByAccountParam();
		param.setAccount(profile.getId());
		authBO.setParam(param);

		return authBO;

    }

    @Override
    protected Boolean needReg(AuthBO<LoginByAccountParam> authBO) {
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
