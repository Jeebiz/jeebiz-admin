/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;

@Alias(value = "AuthzThirdpartyModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzThirdpartyModel extends PaginationModel<AuthzThirdpartyModel> {

	/**
	 * 用户Id
	 */
	private String uid;
	/**
	 * 用户名称
	 */
	private String uname;
	/**
	 * 第三方账号登录表ID
	 */
	private String id;
	/**
	 * 第三方账号类型
	 * @see ThirdpartyType
	 */
	private ThirdpartyType type;
	/**
	 * 第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）
	 */
	private String unionid;
	/**
	 * 第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）
	 */
	private String openid;
	/**
	 * 当前认证对接平台开发者账号ID（通常指与第三方平台进行认证对接的开发者账号的唯一ID）
	 */
	private String devid;
	/**
	 * 第三方认证账号扩展信息
	 */
	private String rawdata;
	/**
	 * 第三方账号绑定时间
	 */
	private String time24;
}
