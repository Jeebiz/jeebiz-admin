
-- ----------------------------
-- Table structure for sys_data_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_files`;
CREATE TABLE `sys_data_files` (
  `f_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `f_uuid` varchar(100) NOT NULL COMMENT '文件UUid',
  `f_ext` varchar(50) NOT NULL COMMENT '文件类型',
  `f_name` varchar(200) NOT NULL COMMENT '文件名',
  `f_to` varchar(10) NOT NULL COMMENT '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储',
  `f_group` varchar(50) NOT NULL DEFAULT 'group1' COMMENT '文件存储分组',
  `f_path` varchar(500) NOT NULL COMMENT '文件存储路径',
  `f_thumb` varchar(500) COMMENT '缩略图访问地址（图片类型文件）',
  `f_uid` varchar(11) NOT NULL COMMENT '文件所属用户id',
  `f_order` bigint(12) NOT NULL COMMENT '文件同批次的顺序编号',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件存储信息表';
