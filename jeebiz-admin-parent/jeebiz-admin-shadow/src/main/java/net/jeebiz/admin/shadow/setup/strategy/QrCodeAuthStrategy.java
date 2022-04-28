package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.web.param.LoginByQrcodeParam;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import org.springframework.security.boot.qrcode.authentication.QrcodeAuthorizationToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

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
    public AuthBO<LoginByQrcodeParam> initInfo(Authentication token) throws AuthenticationException {
    	
    	QrcodeAuthorizationToken qrcodeToken = (QrcodeAuthorizationToken) token;
    	
		AuthBO<LoginByQrcodeParam> authBO = AuthBO.<LoginByQrcodeParam>builder()
				 
				.channel(this.getChannel())
				.build();
		 
		LoginByQrcodeParam param = new LoginByQrcodeParam();
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
