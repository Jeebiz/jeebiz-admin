


-- ----------------------------
-- Table structure for sys_data_settings
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_settings`;
CREATE TABLE `sys_data_settings` (
  `D_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `d_group` varchar(50) NOT NULL COMMENT '参数分组',
  `D_LABEL` varchar(50) NOT NULL COMMENT '参数标签',
  `D_KEY` varchar(50) NOT NULL COMMENT '参数键',
  `D_TEXT` varchar(500) NOT NULL COMMENT '参数值',
  `D_UNIT` varchar(30) DEFAULT NULL COMMENT '参数单位:如 KB',
  `D_TYPE` varchar(20) DEFAULT 'text' COMMENT '参数展示类型：（ text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）',
  `D_RULES` varchar(255) DEFAULT NULL COMMENT '参数验证规则：如（required|range:[0,100] (多个用|隔开)）',
  `D_PLACEHOLDER` varchar(255) DEFAULT NULL COMMENT '参数提示信息',
  `D_REMARK` varchar(500) DEFAULT NULL COMMENT '参数备注信息',
  `D_SOURCE` varchar(1000) DEFAULT NULL COMMENT '参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]',
  `d_status` int(1) DEFAULT 1 COMMENT '参数状态:（0:不可用|1：可用）',
  `d_order` int(3) DEFAULT 1 COMMENT '参数排序:组内排序',
  PRIMARY KEY (`D_ID`),
  UNIQUE KEY (`d_group`,`D_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数设置表';


