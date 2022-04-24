/**
 * Copyright (C) 2018 Jeebiz (http://dajuxiaowo.com).
 * All Rights Reserved.
 */
package net.jeebiz.admin.shadow.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;

import net.jeebiz.admin.authz.rbac0.dao.entities.RoleEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.biz.authc.exception.InvalidAccountException;
import org.apache.shiro.biz.authc.exception.NoneRoleException;
import org.apache.shiro.biz.authz.principal.ShiroPrincipalRepositoryImpl;
import org.apache.shiro.biz.utils.WebThreadContext;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.spring.boot.dingtalk.token.DingTalkAuthenticationToken;
import org.apache.shiro.spring.boot.weixin.authc.WxMaLoginRequest;
import org.apache.shiro.spring.boot.weixin.authc.WxMpLoginRequest;
import org.apache.shiro.spring.boot.weixin.token.WxMaAuthenticationToken;
import org.apache.shiro.spring.boot.weixin.token.WxMpAuthenticationToken;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.hiwepy.jwt.JwtPayload.RolePair;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import hitool.core.collections.CollectionUtils;
import hitool.core.lang3.RandomString;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import net.jeebiz.admin.authz.rbac0.dao.RoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.RolePermsMapper;
import net.jeebiz.admin.authz.rbac0.dao.UserAccountMapper;
import net.jeebiz.admin.authz.thirdparty.dao.AuthzThirdpartyMapper;
import net.jeebiz.admin.authz.thirdparty.dao.AuthzThirdpartyUserMapper;
import net.jeebiz.admin.authz.thirdparty.dao.AuthzThirdpartyUserProfileMapper;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserModel;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserProfileModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.shadow.dao.AuthzLoginMapper;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginStatusModel;
import net.jeebiz.boot.api.XHeaders;

@Service("defRepository")
@Slf4j
public class AuthzPrincipalRepositoryImpl extends ShiroPrincipalRepositoryImpl {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected RandomString randomString = new RandomString(8);
	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;
    // 默认密码
 	private String defaultPassword = "123456";

	@Autowired
	private WxMaService wxMaService;
	@Autowired
	private AuthzLoginMapper authzLoginMapper;
	@Autowired
	private UserAccountMapper userAccountMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermsMapper rolePermsMapper;
	@Autowired
	private AuthzThirdpartyMapper authzThirdpartyMapper;
	@Autowired
	private AuthzThirdpartyUserMapper authzThirdpartyUserMapper;
	@Autowired
	private AuthzThirdpartyUserProfileMapper authzThirdpartyUserProfileMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 钉钉扫码登陆
		if(DingTalkAuthenticationToken.class.isAssignableFrom(token.getClass())) {
			return this.loginDingTalk(token);
		}
		// 扫脸登录
		//else if(QrcodeAuthorizationToken.class.isAssignableFrom(token.getClass())) {

		//}
		// 微信（公共号、服务号）登录
		else if(WxMpAuthenticationToken.class.isAssignableFrom(token.getClass())) {
			return this.loginWxMp(token);
		}
		// 微信（小程序）登录
		else if(WxMaAuthenticationToken.class.isAssignableFrom(token.getClass())) {
			return this.loginWxMa(token);
		}
		// 默认的账号密码登录
		return this.loginPwd(token);
	}

	protected SimpleAuthenticationInfo loginPwd(AuthenticationToken token) {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		if( StringUtils.isBlank(upToken.getUsername()) || upToken.getPassword() == null ){
			throw new UnknownAccountException("Username or password is required.");
		}
		// 账号状态
		AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusWithoutPwd(upToken.getUsername());
   		// 账号不存在 或 用户名或密码不正确
   		if(!statusModel.isHasAcc()){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		// 账号被禁用
		else if (!statusModel.isEnabled()) {
			throw new DisabledAccountException("Account is disabled.");
		}
   		//用户无所属角色
   		else if(!statusModel.isHasRole()){
            throw new NoneRoleException();
   		}
   		// 密码
		String password = new String(upToken.getPassword());
        // 通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, password, statusModel.getSalt(), hashIterations);

   		// 用户主体对象
   		AuthzLoginModel model = getAuthzLoginMapper().getAccount(upToken.getUsername(), hash.toBase64());
   		if(model == null){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}

   		return this.principal(statusModel, model, null, password);
	}

	protected SimpleAuthenticationInfo loginDingTalk(AuthenticationToken token) {

		DingTalkAuthenticationToken dingTalkToken = (DingTalkAuthenticationToken) token;

		// 账号状态
		String account = StringUtils.defaultString(dingTalkToken.getJobnumber(), dingTalkToken.getMobile());
		AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusWithoutPwd(account);
   		// 账号不存在 或 用户名或密码不正确
   		if(!statusModel.isHasAcc()){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		// 账号被禁用
		else if (!statusModel.isEnabled()) {
			throw new DisabledAccountException("Account is disabled.");
		}
   		//用户无所属角色
   		else if(!statusModel.isHasRole()){
            throw new NoneRoleException();
   		}
   		// 密码
		String password = new String(dingTalkToken.getPassword());
        // 通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, password, statusModel.getSalt(), hashIterations);

		//	用户主体对象
   		AuthzLoginModel model = getAuthzLoginMapper().getAccountWithoutPwd(account);
   		//	系统没有钉钉userid对应的用户数据，表示第一次扫码登陆
		if(model == null) {
			/*
			logger.debug(JSONObject.toJSONString(dingTalkToken));

			// 	构建新增用户数据信息
			AuthzUserEntity detailModel = new AuthzUserEntity();

			detailModel.setAlias(StringUtils.defaultIfBlank(dingTalkToken.getName(), dingTalkToken.getNick()));
			detailModel.setAvatar(dingTalkToken.getAvatar());
			detailModel.setEmail(dingTalkToken.getEmail());
			detailModel.setPassword(passwordEncoder.encode(defPassword));
			detailModel.setPhone(dingTalkToken.getMobile());
			detailModel.setRemark(dingTalkToken.getRemark());
			// 	手机号码作为登录账户
			detailModel.setUsername(StringUtils.defaultString(dingTalkToken.getJobnumber(), dingTalkToken.getMobile()));

			logger.error(JSONObject.toJSONString(detailModel));

			getAuthzUserMapper().insert(detailModel);

			// 	查询用户信息
			model = getAuthzLoginMapper().getAccountById(detailModel.getId());
			model.setInitial(true);
			*/
		} else {
			model.setInitial(false);
		}
   		if(model == null){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}

   		return this.principal(statusModel, model, null, password);
	}


	protected SimpleAuthenticationInfo loginWxMp(AuthenticationToken token) {

		WxMpAuthenticationToken wxToken = (WxMpAuthenticationToken) token;

		// 查询微信是否已经绑定
		int rt = getAuthzThirdpartyMapper().getCountByOpenId(wxToken.getOpenid());
		// 微信未绑定
		if(rt == 0) {

			WxMpLoginRequest loginRequest = (WxMpLoginRequest) wxToken.getPrincipal();

			// 用户名不为空，表示交互上绑定已知的用户，否则自动创建用户
			if(StringUtils.isNotBlank(loginRequest.getUsername())) {

		    	//	账号状态
		    	AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusWithoutPwd(loginRequest.getUsername());
		   		//	账号不存在 或 用户名或密码不正确
		   		if(!statusModel.isHasAcc()){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}
		   		// 用户主体对象
		   		AuthzLoginModel model = getAuthzLoginMapper().getAccountWithoutPwd(loginRequest.getUsername());
		   		if(model == null){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}

		   		// 创建本地关联用户第三方登录信息
				AuthzThirdpartyModel thirdModel = new AuthzThirdpartyModel();
				thirdModel.setType(ThirdpartyType.WXMP);
				thirdModel.setUid(model.getUserid());
				thirdModel.setOpenid(wxToken.getOpenid());
				thirdModel.setUnionid(wxToken.getUnionid());
				getAuthzThirdpartyMapper().insert(thirdModel);

				// 通过SimpleHash 来进行加密操作
		        SimpleHash hash = new SimpleHash(algorithmName, new String(loginRequest.getPassword()), statusModel.getSalt(), hashIterations);
		   		return this.principal(statusModel, model, null, hash.toBase64());

			} else {

				// 获取用户信息
				WxOAuth2UserInfo userInfo = wxToken.getUserInfo();

				// 创建本地关联用户
				AuthzThirdpartyUserModel userModel = new AuthzThirdpartyUserModel();
				// 盐值，用于和密码混合起来用
		        String salt = randomString.nextString();
		        // 通过SimpleHash 来进行加密操作
		        SimpleHash hash = new SimpleHash(algorithmName, defaultPassword, salt, hashIterations);
		        userModel.setSalt(salt);
		        userModel.setPassword(hash.toBase64());
		        // Uid检查重复
		 		String uid = randomString.nextNumberString();
		 		while (getAuthzThirdpartyUserMapper().getCountByUid(uid) != 0) {
		 			uid = randomString.nextNumberString();
		 		}
		 		userModel.setUuid(uid);
				// userModel.setUsername(phoneNumberInfo.getPurePhoneNumber());
		 		userModel.setUsername(randomString.nextString());
				ServletRequest request = WebThreadContext.getRequest();
				if(Objects.nonNull(request) && request instanceof ShiroHttpServletRequest) {
					ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
					String appId = shiroRequest.getHeader(XHeaders.X_APP_ID);
					String appChannel = shiroRequest.getHeader(XHeaders.X_APP_CHANNEL);
					String appVersion = shiroRequest.getHeader(XHeaders.X_APP_VERSION);
					log.info(XHeaders.X_APP_ID + "：{}", appId);
					log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
					log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);
					userModel.setAppId(appId);
					userModel.setAppChannel(appChannel);
					userModel.setAppVer(appVersion);
				}
				getAuthzThirdpartyUserMapper().insert(userModel);
				// 创建本地关联用户详情
				AuthzThirdpartyUserProfileModel userProfileModel = new AuthzThirdpartyUserProfileModel();
				userProfileModel.setUid(userModel.getId());
				userProfileModel.setNickname(userInfo.getNickname());
				userProfileModel.setAvatar(userInfo.getHeadImgUrl());
				userProfileModel.setCountry(userInfo.getCountry());
				userProfileModel.setProvince(userInfo.getProvince());
				userProfileModel.setCity(userInfo.getCity());
				//userProfileModel.setGender(userInfo.getGender());
				//userProfileModel.setLanguage(userInfo.getLanguage());
				//userProfileModel.setCountryCode(phoneNumberInfo.getCountryCode());
				//userProfileModel.setPhone(phoneNumberInfo.getPhoneNumber());
				getAuthzThirdpartyUserProfileMapper().insert(userProfileModel);

				// 创建本地关联用户第三方登录信息
				AuthzThirdpartyModel thirdModel = new AuthzThirdpartyModel();
				thirdModel.setType(ThirdpartyType.WXMP);
				thirdModel.setUid(userModel.getId());
				thirdModel.setOpenid(wxToken.getOpenid());
				thirdModel.setUnionid(wxToken.getUnionid());
				getAuthzThirdpartyMapper().insert(thirdModel);

				// 设置游客权限
				getRoleMapper().setUsers("4", Arrays.asList(userModel.getId()));

				// 账号状态
				AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusById(userModel.getId());
				// 账号不存在 或 用户名或密码不正确
		   		if(!statusModel.isHasAcc()){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}
				// 用户主体对象
		   		AuthzLoginModel model = getAuthzLoginMapper().getAccountByUid(userModel.getId());
		   		if(model == null){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}

		   		return this.principal(statusModel, model, null, hash.toBase64());
			}
		}

		// 查询第三方登录信息
		AuthzThirdpartyModel thirdModel = getAuthzThirdpartyMapper().getModelByOpenId(ThirdpartyType.WXMP.name(), wxToken.getOpenid());
		// 账号状态
		AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusById(thirdModel.getUid());
		// 账号不存在 或 用户名或密码不正确
   		if(!statusModel.isHasAcc()){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		// 用户主体对象
   		AuthzLoginModel model = getAuthzLoginMapper().getAccountByUid(thirdModel.getUid());
   		if(model == null){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		// 通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, new String(wxToken.getPassword()), statusModel.getSalt(), hashIterations);
   		return this.principal(statusModel, model, null, hash.toBase64());
	}

	protected SimpleAuthenticationInfo loginWxMa(AuthenticationToken token) {

 		WxMaAuthenticationToken wxToken = (WxMaAuthenticationToken) token;

		// 查询微信是否已经绑定
		int rt = getAuthzThirdpartyMapper().getCountByOpenId(wxToken.getOpenid());
		// 微信未绑定
		if(rt == 0) {

			WxMaLoginRequest loginRequest = (WxMaLoginRequest) wxToken.getPrincipal();

			// 用户名不为空，表示交互上绑定已知的用户，否则自动创建用户
			if(StringUtils.isNotBlank(loginRequest.getUsername())) {

		    	//	账号状态
		    	AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusWithoutPwd(loginRequest.getUsername());
		   		//	账号不存在 或 用户名或密码不正确
		   		if(!statusModel.isHasAcc()){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}
		   		// 用户主体对象
		   		AuthzLoginModel model = getAuthzLoginMapper().getAccountWithoutPwd(loginRequest.getUsername());
		   		if(model == null){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}

		   		// 创建本地关联用户第三方登录信息
				AuthzThirdpartyModel thirdModel = new AuthzThirdpartyModel();
				thirdModel.setType(ThirdpartyType.WXMA);
				thirdModel.setUid(model.getUserid());
				thirdModel.setOpenid(wxToken.getOpenid());
				thirdModel.setUnionid(wxToken.getUnionid());
				getAuthzThirdpartyMapper().insert(thirdModel);

				// 通过SimpleHash 来进行加密操作
		        SimpleHash hash = new SimpleHash(algorithmName, new String(loginRequest.getPassword()), statusModel.getSalt(), hashIterations);

		   		return this.principal(statusModel, model, null, hash.toBase64());

			} else {

				// 获取用户手机号信息
				WxMaPhoneNumberInfo phoneNumberInfo = wxToken.getPhoneNumberInfo();

				// 获取用户信息
				WxMaUserInfo userInfo = wxToken.getUserInfo();

				// 创建本地关联用户
				AuthzThirdpartyUserModel userModel = new AuthzThirdpartyUserModel();
				// 盐值，用于和密码混合起来用
		        String salt = randomString.nextString();
		        // 通过SimpleHash 来进行加密操作
		        SimpleHash hash = new SimpleHash(algorithmName, defaultPassword, salt, hashIterations);
		        userModel.setSalt(salt);
		        userModel.setPassword(hash.toBase64());
		        // Uid检查重复
		 		String uid = randomString.nextNumberString();
		 		while (getAuthzThirdpartyUserMapper().getCountByUid(uid) != 0) {
		 			uid = randomString.nextNumberString();
		 		}
		 		userModel.setUuid(uid);
				userModel.setUsername(phoneNumberInfo.getPurePhoneNumber());
				ServletRequest request = WebThreadContext.getRequest();
				if(Objects.nonNull(request) && request instanceof ShiroHttpServletRequest) {
					ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
					String appId = shiroRequest.getHeader(XHeaders.X_APP_ID);
					String appChannel = shiroRequest.getHeader(XHeaders.X_APP_CHANNEL);
					String appVersion = shiroRequest.getHeader(XHeaders.X_APP_VERSION);
					log.info(XHeaders.X_APP_ID + "：{}", appId);
					log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
					log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);
					userModel.setAppId(appId);
					userModel.setAppChannel(appChannel);
					userModel.setAppVer(appVersion);
				}
				getAuthzThirdpartyUserMapper().insert(userModel);
				// 创建本地关联用户详情
				AuthzThirdpartyUserProfileModel userProfileModel = new AuthzThirdpartyUserProfileModel();
				userProfileModel.setUid(userModel.getId());
				userProfileModel.setNickname(userInfo.getNickName());
				userProfileModel.setAvatar(userInfo.getAvatarUrl());
				userProfileModel.setCountry(userInfo.getCountry());
				userProfileModel.setProvince(userInfo.getProvince());
				userProfileModel.setCity(userInfo.getCity());
				userProfileModel.setGender(userInfo.getGender());
				userProfileModel.setLanguage(userInfo.getLanguage());
				userProfileModel.setCountryCode(Objects.nonNull(phoneNumberInfo) ? phoneNumberInfo.getPurePhoneNumber() : "86");
				userProfileModel.setPhone(Objects.nonNull(phoneNumberInfo) ? phoneNumberInfo.getPhoneNumber() : "");
				getAuthzThirdpartyUserProfileMapper().insert(userProfileModel);

				// 创建本地关联用户第三方登录信息
				AuthzThirdpartyModel thirdModel = new AuthzThirdpartyModel();
				thirdModel.setType(ThirdpartyType.WXMA);
				thirdModel.setUid(userModel.getId());
				thirdModel.setOpenid(wxToken.getOpenid());
				thirdModel.setUnionid(wxToken.getUnionid());
				getAuthzThirdpartyMapper().insert(thirdModel);

				// 设置游客权限
				getRoleMapper().setUsers("4", Arrays.asList(userModel.getId()));

				// 账号状态
				AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusById(userModel.getId());
				// 账号不存在 或 用户名或密码不正确
		   		if(!statusModel.isHasAcc()){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}
				// 用户主体对象
		   		AuthzLoginModel model = getAuthzLoginMapper().getAccountByUid(userModel.getId());
		   		if(model == null){
		   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
		   		}

		   		return this.principal(statusModel, model, null, hash.toBase64());
			}

		}

		try {
			// 查询第三方登录信息
			AuthzThirdpartyModel thirdModel = getAuthzThirdpartyMapper().getModelByOpenId(ThirdpartyType.WXMA.name(), wxToken.getOpenid());
			// 账号状态
			AuthzLoginStatusModel statusModel = getAuthzLoginMapper().getAccountStatusById(thirdModel.getUid());
			// 账号不存在 或 用户名或密码不正确
			if(!statusModel.isHasAcc()){
				throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
			}
			// 用户主体对象
			AuthzLoginModel model = getAuthzLoginMapper().getAccountByUid(thirdModel.getUid());
			if(model == null){
				throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
			}
			// 通过SimpleHash 来进行加密操作
	        SimpleHash hash = new SimpleHash(algorithmName, model.getPassword(), statusModel.getSalt(), hashIterations);

			return this.principal(statusModel, model, null, hash.toBase64());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	protected SimpleAuthenticationInfo principal(AuthzLoginStatusModel statusModel, AuthzLoginModel model, String roleId, String password) {
		// 用户角色信息集合
   		List<RoleEntity> roleModels = getRoleMapper().getUserRoles(model.getId());

   		Optional<RoleEntity> roleFirst = CollectionUtils.isEmpty(roleModels) ? Optional.empty()
				: roleModels.stream().findFirst();

   		// 用户权限标记集合
   		Set<String> perms =  Sets.newHashSet();

   		try {

			logger.error(JSONObject.toJSONString(model));

			// 有设置角色：构造用户权限
			if(roleFirst.isPresent()) {
				RoleEntity role = roleFirst.get();
		   		// 	用户权限标记集合
				perms.addAll(getRolePermsMapper().getPermissions(role.getId()));
			}

			model.setUserid(model.getId());
			model.setPerms(perms);

			// 有设置角色：构造角色信息
			if(roleFirst.isPresent()) {

				List<RolePair> roles = CollectionUtils.isEmpty(roleModels) ? Lists.newArrayList() : roleModels.stream().map(role -> {
					return new RolePair(role.getId(), role.getKey(), role.getName());
		   		}).collect(Collectors.toList());

				model.setRoles(roles);
				model.setRole(roleFirst.get().getKey());
				model.setRoleid(roleFirst.get().getId());

			}
			model.setInitial(model.isInitial());

			// 查询用户个人信息
			Map<String, Object> profile = getAuthzLoginMapper().getAccountProfile(model.getId());
			model.setProfile(profile);

		} catch (Exception e) {
			e.printStackTrace();
		}

   		// 认证信息
   		ByteSource credentialsSalt = ByteSource.Util.bytes(statusModel.getSalt());
   		return new SimpleAuthenticationInfo(model, password, credentialsSalt, "login");
		//return new SimpleAuthenticationInfo("", userService.findUserByUsername(accountPasswordToken.getUsername()).getPassword(), "");
	}

	@Override
	public Set<String> getRoles(Set<Object> principals) {
		Set<String> sets =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof AuthzLoginModel) {
				AuthzLoginModel model = (AuthzLoginModel) principal;
				// 用户角色信息集合
		   		List<RoleEntity> roleModels = getRoleMapper().getUserRoles(model.getUserid());
				if(CollectionUtils.isNotEmpty(roleModels)) {
					for (RoleEntity RoleEntity : roleModels) {
						sets.add(RoleEntity.getKey());
					}
				}
			}
		}
		return sets;
	}


	@Override
	public Set<String> getPermissions(Set<Object> principals) {
		Set<String> set =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof AuthzLoginModel) {
				AuthzLoginModel model = (AuthzLoginModel) principal;
				set.addAll(getRolePermsMapper().getPermissions(model.getRoleid()));
				for (RoleEntity roleModel : model.getRoleList()) {
					set.addAll(getRolePermsMapper().getPermissions(roleModel.getId()));
				}
			}
		}
		return set;
	}

	@Override
	public void doLock(Object principal) {
		// TODO Auto-generated method stub

	}

	public Logger getLogger() {
		return logger;
	}

	public AuthzLoginMapper getAuthzLoginMapper() {
		return authzLoginMapper;
	}

	public UserAccountMapper getAuthzUserMapper() {
		return userAccountMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public RolePermsMapper getRolePermsMapper() {
		return rolePermsMapper;
	}

	public AuthzThirdpartyMapper getAuthzThirdpartyMapper() {
		return authzThirdpartyMapper;
	}

	public AuthzThirdpartyUserMapper getAuthzThirdpartyUserMapper() {
		return authzThirdpartyUserMapper;
	}

	public AuthzThirdpartyUserProfileMapper getAuthzThirdpartyUserProfileMapper() {
		return authzThirdpartyUserProfileMapper;
	}

	public WxMaService getWxMaService() {
		return wxMaService;
	}

}
