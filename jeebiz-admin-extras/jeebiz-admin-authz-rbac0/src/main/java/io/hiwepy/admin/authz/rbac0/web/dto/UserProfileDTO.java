/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "UserProfileDTO", description = "用户描述信息DTO")
@Getter
@Setter
@ToString
public class UserProfileDTO {
	
	/**
	 * 用户详情Id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户详情Id")
	private String id;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "nickname", dataType = "String", value = "用户昵称")
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 手机号码国家码
	 */
	@ApiModelProperty(name = "countryCode", dataType = "String", value = "手机号码国家码")
	private String countryCode;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", dataType = "String", value = "手机号码")
	private String phone;
	/**
	 * 性别：（M：男，F：女）
	 */
	@ApiModelProperty(name = "gender", dataType = "String", value = "性别：（M：男，F：女）")
	private String gender;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", dataType = "String", value = "电子邮箱")
	private String email;
	/**
	 * 出生日期
	 */
	@ApiModelProperty(name = "birthday", dataType = "String", value = "出生日期")
	private String birthday;
	/**
	 * 身份证号码
	 */
	@ApiModelProperty(name = "idcard", dataType = "String", value = "身份证号码")
	private String idcard;
	/**
	 * 用户年龄
	 */
	@ApiModelProperty(name = "age", dataType = "Integer", value = "用户年龄")
	private int age;
	/**
	 *用户身高
	 */
	@ApiModelProperty(name = "height", dataType = "String", value = "用户身高")
	protected String height;
	/**
	 *用户体重
	 */
	@ApiModelProperty(name = "weight", dataType = "String", value = "用户体重")
	protected String weight;
	/**
	 * 官方语言
	 */
	@ApiModelProperty(name = "language", dataType = "String", value = "官方语言")
	private String language;
	/**
	 * 用户简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "用户简介")
	private String intro;
	/**
	 * 个人照片（包含是否封面标记、序号、地址的JSON对象）
	 */
	@ApiModelProperty(name = "photos", dataType = "com.alibaba.fastjson.JSONArray", value = "个人照片（包含是否封面标记、序号、地址的JSON对象）")
	private JSONArray photos;
	/**
	 * 用户位置：常驻省份
	 */
	@ApiModelProperty(name = "province", dataType = "String", value = "用户位置：常驻省份")
	private String province;
	/**
	 * 用户位置：常驻城市
	 */
	@ApiModelProperty(name = "city", dataType = "String", value = "用户位置：常驻城市")
	private String city;
	/**
	 * 用户位置：常驻区域
	 */
	@ApiModelProperty(name = "area", dataType = "String", value = "用户位置：常驻区域")
	private String area;
	/**
	 * 用户位置：常驻地经度
	 */
	@ApiModelProperty(name = "longitude", dataType = "Double", value = "用户位置：常驻地经度")
	private double longitude;
	/**
	 * 用户位置：常驻地纬度
	 */
	@ApiModelProperty(name = "latitude", dataType = "Double", value = "用户位置：常驻地纬度")
	private double latitude;
	/**
	 *用户信息完成度
	 */
	@ApiModelProperty(name = "degree", dataType = "Integer", value = "用户信息完成度")
	private int degree;
	/**
	 * 初始化时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "初始化时间")
	private String time24;

}
