/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "AuthzThirdpartyUserModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzThirdpartyUserModel extends BaseModel<AuthzThirdpartyUserModel> {

	/**
	 * 用户Id
	 */
	private String id;
	/**
	 * 用户唯一ID（员工信息表ID）
	 */
	private String uid;
	/**
	 * 用户唯一编码（工号）
	 */
	protected String ucode;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	private String secret;
	/**
	 * 用户别名（昵称）
	 */
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	private String avatar;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 性别：（male：男，female：女）
	 */
	private String gender;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 身份证号码
	 */
	private String idcard;
	/**
	 * 官方语言
	 */
	private String language;
	/**
	 * 所属城市
	 */
	private String city;
	/**
	 * 所属省份
	 */
	private String province;
	/**
	 * 所属区县
	 */
	private String country;
	/**
	 * 用户备注
	 */
	private String remark;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定）
	 */
	private String status;
	/**
	 * 初始化时间
	 */
	private String time24;

}
