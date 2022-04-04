/*
 * 权限核心表：
 * 2、用户信息表、角色信息表、用户-角色关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- Create table
create table sys_authz_role_list (
  r_id   		VARCHAR2(32) default sys_guid() not null,
  r_key   		VARCHAR2(50) not null,
  r_name   		VARCHAR2(50) not null,
  r_type   		VARCHAR2(2) default 1,
  r_intro  		VARCHAR2(1000),
  r_status		VARCHAR2(2) default 1,
  create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT UNIQUE_r_key UNIQUE(r_key),
  CONSTRAINT PK_Rid PRIMARY KEY(r_id)
);
-- Add comments to the table
comment on table sys_authz_role_list  is '角色信息表';
-- Add comments to the columns
comment on column sys_authz_role_list.r_id  is '角色id';
comment on column sys_authz_role_list.r_key  is '角色编码';
comment on column sys_authz_role_list.r_name  is '角色名称';
comment on column sys_authz_role_list.r_type  is '角色类型（1:原生|2:继承|3:复制|4:自定义）';
comment on column sys_authz_role_list.r_intro  is '角色简介';
comment on column sys_authz_role_list.r_status  is '角色状态（0:禁用|1:可用）';
comment on column sys_authz_role_list.create_time  is '初始化时间';

-- Create table
create table sys_authz_role_perms (
  r_id   		VARCHAR2(32) not null,
  perms 		VARCHAR2(50) not null,
  CONSTRAINT UNIQUE_Rid_perms UNIQUE(r_id, perms)
);
-- Add comments to the table
comment on table sys_authz_role_perms  is '角色-权限关系表（角色-菜单-按钮）';
-- Add comments to the columns
comment on column sys_authz_role_perms.r_id  is '角色id';
comment on column sys_authz_role_perms.perms  is '权限标记：(等同sys_authz_feature_opts.opt_perms)';

-- Create table
create table sys_authz_user_list (
  u_id   			VARCHAR2(32) default sys_guid() not null,
  u_username 		VARCHAR2(100) not null,
  u_password 		VARCHAR2(100) not null,
  u_salt			VARCHAR2(64),
  u_secret			VARCHAR2(128),
  u_status			VARCHAR2(1),
  u_uid   			VARCHAR2(32),
  u_code   			VARCHAR2(32),
  u_app_id 			VARCHAR2(50),
  u_app_channel		VARCHAR2(50),
  u_app_version		VARCHAR2(20),
  u_online			VARCHAR2(1),
  u_latest_online	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  create_time			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT idx_uname UNIQUE(u_username),
  CONSTRAINT idx_uuid UNIQUE(u_uid),
  CONSTRAINT PK_Uid PRIMARY KEY(u_id)
);
-- Add comments to the table
comment on table sys_authz_user_list  is '用户账户信息表';
-- Add comments to the columns
comment on column sys_authz_user_list.u_id  is '用户id';
comment on column sys_authz_user_list.u_username  is '用户名';
comment on column sys_authz_user_list.u_password  is '用户密码';
comment on column sys_authz_user_list.u_salt  is '用户密码盐：用于密码加解密';
comment on column sys_authz_user_list.u_secret  is '用户秘钥：用于用户JWT加解密';
comment on column sys_authz_user_list.u_status  is '用户状态（0:禁用|1:可用|2:锁定）';
comment on column sys_authz_user_list.u_uid  is '用户唯一Uid（用户编号）';
comment on column sys_authz_user_list.u_code  is '用户唯一编号（内部工号）';
comment on column sys_authz_user_list.u_app_id  is '用户客户端应用id';
comment on column sys_authz_user_list.u_app_channel  is '用户客户端应用渠道编码';
comment on column sys_authz_user_list.u_app_version  is '用户客户端版本';
comment on column sys_authz_user_list.u_online  is '用户是否在线（1：是，0：否）';
comment on column sys_authz_user_list.u_latest_online  is '用户最近一次在线登录时间';
comment on column sys_authz_user_list.create_time  is '初始化时间';

-- Create table
create table sys_authz_user_profile (
  u_pid   			VARCHAR2(32) default sys_guid() not null,
  u_id   			VARCHAR2(32) not null,
  u_nickname		VARCHAR2(100) not null,
  u_avatar			VARCHAR2(300),
  u_region_code	VARCHAR2(20),
  u_phone			VARCHAR2(11),
  u_email			VARCHAR2(100),
  u_birthday		VARCHAR2(20),
  u_gender			VARCHAR2(2),
  u_idcard			VARCHAR2(20),
  u_age				VARCHAR2(3),
  u_height			VARCHAR2(4),
  u_weight			VARCHAR2(5),
  u_language		VARCHAR2(50),
  u_intro			VARCHAR2(500),
  u_photos			VARCHAR2(2000),
  u_province		VARCHAR2(50),
  u_city			VARCHAR2(50),
  u_area			VARCHAR2(50),
  u_wgs84_lng		VARCHAR2(50),
  u_wgs84_lat		VARCHAR2(50),
  u_degree			VARCHAR2(3),
  create_time			VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_Uid PRIMARY KEY(u_pid)
);
-- Add comments to the table
comment on table sys_authz_user_profile  is '用户描述信息表';
-- Add comments to the columns
comment on column sys_authz_user_profile.u_pid  is '用户描述id';
comment on column sys_authz_user_profile.u_id  is '用户id';
comment on column sys_authz_user_profile.u_nickname  is '用户昵称';
comment on column sys_authz_user_profile.u_avatar  is '用户头像：图片路径或图标样式';
comment on column sys_authz_user_profile.u_phone  is '手机号码';
comment on column sys_authz_user_profile.u_email  is '邮箱地址';
comment on column sys_authz_user_profile.u_birthday  is '出生日期';
comment on column sys_authz_user_profile.u_gender  is '性别：（M：男，F：女）';
comment on column sys_authz_user_profile.u_idcard  is '身份证号码';
comment on column sys_authz_user_profile.u_age  is '用户年龄';
comment on column sys_authz_user_profile.u_height  is '用户身高';
comment on column sys_authz_user_profile.u_weight  is '用户体重';
comment on column sys_authz_user_profile.u_language  is '官方语言';
comment on column sys_authz_user_profile.u_intro  is '用户简介';
comment on column sys_authz_user_profile.u_photos  is '个人生活照片（包含是否封面标记、序号、地址的JSON字符串）';
comment on column sys_authz_user_profile.u_province  is '用户位置：常驻省份';
comment on column sys_authz_user_profile.u_city  is '用户位置：常驻城市';
comment on column sys_authz_user_profile.u_area  is '用户位置：常驻区域';
comment on column sys_authz_user_profile.u_wgs84_lng  is '用户位置：wgs84经度';
comment on column sys_authz_user_profile.u_wgs84_lat  is '用户位置：wgs84纬度';
comment on column sys_authz_user_profile.u_degree  is '用户信息完成度';
comment on column sys_authz_user_profile.create_time  is '初始化时间';

-- Create table
create table sys_authz_user_role_relation (
  u_id   			VARCHAR2(32) not null,
  r_id   			VARCHAR2(32) not null,
  r_prty			VARCHAR2(2) default '0',
  CONSTRAINT UNIQUE_Uid_Rid UNIQUE(u_id, r_id)
);
-- Add comments to the table
comment on table sys_authz_user_role_relation  is '用户-角色关系表';
-- Add comments to the columns
comment on column sys_authz_user_role_relation.u_id  is '用户id';
comment on column sys_authz_user_role_relation.r_id  is '角色id';
comment on column sys_authz_user_role_relation.r_prty  is '优先级：用于默认登录角色';

