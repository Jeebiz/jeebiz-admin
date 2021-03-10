/* 
 * 权限核心表：
 * 2、用户信息表、角色信息表、用户-角色关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- ----------------------------
-- Table structure for SYS_AUTHZ_ROLE_LIST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ROLE_LIST`;
CREATE TABLE `SYS_AUTHZ_ROLE_LIST` (
  `R_ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `R_KEY` varchar(50) NOT NULL COMMENT '角色编码',
  `R_NAME` varchar(50) NOT NULL COMMENT '角色名称',
  `R_TYPE` int(1) NOT NULL COMMENT '角色类型（1:原生|2:继承|3:复制|4:自定义）',
  `R_INTRO` varchar(1000) NOT NULL COMMENT '角色简介',
  `R_STATUS` int(1) NOT NULL DEFAULT '1' COMMENT '角色状态（0:禁用|1:可用）',
  `R_TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始化时间',
  PRIMARY KEY (`R_ID`),
  UNIQUE KEY (`R_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ROLE_PERMITS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ROLE_PERMS`;
CREATE TABLE `SYS_AUTHZ_ROLE_PERMS` (
  `R_ID` bigint(11) NOT NULL COMMENT '角色ID',
  `PERMS` varchar(50) NOT NULL COMMENT '权限标记：(等同SYS_AUTHZ_FEATURE_OPTS.OPT_PERMS)',
  PRIMARY KEY (`R_ID`,`PERMS`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关系表（角色-菜单-按钮）';

-- ----------------------------
-- Table structure for SYS_AUTHZ_USER_LIST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_USER_LIST`;
CREATE TABLE `SYS_AUTHZ_USER_LIST` (
  `U_ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `U_USERNAME` varchar(100) NOT NULL COMMENT '用户名',
  `U_PASSWORD` varchar(100) NOT NULL COMMENT '用户密码',
  `U_SALT` varchar(64) DEFAULT NULL COMMENT '用户密码盐：用于密码加解密',
  `U_SECRET` varchar(128) DEFAULT NULL COMMENT '用户秘钥：用于用户JWT加解密',
  `U_UID` varchar(16) COMMENT '用户唯一UID（用户编号）',
  `U_CODE` varchar(50) COMMENT '用户唯一编号（内部工号）',
  `U_APP_ID` varchar(50) DEFAULT NULL COMMENT '用户客户端应用ID',
  `U_APP_CHANNEL` varchar(20) DEFAULT NULL COMMENT '用户客户端应用渠道编码',
  `U_APP_VERSION` varchar(20) DEFAULT NULL COMMENT '用户客户端版本',
  `U_ONLINE` smallint(2) DEFAULT '0' COMMENT '用户是否在线（1：是，0：否）',
  `U_LATEST_ONLINE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户最近一次登录时间',
  `U_STATUS` int(1) DEFAULT NULL COMMENT '用户状态（0:禁用|1:可用|2:锁定）',
  `U_TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始化时间',
  PRIMARY KEY (`U_ID`),
  UNIQUE KEY `idx_uname` (`U_USERNAME`),
  UNIQUE KEY `idx_uuid` (`U_UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户信息表';


-- ----------------------------
-- Table structure for SYS_AUTHZ_USER_PROFILE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_USER_PROFILE`;
CREATE TABLE `SYS_AUTHZ_USER_PROFILE` (
  `U_PID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户描述ID',
  `U_ID` bigint(11) NOT NULL COMMENT '用户ID',
  `U_NICKNAME` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `U_AVATAR` varchar(500) DEFAULT NULL COMMENT '用户头像：图片路径或图标样式',
  `U_COUNTRY_CODE` varchar(20) DEFAULT NULL COMMENT '手机号码国家码',
  `U_PHONE` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `U_EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `U_BIRTHDAY` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `U_GENDER` smallint(2) DEFAULT NULL COMMENT '性别：（1：男，2：女）',
  `U_IDCARD` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `U_AGE` int(3) DEFAULT NULL COMMENT '用户年龄',
  `U_HEIGHT` varchar(20) DEFAULT NULL COMMENT '用户身高',
  `U_WEIGHT` varchar(20) DEFAULT NULL COMMENT '用户体重',
  `U_LANGUAGE` varchar(50) DEFAULT NULL COMMENT '官方语言',  
  `U_INTRO` varchar(500) DEFAULT NULL COMMENT '用户简介',
  `U_PHOTOS` varchar(2000) DEFAULT NULL COMMENT '个人生活照片（包含是否封面标记、序号、地址的JSON字符串）',
  `U_PROVINCE` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻省份',
  `U_CITY` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻城市',
  `U_AREA` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻区域',
  `U_WGS84_LNG` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84经度',
  `U_WGS84_LAT` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84纬度',
  `U_DEGREE` int(3) DEFAULT '0' COMMENT '用户信息完成度',
  `U_TIME24` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '初始化时间',
  PRIMARY KEY (`U_PID`),
  UNIQUE KEY `idx_uid` (`U_ID`) USING BTREE,
  UNIQUE KEY `idx_phone` (`U_PHONE`) USING BTREE,
  UNIQUE KEY `idx_nickname` (`U_NICKNAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户描述信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_USER_ROLE_RELATION
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_USER_ROLE_RELATION`;
CREATE TABLE `SYS_AUTHZ_USER_ROLE_RELATION` (
  `U_ID` bigint(11) NOT NULL COMMENT '用户ID',
  `R_ID` bigint(11) NOT NULL COMMENT '角色ID',
  `R_PRTY` int(2) NOT NULL DEFAULT 0 COMMENT '优先级：用于默认登录角色',
  PRIMARY KEY (`U_ID`,`R_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';
