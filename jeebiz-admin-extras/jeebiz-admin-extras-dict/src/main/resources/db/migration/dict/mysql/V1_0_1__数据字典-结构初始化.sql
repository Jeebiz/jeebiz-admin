


-- ----------------------------
-- Table structure for SYS_DATA_PAIRGROUP
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_PAIRGROUP`;
CREATE TABLE `SYS_DATA_PAIRGROUP` (
  `G_ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '数据分组ID',
  `G_KEY` varchar(50) NOT NULL COMMENT '数据分组键',
  `G_TEXT` varchar(500) NOT NULL COMMENT '数据分组值',
  `G_STATUS` int(1) DEFAULT 1 COMMENT '数据分组状态:（0:禁用|1：可用）',
  `G_ORDER` int(2) DEFAULT 1 COMMENT '数据分组排序',
  PRIMARY KEY (`G_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础数据分组信息表';

-- ----------------------------
-- Table structure for SYS_DATA_PAIRVALUE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_PAIRVALUE`;
CREATE TABLE `SYS_DATA_PAIRVALUE` (
  `D_ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  `D_GROUP` varchar(50) NOT NULL COMMENT '数据分组',
  `D_LABEL` varchar(200) NOT NULL COMMENT '数据标签',
  `D_KEY` varchar(50) NOT NULL COMMENT '数据键',
  `D_VALUE` varchar(500) NOT NULL COMMENT '数据值',
  `D_TEXT` varchar(2000) COMMENT '数据描述',
  `D_STATUS` int(1) DEFAULT 1 COMMENT '数据状态:（0:不可用|1：可用）',
  `D_ORDER` int(10) DEFAULT 1 COMMENT '数据排序:组内排序',
  PRIMARY KEY (`D_ID`),
  UNIQUE KEY (`D_GROUP`,`D_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础数据信息表';
