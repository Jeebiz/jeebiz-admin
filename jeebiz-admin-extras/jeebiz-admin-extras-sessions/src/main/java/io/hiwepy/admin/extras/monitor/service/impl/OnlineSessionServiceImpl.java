/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.monitor.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import io.hiwepy.admin.extras.monitor.dao.OnlineSessionMapper;
import io.hiwepy.admin.extras.monitor.dao.entities.OnlineSessionEntity;
import io.hiwepy.admin.extras.monitor.service.IOnlineSessionService;
import io.hiwepy.admin.extras.monitor.web.dto.OnlineSessionDTO;
import io.hiwepy.admin.extras.monitor.web.param.SessionQueryParam;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class OnlineSessionServiceImpl extends BaseServiceImpl<OnlineSessionMapper, OnlineSessionEntity>
		implements IOnlineSessionService {

	@Autowired
    private SessionDAO sessionMapper;
	@Autowired
    private RedisOperationTemplate redisOperation;

	@Override
	public List<OnlineSessionDTO> getActiveSessions(SessionQueryParam queryParam, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request) {

		if(sessionMapper == null){
			throw new IllegalStateException("sessionMapper must be set for this filter");
		}

		Long curruentSequence = queryParam.getLastSequence() + 20;
		String userSessionsKey = BizRedisKey.USER_SESSIONS.getKey();
		Set<TypedTuple<Object>> zRevrangeWithScores = getRedisOperation().zRevrangeWithScores(userSessionsKey,
				Math.max(0, queryParam.getLastSequence()), curruentSequence);
		if(CollectionUtils.isEmpty(zRevrangeWithScores)) {
			return Lists.newArrayList();
		}

		return zRevrangeWithScores.stream().map(tuple ->{
			return this.getActiveSession(tuple.getValue().toString(), appId, appChannel, appVersion, languageCode, request);
		}).collect(Collectors.toList());

	}

	@Override
	public OnlineSessionDTO getActiveSession(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request) {
		// 1、获取在线会话对象
		Session session = getSessionMapper().readSession(sessionId);
		// 2、对象不为空表示session有效
		if(Objects.nonNull(session)) {
			return OnlineSessionDTO.fromSession(session);
		}
		// 3、session已经失效，从数据库查询历史记录
		OnlineSessionEntity onlineSessionEntity = getBaseMapper().selectOne(new QueryWrapper<OnlineSessionEntity>().eq("s_sessionId", sessionId));
		return OnlineSessionDTO.fromSessionEntity(onlineSessionEntity);
	}

	@Override
	public boolean kickout(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request) {
		try {
			// 1、先从缓存中读取Session对象，更改会话状态（RedissonSession会自动更新对应的缓存），以便客户端访问期间Session状态感知
            Session session = getSessionMapper().readSession(sessionId);
            if(session != null) {
            	if(session instanceof SimpleOnlineSession) {
        			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
        			onlineSession.setStatus(OnlineStatus.FORCE_LOGOUT);
        		}
                session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
            // 2、最终手动删除
            getSessionMapper().delete(session);
            return true;
        } catch (Exception e) {/*ignore*/
        	return false;
        }
	}

	@Override
	public int offline(String sessionId) {
		String userSsoStateKey = BizRedisKey.USER_SSO_STATE.getKey();
		getRedisOperation().setBit(userSsoStateKey, 0, false);
		return 1;
	}

	@Override
	public int online(OnlineSessionEntity onlineSession) {
		String userSsoStateKey = BizRedisKey.USER_SSO_STATE.getKey();
		getRedisOperation().setBit(userSsoStateKey, 0, true);

		return 1;
	}

	public SessionDAO getSessionMapper() {
		return sessionMapper;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}

}
