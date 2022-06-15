
-- ----------------------------
-- Table structure for sys_inform_templates
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_templates`;
CREATE TABLE `sys_inform_templates` (
    `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息通知模板id',
    `name` varchar(200) NOT NULL COMMENT '消息通知模板名称',
    `channel` varchar(50) NOT NULL COMMENT '消息通知模板类型',
    `title` varchar(200) COMMENT '消息通知标题（可能包含变量）',
    `sign` varchar(20) COMMENT '消息通知签名（短信消息模板需要使用）',
    `content` varchar(500) NOT NULL COMMENT '消息通知内容（可能包含变量）',
    `template_id` varchar(100) COMMENT '消息模板对应第三方平台内的模板id',
    `payload` varchar(500) COMMENT '消息模板变量载体,JOSN格式的数据',
    `status` tinyint(2) DEFAULT '0' COMMENT '消息通知模板状态（0:停用、1:启用）',
    `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
    `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
    `modify_time` timestamp(0) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板信息表';

-- ----------------------------
-- Table structure for sys_inform_events
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_events`;
CREATE TABLE `sys_inform_events` (
    `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息通知事件id',
    `type` varchar(200) NOT NULL COMMENT '消息通知事件类型',
    `channel` varchar(50) NOT NULL COMMENT '消息通知事件行为（处理通道）',
    `template_id` bigint(12)  NOT NULL COMMENT '消息通知事件关联模板id',
    `intro` varchar(500) COMMENT '消息通知事件说明',
    `status` tinyint(2) DEFAULT '0' COMMENT '消息通知事件状态：（0:停用、1:启用）',
    `target` varchar(50) COMMENT '消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）',
    `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
    `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
    `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
    `modify_time` timestamp(0) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_inform_event` (`type`, `channel`, `template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板信息表';

-- ----------------------------
-- Table structure for sys_inform_targets
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_targets`;
CREATE TABLE `sys_inform_targets` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息通知对象主键id',
  `from_id` bigint(12) NOT NULL COMMENT '消息通知事件id',
  `to_type` varchar(50) NOT NULL COMMENT '消息通知事件通知对象类型：（ORG:组织机构、ROLE:角色、POST：岗位、USER：人员）',
  `to_id` bigint(12) NOT NULL COMMENT '消息通知接收对象id',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
  `modify_time` timestamp(0) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inform_target` (`from_id`,`to_type`, `to_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知对象表';

-- ----------------------------
-- Table structure for sys_inform_records
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_records`;
CREATE TABLE `sys_inform_records` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息通知记录id',
  `from_type` varchar(50) NOT NULL COMMENT '消息通知发送人id',
  `from_id` bigint(12) NOT NULL COMMENT '消息通知发送人id/事件Id',
  `channel` varchar(50) NOT NULL COMMENT '消息通知发送通道',
  `title` varchar(200) COMMENT '消息通知标题',
  `content` varchar(500) NOT NULL COMMENT '消息通知内容',
  `template_id` bigint(12) COMMENT '消息通知对应平台内的模板id',
  `payload` varchar(500) COMMENT '消息通知变量载体,JOSN格式的数据',
  `receiver_id` bigint(12) COMMENT '消息通知接收人id',
  `flow_no` varchar(50) COMMENT '消息通知处理流水编号',
  `biz_id` varchar(20) COMMENT '消息通知关联业务id',
  `status` tinyint(2) DEFAULT '0' COMMENT '消息通知阅读状态：（0:未阅读、1:已阅读）',
  `app_id` varchar(50) COMMENT '客户端应用ID',
  `app_channel` varchar(50) COMMENT '客户端应用渠道',
  `app_version` varchar(50) COMMENT '客户端版本',
  `route` varchar(500) COMMENT '消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '消息通知创建人id',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '消息通知创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '消息通知更新人id',
  `modify_time` timestamp(0) ON UPDATE CURRENT_TIMESTAMP COMMENT '消息通知更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inform_record` (`receiver_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';
