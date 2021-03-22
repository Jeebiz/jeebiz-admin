
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_EXTRAS_LOG_AUTHZ
-- ----------------------------
DROP TABLE IF EXISTS `SYS_EXTRAS_LOG_AUTHZ`;
CREATE TABLE `SYS_EXTRAS_LOG_AUTHZ` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID编号',
  `LOG_USERID` int(11) NOT NULL COMMENT '认证对象ID',
  `LOG_OPT` varchar(50) NOT NULL COMMENT '操作类型：login、logout',
  `LOG_PROTOCOL` varchar(100) NOT NULL COMMENT '认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等',
  `LOG_REALM` varchar(50) NOT NULL COMMENT '负责此次认证的realm名称',
  `LOG_LEVEL` varchar(10) DEFAULT NULL COMMENT '日志级别：trace、debug、info、warn、error、fetal',
  `LOG_ADDR` varchar(50) DEFAULT NULL COMMENT '认证请求来源IP地址',
  `LOG_STATUS` varchar(10) DEFAULT NULL COMMENT '认证结果：fail、success',
  `LOG_MSG` varchar(500) COMMENT '认证请求信息',
  `LOG_EXCP` tinytext COMMENT '认证异常信息',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '认证发生时间',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证授权日志信息表';

-- ----------------------------
-- Table structure for SYS_EXTRAS_LOG_BIZ
-- ----------------------------
DROP TABLE IF EXISTS `SYS_EXTRAS_LOG_BIZ`;
CREATE TABLE `SYS_EXTRAS_LOG_BIZ` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID编号',
  `LOG_MODULE` varchar(50) NOT NULL COMMENT '功能模块',
  `LOG_BIZ` varchar(100) NOT NULL COMMENT '业务名称',
  `LOG_OPT` varchar(50) NOT NULL COMMENT '操作类型',
  `LOG_LEVEL` varchar(10) DEFAULT NULL COMMENT '日志级别：trace、debug、info、warn、error、fetal',
  `LOG_USERID` int(11) NOT NULL COMMENT '功能操作人ID',
  `LOG_ADDR` varchar(50) DEFAULT NULL COMMENT '功能操作请求来源IP地址',
  `LOG_MSG` varchar(500) COMMENT '功能操作信息',
  `LOG_EXCP` tinytext COMMENT '功能操作异常',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '功能操作发生时间',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能操作日志信息表';

-- ----------------------------
-- Table structure for SYS_EXTRAS_LOG_EXCP
-- ----------------------------
DROP TABLE IF EXISTS `SYS_EXTRAS_LOG_EXCP`;
CREATE TABLE `SYS_EXTRAS_LOG_EXCP` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID编号',
  `LOG_USERID` int(11) NOT NULL COMMENT '系统异常操作人ID',
  `LOG_CPMT` varchar(100) NOT NULL COMMENT '系统异常发生源类型',
  `LOG_CLASS` varchar(100) NOT NULL COMMENT '系统异常对象',
  `LOG_TYPE` varchar(50) NOT NULL COMMENT '系统异常类型',
  `LOG_LEVEL` varchar(10) DEFAULT NULL COMMENT '日志级别：trace、debug、info、warn、error、fetal',
  `LOG_ADDR` varchar(50) DEFAULT NULL COMMENT '导致系统异常的请求来源IP地址',
  `LOG_CODE` varchar(50) NOT NULL COMMENT '系统异常代码',
  `LOG_MSG` varchar(500) NOT NULL COMMENT '系统异常描述',
  `LOG_EXCP` tinytext COMMENT '系统异常信息',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统异常发生时间',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统异常日志信息表';

