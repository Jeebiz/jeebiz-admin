
-- ----------------------------
-- Table structure for $${table-prefix}device_activate
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}device_activate`;
CREATE TABLE `$${table-prefix}device_activate` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(11) DEFAULT NULL COMMENT '设备安装的客户端应用ID',
  `app_channel` varchar(255) DEFAULT NULL COMMENT '设备安装的客户端应用渠道编码',
  `app_version` varchar(20) DEFAULT NULL COMMENT '设备安装的客户端版本',
  `device_idfa` varchar(50) DEFAULT NULL COMMENT 'IOS 6+的设备id字段，32位',
  `device_idfa_md5` varchar(50) DEFAULT NULL COMMENT 'IOS 6+的设备id字段的md5摘要，32位',
  `device_openudid` varchar(50) DEFAULT NULL COMMENT 'IOS设备的识别码',
  `device_openudid_md5` varchar(50) DEFAULT NULL COMMENT 'IOS设备的识别码的md5摘要，32位',
  `device_androidid` varchar(50) DEFAULT NULL COMMENT '安卓id原值的md5，32位',
  `device_androidid_md5` varchar(50) DEFAULT NULL COMMENT '安卓id原值的md5摘要，32位',
  `device_oaid` varchar(50) DEFAULT NULL COMMENT 'Android Q及更高版本的设备号，32位',
  `device_oaid_md5` varchar(50) DEFAULT NULL COMMENT 'Android Q及更高版本的设备号的md5摘要，32位',
  `device_imei` varchar(100) DEFAULT NULL COMMENT '设备唯一标识IMEI',
  `device_imei_md5` varchar(100) DEFAULT NULL COMMENT '安卓的设备 ID的md5摘要，32位',
  `device_model` varchar(50) NOT NULL COMMENT '设备型号（手机型号）',
  `device_mac` varchar(255) DEFAULT NULL COMMENT '移动设备mac地址,转换成大写字母,去掉“:”，并且取md5摘要后的结果',
  `device_ip` varchar(255) DEFAULT NULL COMMENT '媒体投放系统获取的用户终端的公共IP地址',
  `device_ua` varchar(2000) DEFAULT NULL COMMENT '用户代理(User Agent)，一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。',
  `status` tinyint(5) NOT NULL DEFAULT 1 COMMENT '是否可用（ 0：不可用，1：可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_imei` (`device_imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端激活数据表';

-- ----------------------------
-- Table structure for $${table-prefix}device_users
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}device_users`;
CREATE TABLE `$${table-prefix}device_users` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `device_id` bigint(12) DEFAULT NULL COMMENT '设备激活记录ID',
  `u_app_id` varchar(11) DEFAULT NULL COMMENT '用户登录的客户端应用ID',
  `u_app_channel` varchar(255) DEFAULT NULL COMMENT '用户登录的客户端应用渠道编码',
  `u_app_version` varchar(20) DEFAULT NULL COMMENT '用户登录的客户端版本',
  `is_delete` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_device` (`device_id`),
  KEY `idx_creator` (`CREATOR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备用户关联表';
