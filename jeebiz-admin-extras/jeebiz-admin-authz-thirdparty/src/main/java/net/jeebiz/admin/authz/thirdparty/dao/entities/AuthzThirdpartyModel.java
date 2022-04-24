/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.dao.entities;

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
import net.jeebiz.admin.authz.login.setup.ThirdpartyType;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "AuthzThirdpartyModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_thirdparty")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzThirdpartyModel extends PaginationEntity<AuthzThirdpartyModel> {

	/**
	 * 第三方账号登录表id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 用户Id
	 */
	@TableField(value = "u_id")
	private String uid;
	/**
	 * 用户名称
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 第三方账号类型
	 * @see ThirdpartyType
	 */
	@TableField(value = "t_type")
	private ThirdpartyType type;
	/**
	 * 第三方平台Unionid（通常指第三方账号体系下用户的唯一id）
	 */
	@TableField(value = "t_unionid")
	private String unionid;
	/**
	 * 第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）
	 */
	@TableField(value = "t_openid")
	private String openid;
	/**
	 * 当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）
	 */
	@TableField(value = "t_devid")
	private String devid;
	/**
	 * 第三方认证账号扩展信息
	 */
	@TableField(value = "t_rawdata")
	private String rawdata;
	
}
