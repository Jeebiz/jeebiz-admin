
-- ----------------------------
-- Table structure for SYS_DATA_OPS_STATS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_OPS_STATS`;
CREATE TABLE `SYS_DATA_OPS_STATS` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `DAY` datetime NOT NULL COMMENT '统计日期',
  `DR` bigint(11) NOT NULL COMMENT '新增注册用户数',
  `DAU` bigint(11) NOT NULL COMMENT '当日活跃用户数',
  `PAY_NUMBER` bigint(11) NOT NULL COMMENT '付费用户数',
  `PAY_AMOUNT` float(11,0) NOT NULL COMMENT '付费金额（元）',
  `CREATOR` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFYER` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `idx_day` (`DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据统计记录表';
