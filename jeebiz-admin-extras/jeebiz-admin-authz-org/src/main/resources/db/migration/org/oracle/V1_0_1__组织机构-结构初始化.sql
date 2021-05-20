
/* 组织机构核心表：机构信息表、部门信息表、岗位信息表、用户组织机构关联表*/

-- Create table
create table sys_authz_org_list (
	org_id 		VARCHAR2(32) default sys_guid() not null,
  	org_code    VARCHAR2(30) not null,
  	org_name  	VARCHAR2(100) not null,
  	org_intro   VARCHAR2(2000),
  	org_parent  VARCHAR2(32) not null,
  	org_uid  	VARCHAR2(32) not null,
  	org_status	VARCHAR2(1) default '1',
  	is_delete	VARCHAR2(2),
  	creator		VARCHAR2(32),
	create_time	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
	modifyer	VARCHAR2(32),
	modify_time	VARCHAR2(32),
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
comment on column sys_authz_org_list.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_org_list.creator  is '创建人ID';
comment on column sys_authz_org_list.create_time  is '创建时间';
comment on column sys_authz_org_list.modifyer  is '修改人ID';
comment on column sys_authz_org_list.modify_time  is '修改时间';

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
  	is_delete		VARCHAR2(2),
  	creator			VARCHAR2(32),
	create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
	modifyer		VARCHAR2(32),
	modify_time		VARCHAR2(32),
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
comment on column sys_authz_org_dept.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_org_dept.creator  is '创建人ID';
comment on column sys_authz_org_dept.create_time  is '创建时间';
comment on column sys_authz_org_dept.modifyer  is '修改人ID';
comment on column sys_authz_org_dept.modify_time  is '修改时间';

-- Create table
create table sys_authz_org_post
(
	dept_id    		VARCHAR2(32) not null,
	post_id   		VARCHAR2(32) default sys_guid() not null,
  	post_code 		VARCHAR2(30) not null,
  	post_name		VARCHAR2(100) not null,
  	post_intro 		VARCHAR2(500),
  	post_uid		VARCHAR2(32) not null,
  	post_status 	VARCHAR2(1) default '0' not null,
  	is_delete		VARCHAR2(2),
  	creator			VARCHAR2(32),
	create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
	modifyer		VARCHAR2(32),
	modify_time		VARCHAR2(32),
  	CONSTRAINT UNIQUE_POST_CODE UNIQUE(post_code),
  	CONSTRAINT PK_post_id PRIMARY KEY(post_id)
);
-- Add comments to the table 
comment on table sys_authz_org_post  is '岗位信息表';
-- Add comments to the columns 
comment on column sys_authz_org_post.dept_id  is '部门id编号';
comment on column sys_authz_org_post.post_id  is '岗位id编号';
comment on column sys_authz_org_post.post_code  is '岗位编码';
comment on column sys_authz_org_post.post_name  is '岗位名称';
comment on column sys_authz_org_post.post_intro is '岗位简介';
comment on column sys_authz_org_post.post_uid is '岗位创建人id';
comment on column sys_authz_org_post.post_status  is '岗位状态（0:禁用|1:可用）';
comment on column sys_authz_org_post.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_org_post.creator  is '创建人ID';
comment on column sys_authz_org_post.create_time  is '创建时间';
comment on column sys_authz_org_post.modifyer  is '修改人ID';
comment on column sys_authz_org_post.modify_time  is '修改时间';

-- Create table
create table sys_authz_org_staff
(
	org_id    		VARCHAR2(32) not null,
	dept_id   		VARCHAR2(32) not null,
  	post_id 		VARCHAR2(32) not null,
  	staff_id		VARCHAR2(32) not null,
  	staff_intro 	VARCHAR2(500),
  	staff_status 	VARCHAR2(1) default '0' not null,
  	is_delete		VARCHAR2(2),
  	creator			VARCHAR2(32),
	create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
	modifyer		VARCHAR2(32),
	modify_time		VARCHAR2(32),
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
comment on column sys_authz_org_staff.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_org_staff.creator  is '创建人ID';
comment on column sys_authz_org_staff.create_time  is '创建时间';
comment on column sys_authz_org_staff.modifyer  is '修改人ID';
comment on column sys_authz_org_staff.modify_time  is '修改时间';

 
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
  	is_delete		VARCHAR2(2),
  	creator			VARCHAR2(32),
	create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
	modifyer		VARCHAR2(32),
	modify_time		VARCHAR2(32),
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
comment on column sys_authz_org_team.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_org_team.creator  is '创建人ID';
comment on column sys_authz_org_team.create_time  is '创建时间';
comment on column sys_authz_org_team.modifyer  is '修改人ID';
comment on column sys_authz_org_team.modify_time  is '修改时间';