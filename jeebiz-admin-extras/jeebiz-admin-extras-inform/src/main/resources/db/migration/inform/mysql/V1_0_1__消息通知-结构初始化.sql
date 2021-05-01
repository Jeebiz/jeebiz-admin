
-- ----------------------------
-- Table structure for $${table-prefix}inform_templates
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}inform_templates`;
CREATE TABLE `$${table-prefix}inform_templates` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息模板id',
  `t_target` varchar(10) DEFAULT 'ALL' COMMENT '消息模板面向对象',
  `t_provider` varchar(50) NOT NULL COMMENT '消息通知的发送提供者',
  `t_title` varchar(200) NOT NULL COMMENT '消息通知标题（可能包含变量）',
  `t_content` varchar(2000) COMMENT '消息通知内容（可能包含变量）',
  `t_tid` varchar(200) COMMENT '消息通知对应第三方平台内的模板id',
  `t_payload` varchar(2000) COMMENT '消息通知变量载体,JOSN格式的数据',
  `t_status` varchar(1) DEFAULT '0' COMMENT '消息通知状态：（0:停用、1:启用）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '更新人id',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知信息表';

-- ----------------------------
-- Table structure for $${table-prefix}inform_targets
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}inform_targets`;
CREATE TABLE `$${table-prefix}inform_targets` (
  `t_id` int(11) NOT NULL COMMENT '消息通知id',
  `t_uid` int(11) NOT NULL COMMENT '消息通知接收人id',
  `t_status` varchar(1) DEFAULT '0' COMMENT '消息通知发送状态：（0:待发送、1:已发送）',
  `time24` timestamp COMMENT '消息通知发送时间',
  UNIQUE KEY (`t_id`,`t_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知对象表';

-- ----------------------------
-- Table structure for $${table-prefix}inform_records
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}inform_records`;
CREATE TABLE `$${table-prefix}inform_records` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息通知记录id',
  `r_uid` int(11) COMMENT '消息通知发送人id',
  `r_provider` varchar(50) NOT NULL COMMENT '消息通知的发送提供者',
  `r_tag` varchar(20) COMMENT '消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）',
  `r_title` varchar(200) NOT NULL COMMENT '消息通知标题（可能包含变量）',
  `r_content` text COMMENT '消息通知内容（可能包含变量）',
  `r_tid` varchar(200) COMMENT '消息通知模板id（系统内信息模板、微信订阅消息等模板id）',
  `r_to` int(11) COMMENT '消息通知接收人id',
  `r_payload` varchar(2000) COMMENT '通知信息关联数据载体,JOSN格式的数据',
  `r_status` varchar(1) DEFAULT '0' COMMENT '消息通知阅读状态：（0:未阅读、1:已阅读）',
  `creator` bigint(12) DEFAULT NULL COMMENT '消息通知创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消息通知创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '消息通知更新人id',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息通知更新时间',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';
