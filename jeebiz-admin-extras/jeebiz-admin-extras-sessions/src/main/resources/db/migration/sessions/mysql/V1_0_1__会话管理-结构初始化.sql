

-- ----------------------------
-- Table structure for sys_user_sessions
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_sessions`;
CREATE TABLE `sys_user_sessions` (
  `s_id` varchar(50) NOT NULL COMMENT '用户会话id编号',
  `s_uid` varchar(32) NOT NULL COMMENT '当前登录的用户Id',
  `s_uname` varchar(100) NOT NULL COMMENT '当前登录的用户名称',
  `s_sessionId` varchar(200) NOT NULL COMMENT '用户会话UUID',
  `s_host` varchar(200)  NOT NULL COMMENT '用户主机地址',
  `s_start_timestamp` varchar(50) NOT NULL COMMENT '用户登录时间',
  `s_last_access_time` varchar(200) NOT NULL COMMENT '最后访问时间',
  `s_ua` varchar(500) NOT NULL COMMENT '用户浏览器类型',
  `s_address` varchar(100) NOT NULL COMMENT '用户登录时系统IP',
  `s_session` blob NOT NULL COMMENT 'Session对象',
  `s_force_logout` bigint(12) NOT NULL COMMENT '会话多久后过期（毫秒） ',
  `s_timeout` bigint(12) NOT NULL COMMENT '会话多久后过期（毫秒） ',
  `s_status` tinyint(2) NOT NULL COMMENT '在线状态（0：离线，1：在线）',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户会话信息表';
