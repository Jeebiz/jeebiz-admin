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
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`r_id`),
  UNIQUE KEY (`r_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ROLE_PERMITS
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_role_perms`;
CREATE TABLE `sys_authz_role_perms` (
  `r_id` bigint(12) NOT NULL COMMENT '角色id',
  `r_key` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `perms` varchar(50) NOT NULL COMMENT '权限标记：(等同sys_authz_feature_opts.opt_perms)',
  PRIMARY KEY (`r_id`,`perms`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关系表（角色-菜单-按钮）';

-- ----------------------------
-- Table structure for sys_authz_user_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_account`;
CREATE TABLE `sys_authz_user_account` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(100) NOT NULL COMMENT '账号名称',
  `password` varchar(100) NOT NULL COMMENT '账号密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '账号密码盐：用于密码加解密',
  `secret` varchar(128) DEFAULT NULL COMMENT '账号秘钥：用于用户JWT加解密',
  `user_id` varchar(16) COMMENT '关联用户id',
  `user_code` varchar(50) COMMENT '关联用户code（短号/工号）',
  `type` varchar(50) COMMENT '登录方式（如：password：账号密码、weixin:微信登录...）',
  `app_id` varchar(50) DEFAULT NULL COMMENT '账号最近一次登录客户端应用id',
  `app_channel` varchar(20) DEFAULT NULL COMMENT '账号最近一次登录客户端应用渠道编码',
  `app_version` varchar(20) DEFAULT NULL COMMENT '账号最近一次登录客户端版本',
  `is_online` tinyint(2) DEFAULT '0' COMMENT '用户是否在线（1：是，0：否）',
  `latest_online` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '账号最近一次登录时间',
  `status` tinyint(2) DEFAULT NULL COMMENT '账号状态（0:禁用|1:可用|2:锁定）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uname` (`username`),
  UNIQUE KEY `idx_uuid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户信息表';

-- ----------------------------
-- Table structure for sys_authz_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_profile`;
CREATE TABLE `sys_authz_user_profile` (
  `user_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` varchar(50) COMMENT '用户code（短号/工号）',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '用户头像：图片路径或图标样式',
  `region_code` varchar(20) DEFAULT NULL COMMENT '国家/地区编码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `gender` smallint(2) DEFAULT NULL COMMENT '性别：（1：男，2：女）',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `age` int(3) DEFAULT NULL COMMENT '用户年龄',
  `height` varchar(20) DEFAULT NULL COMMENT '用户身高',
  `weight` varchar(20) DEFAULT NULL COMMENT '用户体重',
  `language` varchar(50) DEFAULT NULL COMMENT '官方语言',
  `intro` varchar(500) DEFAULT NULL COMMENT '用户简介',
  `photos` varchar(2000) DEFAULT NULL COMMENT '个人生活照片（包含是否封面标记、序号、地址的JSON字符串）',
  `country` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻国家',
  `province` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻省份',
  `city` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻城市',
  `area` varchar(50) DEFAULT NULL COMMENT '用户位置：常驻区域',
  `wgs84_lng` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84经度',
  `wgs84_lat` decimal(10,6) DEFAULT NULL COMMENT '用户位置：wgs84纬度',
  `degree` int(3) DEFAULT '0' COMMENT '用户信息完成度',
  `app_id` varchar(50) DEFAULT NULL COMMENT '注册客户端应用id',
  `app_channel` varchar(20) DEFAULT NULL COMMENT '注册客户端应用渠道编码',
  `app_version` varchar(20) DEFAULT NULL COMMENT '注册客户端版本',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_user` (`user_code`) USING BTREE,
  UNIQUE KEY `idx_nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详情信息表';

-- ----------------------------
-- Table structure for sys_authz_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_role_relation`;
CREATE TABLE `sys_authz_user_role_relation` (
  `u_id` bigint(12) NOT NULL COMMENT '用户id',
  `r_id` bigint(12) NOT NULL COMMENT '角色id',
  `r_prty` int(2) NOT NULL DEFAULT 0 COMMENT '优先级：用于默认登录角色',
  PRIMARY KEY (`u_id`,`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';
