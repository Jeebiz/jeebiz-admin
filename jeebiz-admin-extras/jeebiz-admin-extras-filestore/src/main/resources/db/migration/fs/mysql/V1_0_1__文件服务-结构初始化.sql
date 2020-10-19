


-- ----------------------------
-- Table structure for SYS_DATA_FILES
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DATA_FILES`;
CREATE TABLE `SYS_DATA_FILES` (
  `F_ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `F_UUID` varchar(100) NOT NULL COMMENT '文件UUID',
  `F_EXT` varchar(50) NOT NULL COMMENT '文件类型',
  `F_NAME` varchar(200) NOT NULL COMMENT '文件名',
  `F_TO` varchar(10) NOT NULL COMMENT '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储',
  `F_GROUP` varchar(50) NOT NULL COMMENT '文件存储分组',
  `F_PATH` varchar(500) NOT NULL COMMENT '文件存储路径',
  `F_THUMB` varchar(500) COMMENT '缩略图访问地址（图片类型文件）',
  `F_UID` varchar(11) NOT NULL COMMENT '文件所属用户ID',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件存储信息表';
