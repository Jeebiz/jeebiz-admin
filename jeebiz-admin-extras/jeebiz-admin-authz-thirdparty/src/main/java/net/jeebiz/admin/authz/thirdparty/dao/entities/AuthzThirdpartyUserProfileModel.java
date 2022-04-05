/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.thirdparty.dao.entities;

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
import net.jeebiz.boot.api.dao.entities.BaseEntity;

@Alias(value = "AuthzThirdpartyUserProfileModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_user_profile")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzThirdpartyUserProfileModel extends BaseEntity<AuthzThirdpartyUserProfileModel> {

	/**
	 * 用户详情Id
	 */
	@TableId(value="u_pid",type= IdType.AUTO)
	private String id;
	/**
	 * 用户id
	 */
	@TableField(value = "u_id")
	private String uid;
	/**
	 * 用户别名（昵称）
	 */
	@TableField(value = "u_nickname")
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@TableField(value = "u_avatar")
	private String avatar;
	/**
	 * 手机号码国家码
	 */
	@TableField(value = "u_region_code")
	private String countryCode;
	/**
	 * 手机号码
	 */
	@TableField(value = "u_phone")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@TableField(value = "u_email")
	private String email;
	/**
	 * 出生日期
	 */
	@TableField(value = "u_birthday")
	private String birthday;
	/**
	 * 性别：（M：男，F：女）
	 */
	@TableField(value = "u_gender")
	private String gender;
	/**
	 * 身份证号码
	 */
	@TableField(value = "u_idcard")
	private String idcard;
	/**
	 * 用户年龄
	 */
	@TableField(value = "u_age")
	private int age;
	/**
	 *用户身高
	 */
	@TableField(value = "u_height")
	private String height;
	/**
	 *用户体重
	 */
	@TableField(value = "u_weight")
	private String weight;
	/**
	 * 官方语言
	 */
	@TableField(value = "u_language")
	private String language;
	/**
	 * 用户备注
	 */
	@TableField(value = "u_intro")
	private String intro;
	/**
	 * 个人照片（包含是否封面标记、序号、地址的JSON字符串）
	 */
	@TableField(value = "u_photos")
	private String photos;
	/**
	 * 用户位置：常驻国家
	 */
	@TableField(value = "u_country")
	private String country;
	/**
	 * 用户位置：常驻省份
	 */
	@TableField(value = "u_province")
	private String province;
	/**
	 * 用户位置：常驻城市
	 */
	@TableField(value = "u_city")
	private String city;
	/**
	 * 用户位置：常驻区域
	 */
	@TableField(value = "u_area")
	private String area;
	/**
	 * 用户位置：常驻地经度
	 */
	@TableField(value = "u_wgs84_lng")
	private double longitude;
	/**
	 * 用户位置：常驻地纬度
	 */
	@TableField(value = "u_wgs84_lat")
	private double latitude;
	/**
	 *用户信息完成度
	 */
	@TableField(value = "u_degree")
	private int degree;

}
