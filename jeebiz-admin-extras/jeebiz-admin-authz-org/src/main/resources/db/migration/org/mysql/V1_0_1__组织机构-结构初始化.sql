
/* 组织机构核心表：机构信息表、部门信息表、岗位信息表、用户组织机构关联表*/



-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_LIST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_LIST`;
CREATE TABLE `SYS_AUTHZ_ORG_LIST` (
  `ORG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构ID编号',
  `ORG_CODE` varchar(30) DEFAULT NULL COMMENT '机构编码',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `ORG_INTRO` varchar(2000) COMMENT '机构简介',
  `ORG_PARENT` int(11) DEFAULT 0 COMMENT '父级机构ID编号',
  `ORG_UID` int(11) NOT NULL COMMENT '机构创建人ID',
  `ORG_STATUS` varchar(1) DEFAULT '0' COMMENT '机构状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '机构创建时间',
  PRIMARY KEY (`ORG_ID`),
  UNIQUE KEY `UNIQUE_ORG_CODE` (`ORG_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_DEPT
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_DEPT`;
CREATE TABLE `SYS_AUTHZ_ORG_DEPT` (
  `ORG_ID` int(11) NOT NULL COMMENT '机构ID编号',
  `DEPT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID编号',
  `DEPT_CODE` varchar(30) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `DEPT_INTRO` varchar(500) COMMENT '部门简介',
  `DEPT_PARENT` int(11) DEFAULT NULL COMMENT '父级部门ID编号',
  `DEPT_UID` int(11) NOT NULL COMMENT '部门创建人ID',
  `DEPT_STATUS` varchar(1) DEFAULT '0' COMMENT '部门状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '部门创建时间',
  PRIMARY KEY (`DEPT_ID`),
  UNIQUE KEY `UNIQUE_DEPT_CODE` (`DEPT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_POST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_POST`;
CREATE TABLE `SYS_AUTHZ_ORG_POST` (
  `DEPT_ID` int(11) NOT NULL COMMENT '部门ID编号',
  `POST_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID编号',
  `POST_CODE` varchar(30) DEFAULT NULL COMMENT '岗位编码',
  `POST_NAME` varchar(100) DEFAULT NULL COMMENT '岗位名称',
  `POST_INTRO` varchar(500) COMMENT '岗位简介',
  `POST_UID` int(11) NOT NULL COMMENT '岗位创建人ID',
  `POST_STATUS` varchar(1) DEFAULT '0' COMMENT '岗位状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '岗位创建时间',
  PRIMARY KEY (`POST_ID`),
  UNIQUE KEY `UNIQUE_POST_CODE` (`POST_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_STAFF
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_STAFF`;
CREATE TABLE `SYS_AUTHZ_ORG_STAFF` (
  `ORG_ID` int(11) NOT NULL COMMENT '机构ID编号',
  `DEPT_ID` int(11) NOT NULL COMMENT '部门ID编号',
  `POST_ID` int(11) NOT NULL COMMENT '岗位ID编号',
  `STAFF_ID` int(11) NOT NULL COMMENT  '员工ID编码（用户ID）',
  `STAFF_INTRO` varchar(500) COMMENT '员工简介',
  `STAFF_STATUS` varchar(1) DEFAULT '0' COMMENT '员工状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '员工入职时间',
  UNIQUE KEY `UNIQUE_ORG_STAFF` (`ORG_ID`,`DEPT_ID`,`POST_ID`,`STAFF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织机构关联表';

-- ----------------------------
-- Table structure for SYS_AUTHZ_ORG_TEAM
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_ORG_TEAM`;
CREATE TABLE `SYS_AUTHZ_ORG_TEAM` (
  `ORG_ID` int(11) NOT NULL COMMENT '机构ID编号',
  `DEPT_ID` int(11) NOT NULL COMMENT '部门ID编号',
  `TEAM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队ID编号',
  `TEAM_NAME` varchar(100) DEFAULT NULL COMMENT '团队名称',
  `TEAM_INTRO` varchar(500) COMMENT '团队简介',
  `TEAM_UID` int(11) NOT NULL COMMENT '团队创建人ID',
  `TEAM_STATUS` varchar(1) DEFAULT '0' COMMENT '团队状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '团队创建时间',
  PRIMARY KEY (`TEAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队信息表';

