
-- ----------------------------
-- Table structure for SYS_INFORM_TEMPlatES
-- ----------------------------
DROP TABLE IF EXISTS `SYS_INFORM_TEMPlatES`;
CREATE TABLE `SYS_INFORM_TEMPlatES` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息通知id',
  `t_uid` int(11) NOT NULL COMMENT '消息通知创建人id',
  `T_TARGET` varchar(10) DEFAULT 'ALL' COMMENT '消息通知面向对象',
  `T_PROVidER` varchar(50) NOT NULL COMMENT '消息通知的发送提供者',
  `T_TITLE` varchar(200) NOT NULL COMMENT '消息通知标题（可能包含变量）',
  `T_CONTENT` varchar(2000) COMMENT '消息通知内容（可能包含变量）',
  `t_tid` varchar(200) COMMENT '消息通知对应第三方平台内的模板id',
  `T_PAYLOAD` varchar(2000) COMMENT '消息通知变量载体,JOSN格式的数据',
  `t_status` varchar(1) DEFAULT '0' COMMENT '消息通知状态：（0:停用、1:启用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息通知创建时间',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知信息表';

-- ----------------------------
-- Table structure for SYS_INFORM_TARGETS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_INFORM_TARGETS`;
CREATE TABLE `SYS_INFORM_TARGETS` (
  `t_id` int(11) NOT NULL COMMENT '消息通知id',
  `t_uid` int(11) NOT NULL COMMENT '消息通知接收人id',
  `t_status` varchar(1) DEFAULT '0' COMMENT '消息通知发送状态：（0:待发送、1:已发送）',
  `time24` timestamp COMMENT '消息通知发送时间',
  UNIQUE KEY (`t_id`,`t_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知对象表';

-- ----------------------------
-- Table structure for SYS_INFORM_RECORDS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_INFORM_RECORDS`;
CREATE TABLE `SYS_INFORM_RECORDS` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息通知记录id',
  `R_Uid` int(11) COMMENT '消息通知发送人id',
  `R_PROVidER` varchar(50) NOT NULL COMMENT '消息通知的发送提供者',
  `R_TAG` varchar(20) COMMENT '消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）',
  `R_TITLE` varchar(200) NOT NULL COMMENT '消息通知标题（可能包含变量）',
  `R_CONTENT` text COMMENT '消息通知内容（可能包含变量）',
  `R_Tid` varchar(200) COMMENT '消息通知模板id（系统内信息模板、微信订阅消息等模板id）',
  `R_TO` int(11) COMMENT '消息通知接收人id',
  `R_PAYLOAD` varchar(2000) COMMENT '通知信息关联数据载体,JOSN格式的数据',
  `r_status` varchar(1) DEFAULT '0' COMMENT '消息通知阅读状态：（0:未阅读、1:已阅读）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息通知发送时间',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';
