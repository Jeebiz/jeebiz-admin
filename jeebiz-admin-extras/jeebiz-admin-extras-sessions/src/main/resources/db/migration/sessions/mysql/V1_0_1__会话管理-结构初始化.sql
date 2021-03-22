
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_EXTRAS_SESSIONS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_EXTRAS_SESSIONS`;
CREATE TABLE `SYS_EXTRAS_SESSIONS` (
  `S_ID` varchar(50) NOT NULL COMMENT '用户会话ID编号',
  `S_USERID` varchar(32) NOT NULL COMMENT '当前登录的用户Id',
  `S_USERNAME` varchar(100) NOT NULL COMMENT '当前登录的用户名称',
  `S_HOST` text NOT NULL COMMENT '用户主机地址',
  `S_START_TIMESTAMP` varchar(50) NOT NULL COMMENT '用户登录时间',
  `S_LASTACCESS_TIME` varchar(200) NOT NULL COMMENT '最后访问时间',
  `S_USERAGENT` varchar(500) NOT NULL COMMENT '用户浏览器类型',
  `S_SYSTEM_HOST` varchar(500) NOT NULL COMMENT '用户登录时系统IP',
  `S_SESSION` blob NOT NULL COMMENT 'Session对象',
  `S_TIMEOUT` varchar(500) NOT NULL COMMENT '会话多久后过期（毫秒） ',
  `S_STATUS` varchar(20) NOT NULL COMMENT '在线状态',
  PRIMARY KEY (`S_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户会话信息表';
