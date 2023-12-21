/*
 * 权限核心表：
 * 2、用户信息表、角色信息表、用户-角色关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- ----------------------------
-- Table structure for t_role_list
-- ----------------------------
DROP TABLE IF EXISTS `t_role_list`;
CREATE TABLE `t_role_list` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `key` varchar(50) NOT NULL COMMENT '角色编码',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `type` int(1) NOT NULL COMMENT '角色类型（1:原生|2:继承|3:复制|4:自定义）',
  `intro` varchar(1000) NOT NULL COMMENT '角色简介',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '角色状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for t_role_perms
-- ----------------------------
DROP TABLE IF EXISTS `t_role_perms`;
CREATE TABLE `t_role_perms` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `role_key` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `perms` varchar(50) NOT NULL COMMENT '权限标记：(等同t_feature_opts.opt_perms)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`role_id`,`perms`) USING BTREE COMMENT '角色权限索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关系表（角色-菜单-按钮）';

-- ----------------------------
-- Table structure for t_user_account
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account` varchar(100) NOT NULL COMMENT '账号名称',
  `password` varchar(100) NOT NULL COMMENT '账号密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '账号密码盐：用于密码加解密',
  `secret` varchar(128) DEFAULT NULL COMMENT '账号秘钥：用于用户JWT加解密',
  `user_id` varchar(16) COMMENT '关联用户id',
  `user_code` varchar(50) COMMENT '关联用户code（短号/工号）',
  `type` varchar(50) COMMENT '登录方式（如：password：账号密码、weixin:微信登录...）',
  `rawdata` varchar(500) COMMENT '第三方认证账号扩展信息',
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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id`,`user_code`,`account`) USING BTREE COMMENT '用户账号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户信息表';

-- ----------------------------
-- Table structure for t_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `code` varchar(50) COMMENT '用户code（短号/工号）',
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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_profile`(`code`,`nickname`) USING BTREE COMMENT '用户详情索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详情信息表';

-- ----------------------------
-- Table structure for t_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `t_user_roles`;
CREATE TABLE `t_user_roles` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(12) NOT NULL COMMENT '用户id',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `priority` int(2) NOT NULL DEFAULT 0 COMMENT '优先级：用于默认登录角色',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_role`(`user_id`,`role_id`,`priority`) USING BTREE COMMENT '用户角色关系索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';
