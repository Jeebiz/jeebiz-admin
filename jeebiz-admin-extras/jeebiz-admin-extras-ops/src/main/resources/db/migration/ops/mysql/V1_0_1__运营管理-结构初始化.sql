
-- ----------------------------
-- Table structure for $${table-prefix}ops_stats
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}ops_stats`;
CREATE TABLE `sys_data_ops_stats` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `day` timestamp NOT NULL COMMENT '统计日期',
  `dr` bigint(12) NOT NULL COMMENT '新增注册用户数',
  `dau` bigint(12) NOT NULL COMMENT '当日活跃用户数',
  `pay_number` bigint(12) NOT NULL COMMENT '付费用户数',
  `pay_amount` float(11,0) NOT NULL COMMENT '付费金额（元）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp DEFAULT NULL COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人id',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_day` (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据统计记录表';
