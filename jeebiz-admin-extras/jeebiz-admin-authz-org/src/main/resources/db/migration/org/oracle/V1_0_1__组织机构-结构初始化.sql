
/* 组织机构核心表：机构信息表、部门信息表、岗位信息表、用户组织机构关联表*/

-- Create table
create table SYS_AUTHZ_ORG_LIST (
	ORG_ID 		VARCHAR2(32) default sys_guid() not null,
  	ORG_CODE    VARCHAR2(30) not null,
  	ORG_NAME  	VARCHAR2(100) not null,
  	ORG_INTRO   VARCHAR2(2000),
  	ORG_PARENT  VARCHAR2(32) not null,
  	ORG_UID  VARCHAR2(32) not null,
  	ORG_STATUS	VARCHAR2(1) default '1',
  	TIME24 		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_CODE UNIQUE(ORG_CODE),
  	CONSTRAINT PK_ORG_ID PRIMARY KEY(ORG_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_LIST  is '机构信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_LIST.ORG_ID  is '机构ID编号';
comment on column SYS_AUTHZ_ORG_LIST.ORG_CODE  is '机构编码';
comment on column SYS_AUTHZ_ORG_LIST.ORG_NAME  is '机构名称';
comment on column SYS_AUTHZ_ORG_LIST.ORG_INTRO  is '机构简介'; 
comment on column SYS_AUTHZ_ORG_LIST.ORG_PARENT  is '父级机构ID编号';
comment on column SYS_AUTHZ_ORG_LIST.ORG_UID  is '机构创建人ID';
comment on column SYS_AUTHZ_ORG_LIST.ORG_STATUS  is '机构状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_LIST.TIME24  is '机构创建时间';

-- Create table
create table SYS_AUTHZ_ORG_DEPT
(
	ORG_ID    		VARCHAR2(32) not null,
	DEPT_ID   		VARCHAR2(32) default sys_guid() not null,
  	DEPT_CODE 		VARCHAR2(30) not null,
  	DEPT_NAME 		VARCHAR2(100) not null,
  	DEPT_INTRO		VARCHAR2(500),
  	DEPT_PARENT		VARCHAR2(32),
  	DEPT_UID		VARCHAR2(32) not null,
  	DEPT_STATUS 	VARCHAR2(1) default '0' not null,
  	TIME24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_DEPT_CODE UNIQUE(ORG_ID, DEPT_CODE),
  	CONSTRAINT PK_DEPT_ID PRIMARY KEY(DEPT_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_DEPT  is '功能操作信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_DEPT.ORG_ID  is '机构ID编号';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_ID  is '部门ID编号';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_CODE  is '部门编码';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_NAME  is '部门名称';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_INTRO  is '部门简介';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_PARENT is '父级部门ID编号';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_UID is '部门创建人ID';
comment on column SYS_AUTHZ_ORG_DEPT.DEPT_STATUS  is '部门状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_DEPT.TIME24 is '部门创建时间';

-- Create table
create table SYS_AUTHZ_ORG_POST
(
	DEPT_ID    		VARCHAR2(32) not null,
	POST_ID   		VARCHAR2(32) default sys_guid() not null,
  	POST_CODE 		VARCHAR2(30) not null,
  	POST_NAME		VARCHAR2(100) not null,
  	POST_INTRO 		VARCHAR2(500),
  	POST_UID		VARCHAR2(32) not null,
  	POST_STATUS 	VARCHAR2(1) default '0' not null,
  	TIME24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_POST_CODE UNIQUE(POST_CODE),
  	CONSTRAINT PK_POST_ID PRIMARY KEY(POST_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_POST  is '岗位信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_POST.DEPT_ID  is '部门ID编号';
comment on column SYS_AUTHZ_ORG_POST.POST_ID  is '岗位ID编号';
comment on column SYS_AUTHZ_ORG_POST.POST_CODE  is '岗位编码';
comment on column SYS_AUTHZ_ORG_POST.POST_NAME  is '岗位名称';
comment on column SYS_AUTHZ_ORG_POST.POST_INTRO is '岗位简介';
comment on column SYS_AUTHZ_ORG_POST.POST_UID is '岗位创建人ID';
comment on column SYS_AUTHZ_ORG_POST.POST_STATUS  is '岗位状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_POST.TIME24 is '岗位创建时间';

-- Create table
create table SYS_AUTHZ_ORG_STAFF
(
	ORG_ID    		VARCHAR2(32) not null,
	DEPT_ID   		VARCHAR2(32) not null,
  	POST_ID 		VARCHAR2(32) not null,
  	STAFF_ID		VARCHAR2(32) not null,
  	STAFF_INTRO 	VARCHAR2(500),
  	STAFF_STATUS 	VARCHAR2(1) default '0' not null,
  	TIME24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_STAFF UNIQUE(ORG_ID, DEPT_ID, POST_ID, STAFF_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_STAFF  is '用户组织机构关联表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_STAFF.ORG_ID  is '机构ID编号';
comment on column SYS_AUTHZ_ORG_STAFF.DEPT_ID  is '部门ID编号';
comment on column SYS_AUTHZ_ORG_STAFF.POST_ID  is '岗位ID编号';
comment on column SYS_AUTHZ_ORG_STAFF.STAFF_ID  is '员工ID编码（用户ID）';
comment on column SYS_AUTHZ_ORG_STAFF.STAFF_INTRO  is '员工简介';
comment on column SYS_AUTHZ_ORG_STAFF.STAFF_STATUS  is '员工状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_STAFF.TIME24  is '员工入职时间';
 
-- Create table
create table SYS_AUTHZ_ORG_TEAM
(
	ORG_ID    		VARCHAR2(32) not null,
	DEPT_ID   		VARCHAR2(32) not null,
	TEAM_ID   		VARCHAR2(32) default sys_guid() not null,
  	TEAM_NAME 		VARCHAR2(100) not null,
  	TEAM_INTRO		VARCHAR2(500),
  	TEAM_UID		VARCHAR2(32) not null,
  	TEAM_STATUS 	VARCHAR2(1) default '0' not null,
  	TIME24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_TEAM_CODE UNIQUE(ORG_ID, TEAM_CODE),
  	CONSTRAINT PK_TEAM_ID PRIMARY KEY(TEAM_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_TEAM  is '功能操作信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_TEAM.ORG_ID  is '机构ID编号';
comment on column SYS_AUTHZ_ORG_TEAM.DEPT_ID  is '部门ID编号';
comment on column SYS_AUTHZ_ORG_TEAM.TEAM_ID  is '团队ID编号';
comment on column SYS_AUTHZ_ORG_TEAM.TEAM_NAME  is '团队名称';
comment on column SYS_AUTHZ_ORG_TEAM.TEAM_INTRO  is '团队简介';
comment on column SYS_AUTHZ_ORG_TEAM.TEAM_UID is '团队创建人ID';
comment on column SYS_AUTHZ_ORG_TEAM.TEAM_STATUS  is '团队状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_TEAM.TIME24 is '团队创建时间';
 