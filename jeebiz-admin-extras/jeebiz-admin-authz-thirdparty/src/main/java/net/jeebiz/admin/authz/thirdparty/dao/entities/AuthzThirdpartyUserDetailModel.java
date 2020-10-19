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

@Alias(value = "AuthzThirdpartyUserDetailModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzThirdpartyUserDetailModel extends BaseModel<AuthzThirdpartyUserDetailModel> {
	 
	/**
	 * 用户Id
	 */
	private String uid;
	/**
	 * 用户详情ID
	 */
	private String id;
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
	 * 角色ID（可能多个组合，如：1,2）
	 */
	private String roleId;
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	private String roleName;
	
}
