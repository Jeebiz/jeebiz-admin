package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.shadow.bo.AuthBO;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 登录
 */
public interface AuthStrategy<T> {

    /**
     * 返回登陆所用渠道
     * @return
     */
	AuthChannel getChannel();

    /**
     * 获取账号信息
     * @param token
     * @return
     */
	AuthBO<T> initInfo(Authentication token) throws AuthenticationException;

    /**
     * 统一登陆
     * @param token
     * @return
     * @throws AuthenticationException
     */
    SecurityPrincipal login(Authentication token) throws AuthenticationException;
    
    SecurityPrincipal login(AuthBO<T> authBO) throws AuthenticationException;

    SecurityPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException;

    void afterLogin(AuthBO<T> authBO, Long userId) throws AuthenticationException;

    void afterReg(AuthBO<T> authBO, Long userId) throws AuthenticationException;
    
}
