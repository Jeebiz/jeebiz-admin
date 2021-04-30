
/* 菜单核心表：功能菜单信息表、功能操作信息表、功能菜单-功能操作关系表*/



-- ----------------------------
-- Table structure for sys_authz_feature_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_feature_list`;
CREATE TABLE `sys_authz_feature_list` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能菜单ID',
  `f_name` varchar(50) NOT NULL COMMENT '功能菜单名称',
  `f_abb` varchar(50) DEFAULT NULL COMMENT '功能菜单简称',
  `f_code` varchar(50) NOT NULL COMMENT '功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据',
  `f_url` varchar(500) NOT NULL DEFAULT '#' COMMENT '功能菜单URL',
  `f_path` varchar(500) DEFAULT '' COMMENT '功能菜单对应页面相对路径',
  `f_type` int(1) NOT NULL DEFAULT '1' COMMENT '菜单类型(1:原生|2:自定义)',
  `f_icon` varchar(255) DEFAULT NULL COMMENT '菜单样式或菜单图标路径',
  `f_order` int(3) NOT NULL DEFAULT '1' COMMENT '菜单显示顺序',
  `f_parent` int(11) NOT NULL COMMENT '父级功能菜单ID',
  `f_visible` int(1) NOT NULL DEFAULT '1' COMMENT '菜单是否可见(1:可见|0:不可见)',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能菜单信息表';

-- ----------------------------
-- Table structure for sys_authz_feature_opts
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_feature_opts`;
CREATE TABLE `sys_authz_feature_opts` (
  `f_id` int(11) NOT NULL COMMENT '功能菜单ID',
  `opt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能操作信息表ID',
  `opt_name` varchar(50) NOT NULL COMMENT '功能操作名称',
  `opt_icon` varchar(100) DEFAULT NULL COMMENT '功能操作图标样式',
  `opt_order` int(2) NOT NULL COMMENT '显示顺序',
  `OPT_VISIBLE` int(1) NOT NULL COMMENT '是否可见(1:可见|0:不可见)',
  `opt_perms` varchar(50) NOT NULL COMMENT '权限标记',
  PRIMARY KEY (`opt_id`),
  UNIQUE KEY (`f_id`,`opt_perms`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能操作信息表';
