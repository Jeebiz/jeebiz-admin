/* 
 * 权限核心表：
 * 2、用户信息表、角色信息表、用户-角色关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- ----------------------------
-- Table structure for sys_authz_role_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_role_list`;
CREATE TABLE `sys_authz_role_list` (
  `r_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `r_key` varchar(50) NOT NULL COMMENT '角色编码',
  `r_name` varchar(50) NOT NULL COMMENT '角色名称',
  `r_type` int(1) NOT NULL COMMENT '角色类型（1:原生|2:继承|3:复制|4:自定义）',
  `r_intro` varchar(1000) NOT NULL COMMENT '角色简介',
  `r_status` int(1) NOT NULL DEFAULT '1' COMMENT '角色状态（0:禁用|1:可用）',
  `R_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始化时间',
  PRIMARY KEY (`r_id`),
  UNIQUE KEY (`r_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ROLE_PERMITS
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_role_perms`;
CREATE TABLE `sys_authz_role_perms` (
  `r_id` bigint(12) NOT NULL COMMENT '角色id',
  `perms` varchar(50) NOT NULL COMMENT '权限标记：(等同sys_authz_feature_opts.opt_perms)',
  PRIMARY KEY (`r_id`,`perms`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关系表（角色-菜单-按钮）';

-- ----------------------------
-- Table structure for sys_authz_user_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_list`;
CREATE TABLE `sys_authz_user_list` (
  `u_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `u_username` varchar(100) NOT NULL COMMENT '用户名',
  `u_password` varchar(100) NOT NULL COMMENT '用户密码',
  `u_salt` varchar(64) DEFAULT NULL COMMENT '用户密码盐：用于密码加解密',
  `u_secret` varchar(128) DEFAULT NULL COMMENT '用户秘钥：用于用户JWT加解密',
  `u_uid` varchar(16) COMMENT '用户唯一Uid（用户编号）',
  `u_code` varchar(50) COMMENT '用户唯一编号（内部工号）',
  `u_app_id` varchar(50) DEFAULT NULL COMMENT '用户客户端应用id',
  `u_app_channel` varchar(20) DEFAULT NULL COMMENT '用户客户端应用渠道编码',
  `u_app_version` varchar(20) DEFAULT NULL COMMENT '用户客户端版本',
  `u_online` tinyint(2) DEFAULT '0' COMMENT '用户是否在线（1：是，0：否）',
  `u_latest_online` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '用户最近一次登录时间',
  `u_status` tinyint(2) DEFAULT NULL COMMENT '用户状态（0:禁用|1:可用|2:锁定）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `idx_uname` (`u_username`),
  UNIQUE KEY `idx_uuid` (`u_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户信息表';


-- ----------------------------
-- Table structure for sys_authz_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_profile`;
CREATE TABLE `sys_authz_user_profile` (
  `u_pid` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户描述id',
  `u_id` bigint(12) NOT NULL COMMENT '用户id',
  `u_nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `u_avatar` varchar(500) DEFAULT NULL COMMENT '用户头像：图片路径或图标样式',
  `u_country_code` varchar(20) DEFAULT NULL COMMENT '手机号码国家码',
  `u_phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `u_email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `u_birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `u_gender` smallint(2) DEFAULT NULL COMMENT '性别：（1：男，2：女）',
  `u_idcard` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `u_age` int(3) DEFAULT NULL COMMENT '用户年龄',
  `u_height` varchar(20) DEFAULT NULL COMMENT '用户身高',
  `u_weight` varchar(20) DEFAULT NULL COMMENT '用户体重',
  `u_language` varchar(50) DEFAULT NULL COMMENT '官方语言',
  `u_intro` varchar(500) DEFAULT NULL COMMENT '用户简介',
  `u_photos` varchar(2000) DEFAULT NULL COMMENT '个人生活照片（包含是否封面标记、序号、地址的JSON字符串）',
  `u_country` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻国家',
  `u_province` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻省份',
  `u_city` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻城市',
  `u_area` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻区域',
  `u_wgs84_lng` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84经度',
  `u_wgs84_lat` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84纬度',
  `u_degree` int(3) DEFAULT '0' COMMENT '用户信息完成度',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`u_pid`),
  UNIQUE KEY `idx_uid` (`u_id`) USING BTREE,
  UNIQUE KEY `idx_phone` (`u_phone`) USING BTREE,
  UNIQUE KEY `idx_nickname` (`u_nickname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户描述信息表';

-- ----------------------------
-- Table structure for sys_authz_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_role_relation`;
CREATE TABLE `sys_authz_user_role_relation` (
  `u_id` bigint(12) NOT NULL COMMENT '用户id',
  `r_id` bigint(12) NOT NULL COMMENT '角色id',
  `R_PRTY` int(2) NOT NULL DEFAULT 0 COMMENT '优先级：用于默认登录角色',
  PRIMARY KEY (`u_id`,`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';
