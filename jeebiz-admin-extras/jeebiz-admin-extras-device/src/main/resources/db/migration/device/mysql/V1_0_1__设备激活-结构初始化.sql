
-- ----------------------------
-- Table structure for SYS_DATA_DEVICE_ACTIVATE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_DEVICE_ACTIVATE`;
CREATE TABLE `SYS_DATA_DEVICE_ACTIVATE` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `APP_id` varchar(11) DEFAULT NULL COMMENT '设备安装的客户端应用id',
  `APP_CHANNEL` varchar(255) DEFAULT NULL COMMENT '设备安装的客户端应用渠道编码',
  `APP_VERSION` varchar(20) DEFAULT NULL COMMENT '设备安装的客户端版本',
  `DEVICE_IMEI` varchar(255) DEFAULT NULL COMMENT '设备唯一标识IMEI',
  `DEVICE_MODEL` varchar(50) NOT NULL COMMENT '设备型号（手机型号）',
  `CREATOR` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFYER` bigint(12) DEFAULT NULL COMMENT '修改人id',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_imei` (`DEVICE_IMEI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端设备激活表';

-- ----------------------------
-- Table structure for SYS_DATA_DEVICE_USERS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_DEVICE_USERS`;
CREATE TABLE `SYS_DATA_DEVICE_USERS` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ACTIVATEd_id` bigint(11) DEFAULT NULL COMMENT '设备激活记录id',
  `APP_id` varchar(11) DEFAULT NULL COMMENT '用户登录的客户端应用id',
  `APP_CHANNEL` varchar(255) DEFAULT NULL COMMENT '用户登录的客户端应用渠道编码',
  `APP_VERSION` varchar(20) DEFAULT NULL COMMENT '用户登录的客户端版本',
  `CREATOR` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFYER` bigint(12) DEFAULT NULL COMMENT '修改人id',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_activated` (`ACTIVATEd_id`),
  KEY `idx_creator` (`CREATOR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备用户关联表';
