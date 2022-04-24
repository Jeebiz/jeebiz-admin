/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.setup.provider;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import hitool.core.lang3.RandomString;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyUserModel;
import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyUserProfileModel;
import net.jeebiz.admin.authz.login.setup.ThirdpartyType;
import net.jeebiz.admin.authz.login.web.dto.AuthzWeixinMaBindDTO;
import net.jeebiz.boot.api.XHeaders;

@Component
@Slf4j
public class WxMinappBindingProvider implements ThirdpartyBindingProvider<AuthzWeixinMaBindDTO> {

	protected RandomString randomString = new RandomString(8);
	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;
    // 默认密码
 	private String defaultPassword = "123456";
	private WxMaService wxMaService;
	
	@Override
	public ThirdpartyType getType() {
		return ThirdpartyType.WXMA;
	}
	
	@Override
	public AuthzThirdpartyModel binding(HttpServletRequest request, AuthzWeixinMaBindDTO bindDTO) throws AuthenticationException {
		
		try {
			
			// 根据jscode获取会话信息
			WxMaJscode2SessionResult sessionResult = getWxMaService().jsCode2SessionInfo(bindDTO.getJscode());
			// 判断是否已经完成绑定
			//int rt = getAuthzThirdpartyService().getCountByUid(sessionResult.getOpenid());
			//if(rt > 0) {
			//	AuthzThirdpartyModel model = getAuthzThirdpartyService().getModelByOpenId(this.getType(), sessionResult.getOpenid());
			//	return model;
			//}
			
			// 获取用户手机号信息
			WxMaPhoneNumberInfo phoneNumberInfo = getWxMaService().getUserService().getPhoneNoInfo(sessionResult.getSessionKey(), bindDTO.getEncryptedData(), bindDTO.getIv());
			// 获取用户信息
			WxMaUserInfo userInfo = bindDTO.getUserInfo();
			if(Objects.isNull(userInfo)) {
				userInfo = getWxMaService().getUserService().getUserInfo(bindDTO.getSessionKey(), bindDTO.getEncryptedData(), bindDTO.getIv());
			}
			
			// 创建本地关联用户
			AuthzThirdpartyUserModel userModel = new AuthzThirdpartyUserModel();
			
			// 盐值，用于和密码混合起来用
	        String salt = randomString.nextString();
	        // 通过SimpleHash 来进行加密操作
	        SimpleHash hash = new SimpleHash(algorithmName, defaultPassword, salt, hashIterations);
	        userModel.setSalt(salt);
	        userModel.setPassword(hash.toBase64());
	        // Uid检查重复
	 		//String uid = randomString.nextNumberString();
	 		//while (getAuthzThirdpartyUserService().getCountByUid(uid) != 0) {
	 		//	uid = randomString.nextNumberString();
	 		//}
	 		//userModel.setUuid(uid);
			userModel.setUsername(phoneNumberInfo.getPurePhoneNumber());
			String appId = request.getHeader(XHeaders.X_APP_ID);
			String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
			String appVersion = request.getHeader(XHeaders.X_APP_VERSION);
			log.info(XHeaders.X_APP_ID + "：{}", appId);
			log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
			log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);
			userModel.setAppId(appId);
			userModel.setAppChannel(appChannel);
			userModel.setAppVer(appVersion);
			//getAuthzThirdpartyUserService().save(userModel);
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
			userProfileModel.setCountryCode(phoneNumberInfo.getCountryCode());
			userProfileModel.setPhone(phoneNumberInfo.getPhoneNumber());
			//getAuthzThirdpartyUserProfileService().save(userProfileModel);
			
			// 创建本地关联用户第三方登录信息
			AuthzThirdpartyModel model = new AuthzThirdpartyModel();
			model.setType(this.getType());
			model.setUid(userModel.getId());
			model.setOpenid(sessionResult.getOpenid());
			model.setUnionid(sessionResult.getUnionid());
			//getAuthzThirdpartyService().save(model);
			
			return model;
		} catch (WxErrorException e) {
			throw new AuthenticationException("微信信息获取异常", e);
		}
	}

	@Override
	public int unbind(AuthzThirdpartyModel model) throws AuthenticationException {
		return 0;
	}
	
	public WxMaService getWxMaService() {
		return wxMaService;
	}

}
