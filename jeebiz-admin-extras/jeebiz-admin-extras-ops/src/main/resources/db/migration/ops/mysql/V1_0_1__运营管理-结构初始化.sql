
-- ----------------------------
-- Table structure for $${table-prefix}stats_new_payment
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}stats_new_payment`;
CREATE TABLE `$${table-prefix}stats_new_payment` (
   `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `app_id` varchar(11) DEFAULT NULL COMMENT '客户端应用ID',
   `app_channel` varchar(255) DEFAULT NULL COMMENT '客户端应用渠道编码',
   `app_version` varchar(20) DEFAULT NULL COMMENT '客户端版本',
   `country_code` varchar(20) DEFAULT NULL COMMENT '国家编码：http://doc.chacuo.net/iso-3166-1',
   `day` date NOT NULL COMMENT '日期，yyyy-MM-dd',
   `day_register` bigint(12) DEFAULT NULL COMMENT '新增注册人数',
   `day_amount` decimal(12,2) DEFAULT NULL COMMENT '当日充值金额（美元）',
   `day3_amount` decimal(12,2) DEFAULT NULL COMMENT '3日累计充值金额',
   `day7_amount` decimal(12,2) DEFAULT NULL COMMENT '7日累计充值金额',
   `day14_amount` decimal(12,2) DEFAULT NULL COMMENT '14日累计充值金额',
   `day30_amount` decimal(12,2) DEFAULT NULL COMMENT '30日累计充值金额',
   `day60_amount` decimal(12,2) DEFAULT NULL COMMENT '60日累计充值金额',
   `day90_amount` decimal(12,2) DEFAULT NULL COMMENT '90日累计充值金额',
   `total_amount` decimal(12,2) DEFAULT NULL COMMENT '总充值金额',
   `is_delete` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
   `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
   `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `indx_new_payment` (`app_id`,`app_channel`,`app_version`,`country_code`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新增用户付费统计表';

-- ----------------------------
-- Table structure for $${table-prefix}stats_remain
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}stats_remain`;
CREATE TABLE `$${table-prefix}stats_remain` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(11) DEFAULT NULL COMMENT '客户端应用ID',
  `app_channel` varchar(255) DEFAULT NULL COMMENT '客户端应用渠道编码',
  `app_version` varchar(20) DEFAULT NULL COMMENT '客户端版本',
  `day` date NOT NULL COMMENT '日期，yyyy-MM-dd',
  `dru` bigint(12) DEFAULT NULL COMMENT '新增注册人数',
  `second_day` decimal(4,2) DEFAULT NULL COMMENT '2日留存率',
  `third_day` decimal(4,2) DEFAULT NULL COMMENT '3日留存率',
  `fourth_day` decimal(4,2) DEFAULT NULL COMMENT '4日留存率',
  `fifth_day` decimal(4,2) DEFAULT NULL COMMENT '5日留存率',
  `sixth_day` decimal(4,2) DEFAULT NULL COMMENT '6日留存率',
  `seventh_day` decimal(4,2) DEFAULT NULL COMMENT '7日留存率',
  `eighth_day` decimal(4,2) DEFAULT NULL COMMENT '8日留存率',
  `ninth_day` decimal(4,2) DEFAULT NULL COMMENT '9日留存率',
  `tenth_day` decimal(4,2) DEFAULT NULL COMMENT '10日留存率',
  `eleventh_day` decimal(4,2) DEFAULT NULL COMMENT '11日留存率',
  `twelfth_day` decimal(4,2) DEFAULT NULL COMMENT '12日留存率',
  `thirteenth_day` decimal(4,2) DEFAULT NULL COMMENT '13日留存率',
  `fourteenth_day` decimal(4,2) DEFAULT NULL COMMENT '14日留存率',
  `fifteenth_day` decimal(4,2) DEFAULT NULL COMMENT '15日留存率',
  `sixteenth_day` decimal(4,2) DEFAULT NULL COMMENT '16日留存率',
  `seventeenth_day` decimal(4,2) DEFAULT NULL COMMENT '17日留存率',
  `eighteenth_day` decimal(4,2) DEFAULT NULL COMMENT '18日留存率',
  `nineteenth_day` decimal(4,2) DEFAULT NULL COMMENT '19日留存率',
  `twentieth_day` decimal(4,2) DEFAULT NULL COMMENT '20日留存率',
  `twenty_first_day` decimal(4,2) DEFAULT NULL COMMENT '21日留存率',
  `twenty_second_day` decimal(4,2) DEFAULT NULL COMMENT '22日留存率',
  `twenty_third_day` decimal(4,2) DEFAULT NULL COMMENT '23日留存率',
  `twenty_fourth_day` decimal(4,2) DEFAULT NULL COMMENT '24日留存率',
  `twenty_fifth_day` decimal(4,2) DEFAULT NULL COMMENT '25日留存率',
  `twenty_sixth_day` decimal(4,2) DEFAULT NULL COMMENT '26日留存率',
  `twenty_seventh_day` decimal(4,2) DEFAULT NULL COMMENT '27日留存率',
  `twenty_eighth_day` decimal(4,2) DEFAULT NULL COMMENT '28日留存率',
  `twenty_ninth_day` decimal(4,2) DEFAULT NULL COMMENT '29日留存率',
  `thirtieth_day` decimal(4,2) DEFAULT NULL COMMENT '30日留存率',
  `is_delete` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `indx_remain` (`app_id`,`app_channel`,`app_version`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='注册留存率统计表';
