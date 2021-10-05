


-- ----------------------------
-- Table structure for sys_data_pairvalue
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_pairvalue`;
CREATE TABLE `sys_data_pairvalue` (
  `d_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据id',
  `d_group` varchar(50) NOT NULL COMMENT '数据字典',
  `d_label` varchar(50) NOT NULL COMMENT '数据标签',
  `d_key` varchar(50) NOT NULL COMMENT '数据键',
  `d_text` varchar(300) NOT NULL COMMENT '数据值',
  `d_status` int(1) DEFAULT 1 COMMENT '数据状态:（0:不可用|1：可用）',
  `d_order` int(2) DEFAULT 1 COMMENT '数据排序:组内排序',
  PRIMARY KEY (`d_id`),
  UNIQUE KEY (`d_group`,`d_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础数据信息表';

-- ----------------------------
-- Table structure for sys_data_settings
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_settings`;
CREATE TABLE `sys_data_settings` (
  `d_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '参数id',
  `d_group` varchar(50) NOT NULL COMMENT '参数分组',
  `d_label` varchar(50) NOT NULL COMMENT '参数标签',
  `d_key` varchar(50) NOT NULL COMMENT '参数键',
  `d_text` varchar(300) NOT NULL COMMENT '参数值',
  `d_unit` varchar(30) DEFAULT NULL COMMENT '参数单位:如 KB',
  `d_type` varchar(20) DEFAULT 'text' COMMENT '参数展示类型：（ text,textarea,password,checkbox,radio,file,image,color,date,timestamp,email,month,number,range,select,switch,tel,time,week,url）',
  `d_rules` varchar(255) DEFAULT NULL COMMENT '参数验证规则：如（required|range:[0,100] (多个用|隔开)）',
  `d_placeholder` varchar(255) DEFAULT NULL COMMENT '参数提示信息',
  `d_remark` varchar(500) DEFAULT NULL COMMENT '参数备注信息',
  `d_source` varchar(1000) DEFAULT NULL COMMENT '参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]',
  `d_status` int(1) DEFAULT 1 COMMENT '参数状态:（0:不可用|1：可用）',
  `d_order` int(3) DEFAULT 1 COMMENT '参数排序:组内排序',
  PRIMARY KEY (`d_id`),
  UNIQUE KEY (`d_group`,`d_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数设置表';


