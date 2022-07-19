-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_config`;
CREATE TABLE `sys_data_config` (
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置消息主键ID',
    `corp_id`     varchar(200) NOT NULL COMMENT ' 钉钉机构 CropId ',
    `corp_secret` varchar(200) NOT NULL COMMENT ' 钉钉机构密钥 ',
    `dept_id`     bigint(11) NOT NULL COMMENT ' 组织机构ID ',
    `config_type` varchar(255) DEFAULT NULL COMMENT ' 配置类型 ',
    `is_delete` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除（0:未删除,1:已删除）',
    `creator` bigint(12) DEFAULT 0 COMMENT '创建人ID',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp(0) NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统配置信息';

-- ----------------------------
-- Table structure for sys_data_config_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_config_item`;
CREATE TABLE `sys_data_config_item`  (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置项主键ID',
     `config_id` varchar(200) NOT NULL COMMENT '配置信息 ID',
     `config_type` varchar(200) DEFAULT NULL COMMENT '配置项分类',
     `config_key` varchar(200) NOT NULL COMMENT '配置项名称，如：AppKey',
     `config_value` varchar(200) NOT NULL COMMENT '配置项值，如：xxxxx12645645',
     `is_delete` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除（0:未删除,1:已删除）',
     `creator` bigint(12) DEFAULT 0 COMMENT '创建人ID',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
     `modify_time` timestamp(0) NOT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `idx_config_id`(`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '三方集成配置信息项';
