
-- ----------------------------
-- Table structure for SYS_DATA_LOG_AUTHZ
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_LOG_AUTHZ`;
CREATE TABLE `SYS_DATA_LOG_AUTHZ` (
  `LOG_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '日志id编号',
  `LOG_OPT` varchar(50) NOT NULL COMMENT '操作类型：login、logout',
  `LOG_PROTOCOL` varchar(100) NOT NULL COMMENT '认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等',
  `LOG_ADDR` varchar(50) DEFAULT NULL COMMENT '认证请求来源IP地址',
  `LOG_LOCATION` varchar(200) DEFAULT NULL COMMENT '认证请求来源IP所在地点',
  `LOG_STATUS` varchar(10) DEFAULT NULL COMMENT '认证结果：fail、success',
  `LOG_MSG` varchar(500) DEFAULT NULL COMMENT '认证请求信息',
  `LOG_EXCP` tinytext COMMENT '认证异常信息',
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '认证发生时间',
  `CREATOR` bigint(12) DEFAULT NULL COMMENT '认证对象id',
  PRIMARY KEY (`LOG_id`),
  KEY `idx_uid` (`CREATOR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证授权记录表';

-- ----------------------------
-- Table structure for SYS_DATA_LOG_BIZ
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_LOG_BIZ`;
CREATE TABLE `SYS_DATA_LOG_BIZ` (
  `LOG_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '日志id编号',
  `LOG_MODULE` varchar(50) NOT NULL COMMENT '功能模块',
  `LOG_BIZ` varchar(100) NOT NULL COMMENT '业务名称',
  `LOG_OPT` varchar(50) NOT NULL COMMENT '操作类型',
  `LOG_ADDR` varchar(50) DEFAULT NULL COMMENT '功能操作请求来源IP地址',
  `LOG_MSG` varchar(500) COMMENT '功能操作信息',
  `LOG_EXCP` tinytext COMMENT '功能操作异常',
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '功能操作发生时间',
  `CREATOR` bigint(12) DEFAULT NULL COMMENT '功能操作人id',
  PRIMARY KEY (`LOG_id`),
  KEY `idx_uid` (`CREATOR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能操作日志信息表';
