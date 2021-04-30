


-- ----------------------------
-- Table structure for SYS_DATA_FILES
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_FILES`;
CREATE TABLE `SYS_DATA_FILES` (
  `f_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `F_UUid` varchar(100) NOT NULL COMMENT '文件UUid',
  `F_EXT` varchar(50) NOT NULL COMMENT '文件类型',
  `f_name` varchar(200) NOT NULL COMMENT '文件名',
  `F_TO` varchar(10) NOT NULL COMMENT '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储',
  `F_GROUP` varchar(50) NOT NULL COMMENT '文件存储分组',
  `f_path` varchar(500) NOT NULL COMMENT '文件存储路径',
  `F_THUMB` varchar(500) COMMENT '缩略图访问地址（图片类型文件）',
  `F_Uid` varchar(11) NOT NULL COMMENT '文件所属用户id',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件存储信息表';
