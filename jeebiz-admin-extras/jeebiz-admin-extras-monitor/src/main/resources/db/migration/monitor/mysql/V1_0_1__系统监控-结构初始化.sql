
-- ----------------------------
-- Table structure for $${table-prefix}usage_cpu
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}usage_cpu`;
CREATE TABLE `$${table-prefix}usage_cpu` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inst_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `cpu_num` bigint(3) DEFAULT 1 COMMENT 'CPU核心数',
  `total` double(16) NOT NULL COMMENT 'CPU总的使用率',
  `sys` double(16) NOT NULL COMMENT 'CPU系统使用率',
  `user` double(16) NOT NULL COMMENT 'CPU用户使用率',
  `wait` double(16) NOT NULL COMMENT 'CPU当前等待率',
  `free` double(16) NOT NULL COMMENT 'CPU当前空闲率',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器CPU使用记录表';

-- ----------------------------
-- Table structure for $${table-prefix}usage_mem
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}usage_mem`;
CREATE TABLE `$${table-prefix}usage_mem` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inst_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `total` double(16) NOT NULL COMMENT '内存总量',
  `used` double(16) NOT NULL COMMENT '已用内存',
  `free` double(16) NOT NULL COMMENT '剩余内存',
  `usage` double(16) NOT NULL COMMENT '内存使用率',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器内存使用记录表';

-- ----------------------------
-- Table structure for $${table-prefix}usage_jvm
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}usage_jvm`;
CREATE TABLE `$${table-prefix}usage_jvm` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inst_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `total` double(16) NOT NULL COMMENT '当前JVM占用的内存总数(M)',
  `max` double(16) NOT NULL COMMENT 'JVM最大可用内存总数(M)',
  `free` double(16) NOT NULL COMMENT 'JVM空闲内存(M)',
  `usage` double(16) NOT NULL COMMENT 'JVM使用率',
  `name` varchar(500) NOT NULL COMMENT 'JDK名称',
  `version` varchar(50) NOT NULL COMMENT 'JDK版本',
  `home` varchar(50) NOT NULL COMMENT 'JDK路径',
  `start_time` varchar(50) NOT NULL COMMENT 'JDK启动时间',
  `run_time` varchar(50) NOT NULL COMMENT 'JDK运行时间',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器内存使用记录表';
 