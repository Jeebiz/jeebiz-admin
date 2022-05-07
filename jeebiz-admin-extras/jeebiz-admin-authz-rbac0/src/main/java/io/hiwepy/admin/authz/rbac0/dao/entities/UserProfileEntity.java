/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

import java.time.LocalDate;

@Alias(value = "UserProfileEntity")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_user_profile")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserProfileEntity extends BaseEntity<UserProfileEntity> {

	/**
	 * 用户id
	 */
	@TableId(value="id", type= IdType.AUTO)
	private String userId;
	/**
	 * 用户code（短号/工号）
	 */
	@TableField(value = "code")
	private String userCode;
	/**
	 * 用户别名（昵称）
	 */
	@TableField(value = "nickname")
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@TableField(value = "avatar")
	private String avatar;
	/**
	 * 国家/地区编码
	 */
	@TableField(value = "region_code")
	private String regionCode;
	/**
	 * 手机号码
	 */
	@TableField(value = "phone")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@TableField(value = "email")
	private String email;
	/**
	 * 出生日期
	 */
	@TableField(value = "birthday")
	private LocalDate birthday;
	/**
	 * 性别：（1：男，2：女）
	 */
	@TableField(value = "gender")
	private int gender;
	/**
	 * 身份证号码
	 */
	@TableField(value = "idcard")
	private String idcard;
	/**
	 * 用户年龄
	 */
	@TableField(value = "age")
	private int age;
	/**
	 *用户身高
	 */
	@TableField(value = "height")
	private String height;
	/**
	 *用户体重
	 */
	@TableField(value = "weight")
	private String weight;
	/**
	 * 官方语言
	 */
	@TableField(value = "language")
	private String language;
	/**
	 * 个人签名
	 */
	@TableField(value = "signature")
	private String signature;
	/**
	 * 用户备注
	 */
	@TableField(value = "intro")
	private String intro;
	/**
	 * 个人照片（包含是否封面标记、序号、地址的JSON字符串）
	 */
	@TableField(value = "photos")
	private String photos;
	/**
	 * 用户位置：常驻国家
	 */
	@TableField(value = "country")
	private String country;
	/**
	 * 用户位置：常驻省份
	 */
	@TableField(value = "province")
	private String province;
	/**
	 * 用户位置：常驻城市
	 */
	@TableField(value = "city")
	private String city;
	/**
	 * 用户位置：常驻区域
	 */
	@TableField(value = "area")
	private String area;
	/**
	 * 用户位置：常驻地经度
	 */
	@TableField(value = "wgs84_lng")
	private double longitude;
	/**
	 * 用户位置：常驻地纬度
	 */
	@TableField(value = "wgs84_lat")
	private double latitude;
	/**
	 *用户信息完成度
	 */
	@TableField(value = "degree")
	private int degree;
	/**
	 * 注册客户端应用id
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 注册客户端应用渠道编码
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 * 注册客户端版本
	 */
	@TableField(value = "app_version")
	private String appVer;

}
