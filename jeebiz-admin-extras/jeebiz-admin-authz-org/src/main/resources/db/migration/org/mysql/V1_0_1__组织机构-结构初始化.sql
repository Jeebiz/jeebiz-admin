
/* 组织机构核心表：组织机构信息表（上下级关联）、组织机构部门表、组织机构岗位表、组织机构用户关系表、组织机构用户关系表*/

-- ----------------------------
-- Table structure for t_org_list
-- ----------------------------
DROP TABLE IF EXISTS `t_org_list`;
CREATE TABLE `t_org_list` (
  `org_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '机构id编号',
  `org_code` varchar(30) DEFAULT NULL COMMENT '机构编码',
  `org_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `org_intro` varchar(2000) COMMENT '机构简介',
  `org_parent` bigint(12) DEFAULT 0 COMMENT '父级机构id编号',
  `org_status` varchar(1) DEFAULT '0' COMMENT '机构状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `UNIQUE_org_code` (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for t_org_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_org_dept`;
CREATE TABLE `t_org_dept` (
  `org_id` bigint(12) NOT NULL COMMENT '机构id编号',
  `dept_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '部门id编号',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `dept_intro` varchar(500) COMMENT '部门简介',
  `dept_parent` bigint(12) DEFAULT NULL COMMENT '父级部门id编号',
  `dept_status` varchar(1) DEFAULT '0' COMMENT '部门状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `uk_dept_code` (`dept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for t_org_post
-- ----------------------------
DROP TABLE IF EXISTS `t_org_post`;
CREATE TABLE `t_org_post` (
  `org_id` bigint(12) NOT NULL COMMENT '机构id编号',
  `dept_id` bigint(12) NOT NULL COMMENT '部门id编号',
  `post_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '岗位id编号',
  `post_code` varchar(30) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(100) DEFAULT NULL COMMENT '岗位名称',
  `post_intro` varchar(500) COMMENT '岗位简介',
  `post_status` varchar(1) DEFAULT '0' COMMENT '岗位状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `uk_post_code` (`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

-- ----------------------------
-- Table structure for t_org_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_org_staff`;
CREATE TABLE `t_org_staff` (
  `org_id` bigint(12) NOT NULL COMMENT '机构id编号',
  `dept_id` bigint(12) NOT NULL COMMENT '部门id编号',
  `post_id` bigint(12) NOT NULL COMMENT '岗位id编号',
  `team_id` bigint(12) COMMENT '团队id编号',
  `staff_id` bigint(12) NOT NULL COMMENT  '员工id编码（用户id）',
  `staff_intro` varchar(500) COMMENT '员工简介',
  `staff_status` varchar(1) DEFAULT '0' COMMENT '员工状态（0:禁用|1:可用）',
  `staff_hiredate` timestamp DEFAULT NULL COMMENT '员工入职时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE KEY `uk_org_staff` (`org_id`,`dept_id`,`post_id`,`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织机构关联表';

-- ----------------------------
-- Table structure for t_org_team
-- ----------------------------
DROP TABLE IF EXISTS `t_org_team`;
CREATE TABLE `t_org_team` (
  `org_id` bigint(12) NOT NULL COMMENT '机构id编号',
  `dept_id` bigint(12) NOT NULL COMMENT '部门id编号',
  `team_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '团队id编号',
  `team_name` varchar(100) DEFAULT NULL COMMENT '团队名称',
  `team_intro` varchar(500) COMMENT '团队简介',
  `team_status` varchar(1) DEFAULT '0' COMMENT '团队状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队信息表';


-- ----------------------------
-- Table structure for sys_tree_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree_config`;
CREATE TABLE `sys_tree_config`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tree_id` bigint(20) DEFAULT NULL COMMENT '树ID（区分不同的数据）',
    `tree_parent_id` bigint(20) DEFAULT NULL COMMENT '上级节点配置ID',
    `tree_type` int(2) DEFAULT NULL COMMENT '树节点类型（1：虚拟节点，2：数据节点）',
    `tree_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '树节点名称（虚拟节点主用）',
    `tree_data_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '树节点数据类型（对应字典t_tree_type中的value)',
    `tree_data_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '树节点数据来源',
    `tree_expanded` int(2) DEFAULT 0 COMMENT '树节点展开状态（1：展开，0：关闭）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tree_config
-- ----------------------------
INSERT INTO `sys_tree_config` VALUES (1, 1, 0, 1, '根节点', '01', NULL);
INSERT INTO `sys_tree_config` VALUES (2, 1, 1, 2, 'xd', '02', 'tree_xd');
INSERT INTO `sys_tree_config` VALUES (3, 1, 2, 2, 'xx', '03', 'tree_xx');
INSERT INTO `sys_tree_config` VALUES (4, 1, 3, 2, 'xq', '04', 'tree_xq');