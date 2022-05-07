/**
 * Copyright (C) 2022 杭州天音计算机系统工程有限公司
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao.entities;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;


@Alias(value = "AccountStatusModel")
@SuppressWarnings("serial")
@Data
public class AccountStatusModel implements Cloneable, Serializable {

	/**
	 * 匹配的用户数量：用户名匹配
	 */
	public int ucount;
	/**
	 * 匹配的用户数量：用户名和密码匹配
	 */
	public int pcount;
	/**
	 * 用户角色数量
	 */
	public int rcount;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public String status;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	private String salt;

	/**
	 * 账户是否存在
	 */
	public boolean isHasAcc() {
		return ucount > 0;
	}

	/**
	 * 角色是否存在
	 */
	public boolean isHasRole() {
		return rcount > 0;
	}

	/**
	 * 账户是否可用 :true:可用 false:不可用
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public boolean isEnabled() {
		return StringUtils.equalsIgnoreCase("1", status);
	}

	/**
	 * 账户是否锁定 :true:未锁定 false:已锁定
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public boolean isAccountNonLocked() {
		return !StringUtils.equalsIgnoreCase("2", status);
	}

	/**
	 * 账户是否过期 :true:没过期 false:过期
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 账户密码是否有效 :true:凭证有效 false:凭证无效
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	public boolean isCredentialsNonExpired() {
		return !StringUtils.equalsIgnoreCase("3", status);
	}

}
