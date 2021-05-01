
-- Create table
create table sys_log_authz (
  log_id  		VARCHAR2(32) default sys_guid() not null,
  log_opt     	VARCHAR2(50) not null,
  log_protocol 	VARCHAR2(100) not null,
  log_addr		VARCHAR2(50) not null,
  log_location	VARCHAR2(200),
  log_status 	VARCHAR2(50) not null,
  log_msg     	VARCHAR2(500),
  log_excp     	CLOB,
  create_time	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  creator    	VARCHAR2(32) not null,
  device_id	VARCHAR2(11),
  u_app_id	VARCHAR2(11),
  u_app_channel	VARCHAR2(50),
  u_app_version	VARCHAR2(20),
  CONSTRAINT sys_log_authz_PK PRIMARY KEY(LOG_id)
);     
-- Add comments to the table 
comment on table sys_log_authz  is '认证授权日志信息表';
-- Add comments to the columns 
comment on column sys_log_authz.log_id is '日志id编号';
comment on column sys_log_authz.log_opt is '认证类型：login、logout';
comment on column sys_log_authz.log_protocol is '认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等';
comment on column sys_log_authz.log_addr is '认证请求来源IP';
comment on column sys_log_authz.log_location is '认证请求来源IP所在地点';
comment on column sys_log_authz.log_status is '认证结果：fail、success';
comment on column sys_log_authz.log_msg is '认证请求信息';
comment on column sys_log_authz.log_excp is '认证异常信息';
comment on column sys_log_authz.create_time is '认证发生时间';
comment on column sys_log_authz.creator is '认证用户id';
comment on column sys_log_authz.device_id is '设备激活记录ID';
comment on column sys_log_authz.u_app_id is '用户登录的客户端应用ID';
comment on column sys_log_authz.u_app_channel is '用户登录的客户端应用渠道编码';
comment on column sys_log_authz.u_app_version is '用户登录的客户端版本';


-- Create table
create table sys_log_biz (
  log_id  		VARCHAR2(32) default sys_guid() not null,
  log_module 	VARCHAR2(50),
  log_biz 		VARCHAR2(100),
  log_opt     	VARCHAR2(50),
  log_addr		VARCHAR2(50),
  log_location	VARCHAR2(200),
  log_msg     	VARCHAR2(500),
  log_excp     	CLOB,
  device_id		VARCHAR2(11),
  u_app_id		VARCHAR2(11),
  u_app_channel	VARCHAR2(50),
  u_app_version	VARCHAR2(20),
  create_time	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  creator   	VARCHAR2(32) not null,
  CONSTRAINT sys_log_biz_pk PRIMARY KEY(log_id)
);     
-- Add comments to the table 
comment on table sys_log_biz  is '功能操作日志信息表';
-- Add comments to the columns 
comment on column sys_log_biz.log_id is '日志id编号';
comment on column sys_log_biz.log_module is '功能模块';
comment on column sys_log_biz.log_biz is '业务名称';
comment on column sys_log_biz.log_opt is '操作类型';
comment on column sys_log_biz.log_addr is '功能操作请求来源IP';
comment on column sys_log_biz.log_location is '功能操作请求IP所在地点';
comment on column sys_log_biz.log_msg is '功能操作信息';
comment on column sys_log_biz.log_excp is '功能操作异常';
comment on column sys_log_biz.create_time is '功能操作发生时间';
comment on column sys_log_biz.creator is '功能操作人id';
comment on column sys_log_biz.device_id is '设备激活记录ID';
comment on column sys_log_biz.u_app_id is '用户登录的客户端应用ID';
comment on column sys_log_biz.u_app_channel is '用户登录的客户端应用渠道编码';
comment on column sys_log_biz.u_app_version is '用户登录的客户端版本';
