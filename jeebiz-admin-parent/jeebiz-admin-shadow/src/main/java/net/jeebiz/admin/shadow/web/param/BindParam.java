package net.jeebiz.admin.shadow.web.param;

import lombok.Data;

@Data
public class BindParam {
    private Long userId;
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
     * 手机国家码
     */
    private Integer countryCode;
    /**
     * 注册渠道
     */
    private String channel;
    /**
     * 密码
     */
    private String password;
    /**
     * app
     */
    private Integer app;
}
