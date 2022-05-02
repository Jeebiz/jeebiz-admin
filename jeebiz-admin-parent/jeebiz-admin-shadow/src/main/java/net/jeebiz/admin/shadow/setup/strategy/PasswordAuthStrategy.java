package net.jeebiz.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.jwt.token.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.admin.shadow.web.param.LoginByPasswordParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;

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
    public AuthBO<LoginByPasswordParam> initInfo(AuthenticationToken token) throws AuthenticationException {

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
    protected Boolean needReg(AuthBO<LoginByPasswordParam> authBO) {
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
