package net.jeebiz.admin.shadow.setup.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;

import net.jeebiz.admin.shadow.bo.AuthBO;

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
	AuthBO<T> initInfo(AuthenticationToken token) throws AuthenticationException;

    /**
     * 统一登陆
     * @param token
     * @return
     * @throws AuthenticationException
     */
	SimpleAuthenticationInfo login(AuthenticationToken token) throws AuthenticationException;

	SimpleAuthenticationInfo login(AuthBO<T> authBO) throws AuthenticationException;

    ShiroPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException;

    void afterLogin(AuthBO<T> authBO, String userId) throws AuthenticationException;

    void afterReg(AuthBO<T> authBO, String userId) throws AuthenticationException;

}
