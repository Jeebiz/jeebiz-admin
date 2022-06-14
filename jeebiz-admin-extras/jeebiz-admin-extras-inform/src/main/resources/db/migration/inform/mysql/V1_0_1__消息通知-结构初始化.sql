
-- ----------------------------
-- Table structure for sys_inform_templates
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_templates`;
CREATE TABLE `sys_inform_templates` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息模板id',
  `channel` varchar(50) NOT NULL COMMENT '消息通知模板类型',
  `title` varchar(200) NOT NULL COMMENT '消息模板标题（可能包含变量）',
  `content` varchar(2000) COMMENT '消息模板内容（可能包含变量）',
  `template_id` varchar(200) COMMENT '消息模板对应第三方平台内的模板id',
  `payload` varchar(2000) COMMENT '消息模板变量载体,JOSN格式的数据',
  `status` varchar(1) DEFAULT '0' COMMENT '消息模板状态：（0:停用、1:启用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
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
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY (`from_id`,`to_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知对象表';

-- ----------------------------
-- Table structure for sys_inform_records
-- ----------------------------
DROP TABLE IF EXISTS `sys_inform_records`;
CREATE TABLE `sys_inform_records` (
  `r_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息通知记录id',
  `r_uid` bigint(12) COMMENT '消息通知发送人id',
  `r_provider` varchar(50) NOT NULL COMMENT '消息通知的发送提供者',
  `r_tag` varchar(20) COMMENT '消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）',
  `r_title` varchar(200) NOT NULL COMMENT '消息通知标题（可能包含变量）',
  `r_content` text COMMENT '消息通知内容（可能包含变量）',
  `r_tid` varchar(200) COMMENT '消息通知模板id（系统内信息模板、微信订阅消息等模板id）',
  `r_to` bigint(12) COMMENT '消息通知接收人id',
  `r_payload` varchar(2000) COMMENT '通知信息关联数据载体,JOSN格式的数据',
  `r_status` varchar(1) DEFAULT '0' COMMENT '消息通知阅读状态：（0:未阅读、1:已阅读）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '消息通知创建人id',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '消息通知创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '消息通知更新人id',
  `modify_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息通知更新时间',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';
