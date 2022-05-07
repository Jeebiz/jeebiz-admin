/*
 * Copyright (c) 2018 (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.hiwepy.admin.extras.monitor.setup.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import lombok.extern.slf4j.Slf4j;
import io.hiwepy.admin.extras.monitor.dao.OnlineSessionMapper;
import io.hiwepy.admin.extras.monitor.dao.entities.OnlineSessionEntity;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;

/**
 * 会话监听
 * @author <a href="https://github.com/hiwepy">hiwepy</a>
 */
@Slf4j
@Component
public class ShiroSessionListener extends SessionListenerAdapter {

	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private RedisOperationTemplate redisOperation;
	@Autowired
	private OnlineSessionMapper sessionMapper;

	/**
	 * 会话开始
	 */
	@Override
	public void onStart(Session session) {

		log.info("创建session:(" + session.getId() + "," + session.getHost() + ")");
		String userSessionsKey = BizRedisKey.USER_SESSIONS.getKey();
		redisOperation.zAdd(userSessionsKey, session.getId(), session.getStartTimestamp().getTime());

		OnlineSessionEntity onlineSessionEntity = OnlineSessionEntity.builder()
				.sessionId(session.getId().toString())
				.startTimestamp(session.getStartTimestamp())
				.timeout(session.getTimeout())
				.status("1")
				.build();
		sessionMapper.insert(onlineSessionEntity);

	}

	/**
	 * 会话结束
	 */
	@Override
	public void onStop(Session session) {
		log.info("结束session:(" + session.getId() + "," + session.getHost() + ")");

		OnlineSessionEntity onlineSessionEntity = OnlineSessionEntity.builder()
				.sessionId(session.getId().toString())
				.lastAccessTime(session.getLastAccessTime())
				.build();

		sessionMapper.update(onlineSessionEntity, new UpdateWrapper<OnlineSessionEntity>().eq("s_sessionId", session.getId()));

	}

	/**
	 * 会话过期
	 */
	@Override
	public void onExpiration(Session session) {
		log.info("过期session:(" + session.getId() + "," + session.getHost() + ")");
		String userSessionsKey = BizRedisKey.USER_SESSIONS.getKey();
		redisOperation.zRem(userSessionsKey, session.getId());

		OnlineSessionEntity onlineSessionEntity = OnlineSessionEntity.builder()
				.sessionId(session.getId().toString())
				.status("0")
				.build();

		sessionMapper.update(onlineSessionEntity, new UpdateWrapper<OnlineSessionEntity>().eq("s_sessionId", session.getId()));

	}

}
