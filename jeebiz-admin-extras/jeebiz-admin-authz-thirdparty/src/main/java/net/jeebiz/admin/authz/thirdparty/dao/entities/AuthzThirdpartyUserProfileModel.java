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

@Alias(value = "AuthzThirdpartyUserProfileModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzThirdpartyUserProfileModel extends BaseModel<AuthzThirdpartyUserProfileModel> {
	
	/**
	 * 用户详情Id
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String uid;
	/**
	 * 用户别名（昵称）
	 */
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	private String avatar;
	/**
	 * 手机号码国家码
	 */
	private String countryCode;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 性别：（M：男，F：女）
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
	 * 用户年龄
	 */
	private int age;
	/**
	 *用户身高
	 */
	private int height;
	/**
	 *用户体重
	 */
	private int weight;
	/**
	 * 官方语言
	 */
	private String language;
	/**
	 * 用户备注
	 */
	private String intro;
	/**
	 * 个人照片（包含是否封面标记、序号、地址的JSON字符串）
	 */
	private String photos;
	/**
	 * 用户位置：常驻国家
	 */
	private String country;
	/**
	 * 用户位置：常驻省份
	 */
	private String province;
	/**
	 * 用户位置：常驻城市
	 */
	private String city;
	/**
	 * 用户位置：常驻区域
	 */
	private String area;
	/**
	 * 用户位置：常驻地经度
	 */
	private double longitude;
	/**
	 * 用户位置：常驻地纬度
	 */
	private double latitude;
	/**
	 *用户信息完成度
	 */
	private int degree;
	
}
