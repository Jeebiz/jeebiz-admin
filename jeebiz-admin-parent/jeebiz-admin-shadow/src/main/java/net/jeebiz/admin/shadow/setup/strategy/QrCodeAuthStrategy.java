package net.jeebiz.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.boot.qrcode.token.QrcodeAuthenticationToken;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.web.param.LoginByQrcodeParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;

/**
 * 扫码登录
 */
@Component
public class QrCodeAuthStrategy extends AbstractAuthStrategy<LoginByQrcodeParam> {
	
    @Override
    public AuthChannel getChannel() {
        return AuthChannel.QRCODE_SCAN;
    }

    @Override
    public AuthBO<LoginByQrcodeParam> initInfo(AuthenticationToken token) throws AuthenticationException {
    	
    	QrcodeAuthenticationToken qrcodeToken = (QrcodeAuthenticationToken) token;
    	
		AuthBO<LoginByQrcodeParam> authBO = AuthBO.<LoginByQrcodeParam>builder()
				.channel(this.getChannel())
				.account(qrcodeToken.getUuid())
				.build();
		
		LoginByQrcodeParam param = new LoginByQrcodeParam();
		param.setUuid(qrcodeToken.getUuid());
		authBO.setParam(param);
        
		return authBO;
        
    }
    
    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByQrcodeParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        //registerParam.setAccount(authBO.getParam().getOpenid());
        return registerParam;
    }

    @Override
    protected boolean useUserCode() {
        return true;
    }

}
