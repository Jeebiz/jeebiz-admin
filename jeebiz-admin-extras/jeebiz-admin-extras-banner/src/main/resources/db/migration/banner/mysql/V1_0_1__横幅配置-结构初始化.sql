
-- ----------------------------
-- Table structure for 我的应用信息表
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_myapp`;
CREATE TABLE `sys_data_bannermyapp` (
  `app_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '应用ID编号',
  `app_uid` varchar(50) NOT NULL COMMENT '应用UID',
  `app_name` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `app_intro` varchar(2000) DEFAULT NULL COMMENT '应用描述',
  `app_lang` varchar(50) DEFAULT NULL COMMENT '应用开发语言',
  `app_addr` varchar(500) DEFAULT NULL COMMENT '应用部署地址',
  `app_key` varchar(100) COMMENT '应用Key',
  `app_secret` varchar(500) COMMENT '应用Secret',
  `app_userid` varchar(32) DEFAULT NULL COMMENT '应用所属人ID',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='我的应用信息表';
