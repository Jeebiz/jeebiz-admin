package io.hiwepy.admin.authz.rbac0.setup.strategy;

public interface AuthStrategy {

    /**
     * 获取发送渠道
     * @return
     */
    AuthChannel getChannel();

}
