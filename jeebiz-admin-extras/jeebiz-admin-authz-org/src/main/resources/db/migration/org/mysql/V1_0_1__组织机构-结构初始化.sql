
/* 组织机构核心表：机构信息表、部门信息表、岗位信息表、用户组织机构关联表*/



-- ----------------------------
-- Table structure for sys_authz_org_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_org_list`;
CREATE TABLE `sys_authz_org_list` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构id编号',
  `org_code` varchar(30) DEFAULT NULL COMMENT '机构编码',
  `org_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `org_intro` varchar(2000) COMMENT '机构简介',
  `org_parent` int(11) DEFAULT 0 COMMENT '父级机构id编号',
  `org_uid` int(11) NOT NULL COMMENT '机构创建人id',
  `org_status` varchar(1) DEFAULT '0' COMMENT '机构状态（0:禁用|1:可用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '机构创建时间',
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `UNIQUE_org_code` (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for sys_authz_org_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_org_dept`;
CREATE TABLE `sys_authz_org_dept` (
  `org_id` int(11) NOT NULL COMMENT '机构id编号',
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id编号',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `dept_intro` varchar(500) COMMENT '部门简介',
  `dept_parent` int(11) DEFAULT NULL COMMENT '父级部门id编号',
  `dept_uid` int(11) NOT NULL COMMENT '部门创建人id',
  `dept_status` varchar(1) DEFAULT '0' COMMENT '部门状态（0:禁用|1:可用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '部门创建时间',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `UNIQUE_dept_code` (`dept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_POST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_POST`;
CREATE TABLE `SYS_AUTHZ_ORG_POST` (
  `dept_id` int(11) NOT NULL COMMENT '部门id编号',
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位id编号',
  `POST_CODE` varchar(30) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(100) DEFAULT NULL COMMENT '岗位名称',
  `post_intro` varchar(500) COMMENT '岗位简介',
  `post_uid` int(11) NOT NULL COMMENT '岗位创建人id',
  `post_status` varchar(1) DEFAULT '0' COMMENT '岗位状态（0:禁用|1:可用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '岗位创建时间',
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `UNIQUE_POST_CODE` (`POST_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

-- ----------------------------
-- Table structure for sys_authz_org_staff
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_org_staff`;
CREATE TABLE `sys_authz_org_staff` (
  `org_id` int(11) NOT NULL COMMENT '机构id编号',
  `dept_id` int(11) NOT NULL COMMENT '部门id编号',
  `post_id` int(11) NOT NULL COMMENT '岗位id编号',
  `staff_id` int(11) NOT NULL COMMENT  '员工id编码（用户id）',
  `staff_intro` varchar(500) COMMENT '员工简介',
  `staff_status` varchar(1) DEFAULT '0' COMMENT '员工状态（0:禁用|1:可用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '员工入职时间',
  UNIQUE KEY `UNIQUE_ORG_STAFF` (`org_id`,`dept_id`,`post_id`,`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织机构关联表';

-- ----------------------------
-- Table structure for sys_authz_org_team
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_org_team`;
CREATE TABLE `sys_authz_org_team` (
  `org_id` int(11) NOT NULL COMMENT '机构id编号',
  `dept_id` int(11) NOT NULL COMMENT '部门id编号',
  `team_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队id编号',
  `team_name` varchar(100) DEFAULT NULL COMMENT '团队名称',
  `team_intro` varchar(500) COMMENT '团队简介',
  `team_uid` int(11) NOT NULL COMMENT '团队创建人id',
  `team_status` varchar(1) DEFAULT '0' COMMENT '团队状态（0:禁用|1:可用）',
  `time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '团队创建时间',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队信息表';

