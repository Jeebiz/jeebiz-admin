/* 
 * 权限核心表：
 * 2、用户信息表、角色信息表、用户-角色关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- Create table
create table SYS_AUTHZ_ROLE_LIST (
  R_ID   		VARCHAR2(32) default sys_guid() not null,
  R_KEY   		VARCHAR2(50) not null,
  R_NAME   		VARCHAR2(50) not null,
  R_TYPE   		VARCHAR2(2) default 1,
  R_INTRO  		VARCHAR2(1000),
  R_STATUS		VARCHAR2(2) default 1,
  R_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT UNIQUE_R_KEY UNIQUE(R_KEY),
  CONSTRAINT PK_RID PRIMARY KEY(R_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ROLE_LIST  is '角色信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ROLE_LIST.R_ID  is '角色ID';
comment on column SYS_AUTHZ_ROLE_LIST.R_KEY  is '角色编码';
comment on column SYS_AUTHZ_ROLE_LIST.R_NAME  is '角色名称';
comment on column SYS_AUTHZ_ROLE_LIST.R_TYPE  is '角色类型（1:原生|2:继承|3:复制|4:自定义）';
comment on column SYS_AUTHZ_ROLE_LIST.R_INTRO  is '角色简介';
comment on column SYS_AUTHZ_ROLE_LIST.R_STATUS  is '角色状态（0:禁用|1:可用）';
comment on column SYS_AUTHZ_ROLE_LIST.R_TIME24  is '初始化时间';

-- Create table
create table SYS_AUTHZ_ROLE_PERMS (
  R_ID   		VARCHAR2(32) not null,
  PERMS 		VARCHAR2(50) not null,
  CONSTRAINT UNIQUE_RID_PERMS UNIQUE(R_ID, PERMS)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_ROLE_PERMS  is '角色-权限关系表（角色-菜单-按钮）';
-- Add comments to the columns 
comment on column SYS_AUTHZ_ROLE_PERMS.R_ID  is '角色ID';
comment on column SYS_AUTHZ_ROLE_PERMS.PERMS  is '权限标记：(等同SYS_AUTHZ_FEATURE_OPTS.OPT_PERMS)';

-- Create table
create table SYS_AUTHZ_USER_LIST (
  U_ID   			VARCHAR2(32) default sys_guid() not null,
  U_USERNAME 		VARCHAR2(100) not null,
  U_PASSWORD 		VARCHAR2(100) not null,
  U_SALT			VARCHAR2(64),
  U_SECRET			VARCHAR2(128),
  U_STATUS			VARCHAR2(1),
  U_UID   			VARCHAR2(32),
  U_CODE   			VARCHAR2(32),
  U_APP_ID 			VARCHAR2(50),
  U_APP_CHANNEL		VARCHAR2(50),
  U_APP_VERSION		VARCHAR2(20),
  U_ONLINE			VARCHAR2(1),
  U_LATEST_ONLINE	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  U_TIME24			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT idx_uname UNIQUE(U_USERNAME),
  CONSTRAINT idx_uuid UNIQUE(U_UID),
  CONSTRAINT PK_UID PRIMARY KEY(U_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_USER_LIST  is '用户账户信息表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_USER_LIST.U_ID  is '用户ID';
comment on column SYS_AUTHZ_USER_LIST.U_USERNAME  is '用户名';
comment on column SYS_AUTHZ_USER_LIST.U_PASSWORD  is '用户密码';
comment on column SYS_AUTHZ_USER_LIST.U_SALT  is '用户密码盐：用于密码加解密';
comment on column SYS_AUTHZ_USER_LIST.U_SECRET  is '用户秘钥：用于用户JWT加解密';
comment on column SYS_AUTHZ_USER_LIST.U_STATUS  is '用户状态（0:禁用|1:可用|2:锁定）';
comment on column SYS_AUTHZ_USER_LIST.U_UID  is '用户唯一UID（用户编号）';
comment on column SYS_AUTHZ_USER_LIST.U_CODE  is '用户唯一编号（内部工号）';
comment on column SYS_AUTHZ_USER_LIST.U_APP_ID  is '用户客户端应用ID';
comment on column SYS_AUTHZ_USER_LIST.U_APP_CHANNEL  is '用户客户端应用渠道编码';
comment on column SYS_AUTHZ_USER_LIST.U_APP_VERSION  is '用户客户端版本';
comment on column SYS_AUTHZ_USER_LIST.U_ONLINE  is '用户是否在线（1：是，0：否）';
comment on column SYS_AUTHZ_USER_LIST.U_LATEST_ONLINE  is '用户最近一次在线登录时间';
comment on column SYS_AUTHZ_USER_LIST.U_TIME24  is '初始化时间';

-- Create table
create table SYS_AUTHZ_USER_ROLE_RELATION (
  U_ID   			VARCHAR2(32) not null,
  R_ID   			VARCHAR2(32) not null,
  R_PRTY			VARCHAR2(2) default '0',
  CONSTRAINT UNIQUE_UID_RID UNIQUE(U_ID, R_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_USER_ROLE_RELATION  is '用户-角色关系表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_USER_ROLE_RELATION.U_ID  is '用户ID';
comment on column SYS_AUTHZ_USER_ROLE_RELATION.R_ID  is '角色ID';
comment on column SYS_AUTHZ_USER_ROLE_RELATION.R_PRTY  is '优先级：用于默认登录角色';

