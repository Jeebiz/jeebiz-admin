/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzUserProfileRenewDTO", description = "用户详情信息更新DTO")
@Data
public class AuthzUserProfileRenewDTO {
 
	/**
	 * 用户详情Id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户详情Id")
	@NotBlank(message = "用户详情Id必填")
	private String id;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "nickname", required = true, dataType = "String", value = "用户昵称")
	@NotBlank(message = "用户昵称必填")
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
	@NotBlank(message = "国家编码必填")
	private String countryCode;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	@NotBlank(message = "手机号码必填")
	@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}",message = "无效的手机号")
	private String phone;
	/**
	 * 性别：（1：男，2：女）
	 */
	@ApiModelProperty(name = "gender", required = true, dataType = "String", value = "性别：（1：男，2：女）")
	@NotBlank(message = "性别必填")
	private Integer gender;
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

}
