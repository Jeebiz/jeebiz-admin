
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_EXTRAS_INFORMS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_EXTRAS_INFORMS`;
CREATE TABLE `SYS_EXTRAS_INFORMS` (
  `INFO_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息通知ID编号',
  `INFO_USERID` int(11) DEFAULT NULL COMMENT '消息通知通知对象ID',
  `INFO_TYPE` varchar(10) DEFAULT NULL COMMENT '消息通知类型：（notice：通知、letter：私信）',
  `INFO_TITLE` varchar(200) DEFAULT NULL COMMENT '消息通知标题',
  `INFO_DETAIL` tinytext COMMENT '消息通知内容',
  `INFO_STATUS` varchar(1) DEFAULT NULL COMMENT '消息通知阅读状态：（0:未阅读、1:已阅读）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息通知送达时间',
  PRIMARY KEY (`INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知信息表';

