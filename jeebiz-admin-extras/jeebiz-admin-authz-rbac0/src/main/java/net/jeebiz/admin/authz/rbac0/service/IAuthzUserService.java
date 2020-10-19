/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserAllotRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.boot.api.service.IBaseService;


/**
 * 用户管理Service接口
 */
public interface IAuthzUserService extends IBaseService<AuthzUserModel> {
	
	/**
	 * 用户注册
	 * @param model
	 * @return
	 */
	int register(AuthzUserModel model);
	
	/**
	 * 查询系统所有用户
	 * @return
	 */
	public List<AuthzUserModel> getUserList();

	/**
	 * 批量修改用户密码
	 * @param users 用户名数组
	 * @param type 密码初始化方式（0：系统默认密码，1：身份证后6位，2：手机号后6位，3：学工号后6位）
	 * @return 变更记录数
	 */
	public int resetPwd(List<String> users, String type);

	/**
	 * 当前用户设置密码
	 * @param userId 用户ID
	 * @param oldPassword 旧密码
	 * @param password    新密码
	 * @return
	 */
	public int resetPwd(String userId,String oldPassword,String password);
	
	/**
	 * 更新用户状态
	 * @param roleId 用户ID
	 * @param status 用户状态（0:禁用|1:可用|2:锁定）
	 * @return
	 */
	public int setStatus( String userId, String status);
	
	/**
	 * 执行用户分配角色逻辑操作
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	public int doAllot(AuthzUserAllotRoleModel model);
	
	/**
	 * 取消已分配给指定用户的角色
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	public int doUnAllot(AuthzUserAllotRoleModel model);

	/**
	 * 根据手机号查询相同手机号数量
	 * @param phone 手机号码
	 * @return
	 */
	public int getCountByPhone( String phone);

	/**
	 * 根据邮箱查询相同手机号数量
	 * @param email 手机号码
	 * @return
	 */
	public int getCountByEmail(String email);
	
	/**
	 * 获取用户已分配角色ID
	 * @param userId 用户ID
	 * @return
	 */
	public List<AuthzRoleModel> getRoles(String userId);
	
	/**
	 * 查询角色具备的权限标记 
	 * @param userId 用户ID
	 * @return 用户所属角色具备的权限标记
	 */
	public List<String> getPermissions(String userId);
	
	/**
	 * 分页查询用户已分配角色信息
	 * @param model
	 * @return
	 */
	public Page<AuthzRoleModel> getPagedAllocatedList(AuthzUserModel model);
	
	/**
	 * 分页查询用户未分配角色信息
	 * @param model
	 * @return
	 */
	public Page<AuthzRoleModel> getPagedUnAllocatedList(AuthzUserModel model);

	/**
	 * 通过ID删除用户
	 * @param id
	 * @return
	 */
	int deleteUserById(String id);

    int getCountUpdateByPhone(String phone, String id);

	int getCountUpdateByEmail(String email, String id);

}
