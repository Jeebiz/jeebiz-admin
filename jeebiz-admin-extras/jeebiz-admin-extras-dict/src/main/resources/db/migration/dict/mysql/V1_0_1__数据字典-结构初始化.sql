


-- ----------------------------
-- Table structure for SYS_DATA_PAIRGROUP
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_PAIRGROUP`;
CREATE TABLE `SYS_DATA_PAIRGROUP` (
  `G_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '数据分组id',
  `G_KEY` varchar(50) NOT NULL COMMENT '数据分组键',
  `G_TEXT` varchar(500) NOT NULL COMMENT '数据分组值',
  `G_STATUS` int(1) DEFAULT 1 COMMENT '数据分组状态:（0:禁用|1：可用）',
  `G_ORDER` int(2) DEFAULT 1 COMMENT '数据分组排序',
  PRIMARY KEY (`G_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础数据分组信息表';

-- ----------------------------
-- Table structure for SYS_DATA_PAIRVALUE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_PAIRVALUE`;
CREATE TABLE `SYS_DATA_PAIRVALUE` (
  `d_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
  `d_group` varchar(50) NOT NULL COMMENT '数据分组',
  `d_label` varchar(200) NOT NULL COMMENT '数据标签',
  `d_key` varchar(50) NOT NULL COMMENT '数据键',
  `D_VALUE` varchar(500) NOT NULL COMMENT '数据值',
  `D_TEXT` varchar(2000) COMMENT '数据描述',
  `d_status` int(1) DEFAULT 1 COMMENT '数据状态:（0:不可用|1：可用）',
  `d_order` int(10) DEFAULT 1 COMMENT '数据排序:组内排序',
  PRIMARY KEY (`d_id`),
  UNIQUE KEY (`d_group`,`d_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础数据信息表';
