/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.extras.sessions.dao.IOnlineSessionDao;
import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;
import net.jeebiz.admin.extras.sessions.service.IOnlineSessionService;
import net.jeebiz.admin.extras.sessions.web.dto.OnlineSessionDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class OnlineSessionServiceImpl extends BaseServiceImpl<OnlineSessionModel, IOnlineSessionDao>
		implements IOnlineSessionService {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	@Autowired  
    private SessionDAO sessionDao;
	@Autowired  
    private RedisOperationTemplate redisOperationTemplate;
	
	@Override
	public List<OnlineSessionDTO> getActiveSessions() {
		
		if(sessionDao == null){
			throw new IllegalStateException("sessionDao must be set for this filter");
		}
    	Collection<Session> sessions = getSessionDao().getActiveSessions();
    	List<OnlineSessionDTO> onlineSessions = Lists.newArrayList();
    	for(Session session : sessions){         
    		
			OnlineSessionDTO sessionDTO = new OnlineSessionDTO(String.valueOf(session.getId()), session.getHost(),
					dateFormat.format(session.getStartTimestamp()), 
					dateFormat.format(session.getLastAccessTime()),
					session.getTimeout());
    		
    		if(session instanceof SimpleOnlineSession) {
    			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
    			sessionDTO.setStatus(onlineSession.getStatus().getInfo());
    			sessionDTO.setUserAgent(onlineSession.getUserAgent());
    			sessionDTO.setSystemHost(onlineSession.getSystemHost());
    		}
    		if(Boolean.TRUE.equals(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY))) {
    			sessionDTO.setStatus(OnlineStatus.FORCE_LOGOUT.getInfo());
    		} 
    		onlineSessions.add(sessionDTO);
		}
		return onlineSessions;
	}

	@Override
	public boolean kickout(String sessionId) {
		try {
			// 1、先从缓存中读取Session对象，更改会话状态（RedissonSession会自动更新对应的缓存），以便客户端访问期间Session状态感知
            Session session = getSessionDao().readSession(sessionId);  
            if(session != null) {  
            	if(session instanceof SimpleOnlineSession) {
        			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
        			onlineSession.setStatus(OnlineStatus.FORCE_LOGOUT);
        		}
                session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);  
            }
            // 2、最终手动删除
            getSessionDao().delete(session);
            return true;
        } catch (Exception e) {/*ignore*/
        	return false;
        }  
	}  
	
	@Override
	public int offline(String sessionId) {
		
		getRedisOperationTemplate().setBit(RedisKeyGenerator.getUserSessionState(), 0, false);

		return 1;
	}

	@Override
	public int online(OnlineSessionModel onlineSession) {
		
		getRedisOperationTemplate().setBit(RedisKeyGenerator.getUserSessionState(), 0, true);
		
		return 1; 
	}
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}
	
}
