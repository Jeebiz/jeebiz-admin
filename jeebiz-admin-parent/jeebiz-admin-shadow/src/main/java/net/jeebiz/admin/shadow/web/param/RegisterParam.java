package net.jeebiz.admin.shadow.web.param;

import lombok.Data;
import net.jeebiz.admin.shadow.setup.strategy.AuthChannel;

/**
 */
@Data
public class RegisterParam {
	/**
     * 注册账号
     */
    private String account;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 注册渠道(实际上是账号注册方式)
     */
    private AuthChannel channel;
    /**
     * 密码
     */
    private String password;
    /**
     * 语言码
     */
    private String lang;

    /**
     * 用户id
     */
    private String userId;
}
