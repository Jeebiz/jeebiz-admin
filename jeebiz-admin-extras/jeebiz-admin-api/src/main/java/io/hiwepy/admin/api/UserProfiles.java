/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.api;

import com.github.hiwepy.jwt.JwtClaims;

public class UserProfiles extends JwtClaims {

	/**
	 * 用户禁用状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public static final String DISABLED = "disabled";
	/**
	 * 用户别名（昵称）
	 */
	public static final String NICKNAME = "nickname";
	/**
	 * 用户头像：图片路径或图标样式
	 */
	public static final String AVATAR = "avatar";
	/**
	 * 用户封面
	 */
	public static final String COVER = "cover";
	/**
	 * 手机号码
	 */
	public static final String PHONE = "phone";
	/**
	 * 电子邮箱
	 */
	public static final String EMAIL = "email";
	/**
	 * 性别：（1：男，2：女）
	 */
	public static final String GENDER = "gender";
	/**
	 * 出生日期
	 */
	public static final String BIRTHDAY = "birthday";
	/**
	 * 身份证号码
	 */
	public static final String IDCARD = "idcard";
	/**
	 * 用户年龄
	 */
	public static final String AGE = "age";
	/**
	 *用户身高
	 */
	public static final String HEIGHT = "height";
	/**
	 *用户体重
	 */
	public static final String WEIGHT = "weight";
	/**
	 * 常驻省份编码
	 */
	public static final String PROVINCE = "province";
	/**
	 * 常驻城市编码
	 */
	public static final String CITY = "city";
	/**
	 * 所在城市区
	 */
	public static final String AREA = "area";
	/**
	 * 常驻省份编码
	 */
	public static final String GEO_PROVINCE = "geoProvince";
	/**
	 * 常驻城市编码
	 */
	public static final String GEO_CITY = "geoCity";
	/**
	 * 所在城市区
	 */
	public static final String GEO_AREA = "geoArea";
	/**
	 * 用户简介
	 */
	public static final String INTRO = "intro";
	/**
	 *用户是否VIP（1：是，0：否）
	 */
	public static final String VIP = "vip";
	/**
	 * 用户VIP类型（数据字典编码）
	 */
	public static final String VIP_TYPE = "vipType";
	/**
	 * 用户VIP到期日期
	 */
	public static final String VIP_EXPIRE = "vipExpire";
	/**
	 * 创建时间
	 */
	public static final String TIME24 = "time24";
	/**
	 * 用户客户端应用ID
	 */
	public static final String APP_ID = "appId";
	/**
	 * 用户客户端应用渠道
	 */
	public static final String APP_CHANNEL = "appChannel";
	/**
	 * 用户客户端应用版本
	 */
	public static final String APP_VERSION = "appVer";
	
}
