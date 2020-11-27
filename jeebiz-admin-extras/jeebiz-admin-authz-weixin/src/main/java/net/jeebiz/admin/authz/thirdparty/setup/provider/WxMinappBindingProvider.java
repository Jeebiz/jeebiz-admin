/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup.provider;

import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import net.jeebiz.admin.authz.thirdparty.dao.IAuthzThirdpartyDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzWeixinMaBindDTO;

@Component
public class WxMinappBindingProvider implements ThirdpartyBindingProvider<AuthzWeixinMaBindDTO> {

	@Autowired
	private IAuthzThirdpartyDao authzThirdpartyDao;
	@Autowired
	private WxMaService wxMaService;
	
	@Override
	public ThirdpartyType getType() {
		return ThirdpartyType.WXMA;
	}
	
	@Override
	public AuthzThirdpartyModel binding(AuthzWeixinMaBindDTO bindDTO) throws AuthenticationException {
		
		try {
			
			// 根据jscode获取会话信息
			WxMaJscode2SessionResult sessionResult = getWxMaService().jsCode2SessionInfo(bindDTO.getJscode());
			// 判断是否已经完成绑定
			int rt = getAuthzThirdpartyDao().getCountByUid(sessionResult.getOpenid());
			if(rt > 0) {
				AuthzThirdpartyModel model = getAuthzThirdpartyDao().getModelByOpenId(this.getType().name(), sessionResult.getOpenid());
				return model;
			}
			
			// 获取用户手机号信息
			WxMaPhoneNumberInfo phoneNumberInfo = getWxMaService().getUserService().getPhoneNoInfo(sessionResult.getSessionKey(), bindDTO.getEncryptedData(), bindDTO.getIv());
			// 获取用户信息
			WxMaUserInfo userInfo = bindDTO.getUserInfo();
			if(Objects.isNull(userInfo)) {
				userInfo = getWxMaService().getUserService().getUserInfo(bindDTO.getSessionKey(), bindDTO.getEncryptedData(), bindDTO.getIv());
			}
			// 创建本地关联用户
			AuthzThirdpartyUserModel userModel = new AuthzThirdpartyUserModel();
			userModel.setUsername(phoneNumberInfo.getPurePhoneNumber());
			userModel.setAlias(userInfo.getNickName());
			userModel.setAvatar(userInfo.getAvatarUrl());
			userModel.setPhone(phoneNumberInfo.getPhoneNumber());
			getAuthzThirdpartyDao().insertUser(userModel);
			
			// 创建本地关联用户第三方登录信息
			AuthzThirdpartyModel model = new AuthzThirdpartyModel();
			model.setType(this.getType());
			model.setUid(userModel.getId());
			model.setOpenid(sessionResult.getOpenid());
			model.setUnionid(sessionResult.getUnionid());
			getAuthzThirdpartyDao().insert(model);
			
			return model;
		} catch (WxErrorException e) {
			throw new AuthenticationException("微信信息获取异常", e);
		}
	}

	@Override
	public int unbind(AuthzThirdpartyModel model) throws AuthenticationException {
		return 0;
	}
	
	public IAuthzThirdpartyDao getAuthzThirdpartyDao() {
		return authzThirdpartyDao;
	}
	
	public WxMaService getWxMaService() {
		return wxMaService;
	}

}
