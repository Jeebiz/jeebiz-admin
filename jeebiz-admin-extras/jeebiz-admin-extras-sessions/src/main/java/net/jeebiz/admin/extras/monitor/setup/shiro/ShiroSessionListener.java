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
package net.jeebiz.admin.extras.monitor.setup.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.monitor.dao.SessionMapper;
import net.jeebiz.admin.extras.monitor.dao.entities.SessionEntity;
import net.jeebiz.boot.extras.redis.setup.RedisKey;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

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
	private SessionMapper sessionMapper;

	/**
	 * 会话开始
	 */
	@Override
	public void onStart(Session session) {

		log.info("创建session:(" + session.getId() + "," + session.getHost() + ")");
		String userSessionsKey = RedisKey.USER_SESSIONS.getKey();
		redisOperation.zAdd(userSessionsKey, session.getId(), session.getStartTimestamp().getTime());

		SessionEntity sessionEntity = SessionEntity.builder()
				.sessionId(session.getId().toString())
				.startTimestamp(session.getStartTimestamp())
				.timeout(session.getTimeout())
				.status("1")
				.build();
		sessionMapper.insert(sessionEntity);

	}

	/**
	 * 会话结束
	 */
	@Override
	public void onStop(Session session) {
		log.info("结束session:(" + session.getId() + "," + session.getHost() + ")");

		SessionEntity sessionEntity = SessionEntity.builder()
				.sessionId(session.getId().toString())
				.lastAccessTime(session.getLastAccessTime())
				.build();

		sessionMapper.update(sessionEntity, new UpdateWrapper<SessionEntity>().eq("s_sessionId", session.getId()));

	}

	/**
	 * 会话过期
	 */
	@Override
	public void onExpiration(Session session) {
		log.info("过期session:(" + session.getId() + "," + session.getHost() + ")");
		String userSessionsKey = RedisKey.USER_SESSIONS.getKey();
		redisOperation.zRem(userSessionsKey, session.getId());

		SessionEntity sessionEntity = SessionEntity.builder()
				.sessionId(session.getId().toString())
				.status("0")
				.build();

		sessionMapper.update(sessionEntity, new UpdateWrapper<SessionEntity>().eq("s_sessionId", session.getId()));

	}

}
