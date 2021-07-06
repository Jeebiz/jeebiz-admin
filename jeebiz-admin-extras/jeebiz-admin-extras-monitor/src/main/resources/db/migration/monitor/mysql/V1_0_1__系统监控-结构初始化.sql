
-- ----------------------------
-- Table structure for $${table-prefix}usage_cpu
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}usage_cpu`;
CREATE TABLE `$${table-prefix}usage_cpu` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `instance_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `cpu_num` bigint(3) DEFAULT 1 COMMENT 'CPU核心数',
  `cpu_total` double(10,2) NOT NULL COMMENT 'CPU总的使用率',
  `cpu_sys` double(10,2) NOT NULL COMMENT 'CPU系统使用率',
  `cpu_user` double(10,2) NOT NULL COMMENT 'CPU用户使用率',
  `cpu_wait` double(10,2) NOT NULL COMMENT 'CPU当前等待率',
  `cpu_free` double(10,2) NOT NULL COMMENT 'CPU当前空闲率',
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
  `instance_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `mem_total` double(10,2) NOT NULL COMMENT '内存总量',
  `mem_used` double(10,2) NOT NULL COMMENT '已用内存',
  `mem_free` double(10,2) NOT NULL COMMENT '剩余内存',
  `mem_usage` double(10,2) NOT NULL COMMENT '内存使用率',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器内存使用记录表';


-- ----------------------------
-- Table structure for $${table-prefix}usage_disk
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}usage_disk`;
CREATE TABLE `$${table-prefix}usage_disk` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `instance_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `disk_mount` varchar(500) NOT NULL COMMENT '盘符路径',
  `disk_name` varchar(500) NOT NULL COMMENT '盘符类型',
  `disk_type` varchar(500) NOT NULL COMMENT '文件类型',
  `disk_total` double(10,2) NOT NULL COMMENT '磁盘总大小',
  `disk_used` double(10,2) NOT NULL COMMENT '已用磁盘大小',
  `disk_free` double(10,2) NOT NULL COMMENT '剩余磁盘大小',
  `disk_usage` double(10,2) NOT NULL COMMENT '磁盘使用率',
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
  `instance_id` varchar(100) NOT NULL COMMENT '服务器实例ID',
  `jvm_total` double(10,2) NOT NULL COMMENT '当前JVM占用的内存总数(M)',
  `jvm_max` double(10,2) NOT NULL COMMENT 'JVM最大可用内存总数(M)',
  `jvm_free` double(10,2) NOT NULL COMMENT 'JVM空闲内存(M)',
  `jvm_used` double(10,2) NOT NULL COMMENT 'JVM已用内存(M)',
  `jvm_usage` double(10,2) NOT NULL COMMENT 'JVM使用率',
  `jdk_name` varchar(500) NOT NULL COMMENT 'JDK名称',
  `jdk_version` varchar(50) NOT NULL COMMENT 'JDK版本',
  `jdk_home` varchar(500) NOT NULL COMMENT 'JDK路径',
  `jvm_start_time` varchar(50) NOT NULL COMMENT 'JDK启动时间',
  `jvm_run_time` varchar(50) NOT NULL COMMENT 'JDK运行时间',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器内存使用记录表';
 