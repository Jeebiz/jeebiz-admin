
/* 组织机构核心表：机构信息表、部门信息表、岗位信息表、用户组织机构关联表*/

-- Create table
create table sys_authz_org_list (
	org_id 		VARCHAR2(32) default sys_guid() not null,
  	org_code    VARCHAR2(30) not null,
  	org_name  	VARCHAR2(100) not null,
  	org_intro   VARCHAR2(2000),
  	org_parent  VARCHAR2(32) not null,
  	org_uid  VARCHAR2(32) not null,
  	org_status	VARCHAR2(1) default '1',
  	time24 		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_org_code UNIQUE(org_code),
  	CONSTRAINT PK_org_id PRIMARY KEY(org_id)
);
-- Add comments to the table 
comment on table sys_authz_org_list  is '机构信息表';
-- Add comments to the columns 
comment on column sys_authz_org_list.org_id  is '机构id编号';
comment on column sys_authz_org_list.org_code  is '机构编码';
comment on column sys_authz_org_list.org_name  is '机构名称';
comment on column sys_authz_org_list.org_intro  is '机构简介';
comment on column sys_authz_org_list.org_parent  is '父级机构id编号';
comment on column sys_authz_org_list.org_uid  is '机构创建人id';
comment on column sys_authz_org_list.org_status  is '机构状态（0:禁用|1:可用）';
comment on column sys_authz_org_list.time24  is '机构创建时间';

-- Create table
create table sys_authz_org_dept
(
	org_id    		VARCHAR2(32) not null,
	dept_id   		VARCHAR2(32) default sys_guid() not null,
  	dept_code 		VARCHAR2(30) not null,
  	dept_name 		VARCHAR2(100) not null,
  	dept_intro		VARCHAR2(500),
  	dept_parent		VARCHAR2(32),
  	dept_uid		VARCHAR2(32) not null,
  	dept_status 	VARCHAR2(1) default '0' not null,
  	time24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_dept_code UNIQUE(org_id, dept_code),
  	CONSTRAINT PK_dept_id PRIMARY KEY(dept_id)
);
-- Add comments to the table 
comment on table sys_authz_org_dept  is '功能操作信息表';
-- Add comments to the columns 
comment on column sys_authz_org_dept.org_id  is '机构id编号';
comment on column sys_authz_org_dept.dept_id  is '部门id编号';
comment on column sys_authz_org_dept.dept_code  is '部门编码';
comment on column sys_authz_org_dept.dept_name  is '部门名称';
comment on column sys_authz_org_dept.dept_intro  is '部门简介';
comment on column sys_authz_org_dept.dept_parent is '父级部门id编号';
comment on column sys_authz_org_dept.dept_uid is '部门创建人id';
comment on column sys_authz_org_dept.dept_status  is '部门状态（0:禁用|1:可用）';
comment on column sys_authz_org_dept.time24 is '部门创建时间';

-- Create table
create table SYS_AUTHZ_ORG_POST
(
	dept_id    		VARCHAR2(32) not null,
	post_id   		VARCHAR2(32) default sys_guid() not null,
  	POST_CODE 		VARCHAR2(30) not null,
  	post_name		VARCHAR2(100) not null,
  	post_intro 		VARCHAR2(500),
  	post_uid		VARCHAR2(32) not null,
  	post_status 	VARCHAR2(1) default '0' not null,
  	time24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_POST_CODE UNIQUE(POST_CODE),
  	CONSTRAINT PK_post_id PRIMARY KEY(post_id)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ORG_POST  is '岗位信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ORG_POST.dept_id  is '部门id编号';
comment on column SYS_AUTHZ_ORG_POST.post_id  is '岗位id编号';
comment on column SYS_AUTHZ_ORG_POST.POST_CODE  is '岗位编码';
comment on column SYS_AUTHZ_ORG_POST.post_name  is '岗位名称';
comment on column SYS_AUTHZ_ORG_POST.post_intro is '岗位简介';
comment on column SYS_AUTHZ_ORG_POST.post_uid is '岗位创建人id';
comment on column SYS_AUTHZ_ORG_POST.post_status  is '岗位状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ORG_POST.time24 is '岗位创建时间';

-- Create table
create table sys_authz_org_staff
(
	org_id    		VARCHAR2(32) not null,
	dept_id   		VARCHAR2(32) not null,
  	post_id 		VARCHAR2(32) not null,
  	staff_id		VARCHAR2(32) not null,
  	staff_intro 	VARCHAR2(500),
  	staff_status 	VARCHAR2(1) default '0' not null,
  	time24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_STAFF UNIQUE(org_id, dept_id, post_id, staff_id)
);
-- Add comments to the table 
comment on table sys_authz_org_staff  is '用户组织机构关联表';
-- Add comments to the columns 
comment on column sys_authz_org_staff.org_id  is '机构id编号';
comment on column sys_authz_org_staff.dept_id  is '部门id编号';
comment on column sys_authz_org_staff.post_id  is '岗位id编号';
comment on column sys_authz_org_staff.staff_id  is '员工id编码（用户id）';
comment on column sys_authz_org_staff.staff_intro  is '员工简介';
comment on column sys_authz_org_staff.staff_status  is '员工状态（0:禁用|1:可用）';
comment on column sys_authz_org_staff.time24  is '员工入职时间';
 
-- Create table
create table sys_authz_org_team
(
	org_id    		VARCHAR2(32) not null,
	dept_id   		VARCHAR2(32) not null,
	team_id   		VARCHAR2(32) default sys_guid() not null,
  	team_name 		VARCHAR2(100) not null,
  	team_intro		VARCHAR2(500),
  	team_uid		VARCHAR2(32) not null,
  	team_status 	VARCHAR2(1) default '0' not null,
  	time24 			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT UNIQUE_ORG_TEAM_CODE UNIQUE(org_id, TEAM_CODE),
  	CONSTRAINT PK_team_id PRIMARY KEY(team_id)
);
-- Add comments to the table 
comment on table sys_authz_org_team  is '功能操作信息表';
-- Add comments to the columns 
comment on column sys_authz_org_team.org_id  is '机构id编号';
comment on column sys_authz_org_team.dept_id  is '部门id编号';
comment on column sys_authz_org_team.team_id  is '团队id编号';
comment on column sys_authz_org_team.team_name  is '团队名称';
comment on column sys_authz_org_team.team_intro  is '团队简介';
comment on column sys_authz_org_team.team_uid is '团队创建人id';
comment on column sys_authz_org_team.team_status  is '团队状态（0:禁用|1:可用）';
comment on column sys_authz_org_team.time24 is '团队创建时间';
 