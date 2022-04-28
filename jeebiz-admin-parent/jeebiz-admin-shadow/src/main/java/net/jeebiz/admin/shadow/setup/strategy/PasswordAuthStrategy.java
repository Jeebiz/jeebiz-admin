package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByPasswordParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.jwt.authentication.JwtAuthenticationToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 账号密码登录策略
 */
@Component
public class PasswordAuthStrategy extends AbstractAuthStrategy<LoginByPasswordParam> {

	@Autowired
	private CommonProperteis commonProperteis;
	
    @Override
    public AuthChannel getChannel() {
        return AuthChannel.PASSWORD;
    }

    @Override
    public AuthBO<LoginByPasswordParam> initInfo(Authentication token) throws AuthenticationException {

        JwtAuthenticationToken upToken = (JwtAuthenticationToken) token;
    	
    	AuthBO<LoginByPasswordParam> authBO = AuthBO.<LoginByPasswordParam>builder()
				.account(upToken.getPrincipal().toString())
				.password(upToken.getCredentials().toString())
				.channel(this.getChannel())
				.build();
		 
    	LoginByPasswordParam param = new LoginByPasswordParam();
    	param.setAccount(upToken.getPrincipal().toString());
    	param.setPassword(upToken.getCredentials().toString());
		authBO.setParam(param);
        
		return authBO;
        
    }

    @Override
    protected Boolean needReg() {
        return commonProperteis.isRegisterSwitch();
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByPasswordParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getAccount());
        return registerParam;
    }

    @Override
    protected boolean useUserCode() {
        return true;
    }
}
