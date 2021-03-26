
-- Create table
create table SYS_DATA_LOG_AUTHZ (
  LOG_ID  		VARCHAR2(32) default sys_guid() not null,
  LOG_OPT     	VARCHAR2(50) not null,
  LOG_PROTOCOL 	VARCHAR2(100) not null,
  LOG_ADDR		VARCHAR2(50) not null,
  LOG_LOCATION	VARCHAR2(200),
  LOG_STATUS 	VARCHAR2(50) not null,
  LOG_MSG     	VARCHAR2(500),
  LOG_EXCP     	CLOB,
  CREATE_TIME	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CREATOR    	VARCHAR2(32) not null,
  CONSTRAINT SYS_DATA_LOG_AUTHZ_PK PRIMARY KEY(LOG_ID)
);     
-- Add comments to the table 
comment on table SYS_DATA_LOG_AUTHZ  is '认证授权日志信息表';
-- Add comments to the columns 
comment on column SYS_DATA_LOG_AUTHZ.LOG_ID is '日志ID编号';
comment on column SYS_DATA_LOG_AUTHZ.LOG_OPT is '认证类型：login、logout';
comment on column SYS_DATA_LOG_AUTHZ.LOG_PROTOCOL is '认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等';
comment on column SYS_DATA_LOG_AUTHZ.LOG_ADDR is '认证请求来源IP';
comment on column SYS_DATA_LOG_AUTHZ.LOG_LOCATION is '认证请求来源IP所在地点';
comment on column SYS_DATA_LOG_AUTHZ.LOG_STATUS is '认证结果：fail、success';
comment on column SYS_DATA_LOG_AUTHZ.LOG_MSG is '认证请求信息';
comment on column SYS_DATA_LOG_AUTHZ.LOG_EXCP is '认证异常信息';
comment on column SYS_DATA_LOG_AUTHZ.CREATE_TIME is '认证发生时间';
comment on column SYS_DATA_LOG_AUTHZ.CREATOR is '认证用户ID';


-- Create table
create table SYS_DATA_LOG_BIZ (
  LOG_ID  		VARCHAR2(32) default sys_guid() not null,
  LOG_MODULE 	VARCHAR2(50),
  LOG_BIZ 		VARCHAR2(100),
  LOG_OPT     	VARCHAR2(50),
  LOG_ADDR		VARCHAR2(50),
  LOG_LOCATION	VARCHAR2(200),
  LOG_MSG     	VARCHAR2(500),
  LOG_EXCP     	CLOB,
  CREATE_TIME	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CREATOR   	VARCHAR2(32) not null,
  CONSTRAINT SYS_DATA_LOG_BIZ_PK PRIMARY KEY(LOG_ID)
);     
-- Add comments to the table 
comment on table SYS_DATA_LOG_BIZ  is '功能操作日志信息表';
-- Add comments to the columns 
comment on column SYS_DATA_LOG_BIZ.LOG_ID is '日志ID编号';
comment on column SYS_DATA_LOG_BIZ.LOG_MODULE is '功能模块';
comment on column SYS_DATA_LOG_BIZ.LOG_BIZ is '业务名称';
comment on column SYS_DATA_LOG_BIZ.LOG_OPT is '操作类型';
comment on column SYS_DATA_LOG_BIZ.LOG_ADDR is '功能操作请求来源IP';
comment on column SYS_DATA_LOG_BIZ.LOG_LOCATION is '功能操作请求IP所在地点';
comment on column SYS_DATA_LOG_BIZ.LOG_MSG is '功能操作信息';
comment on column SYS_DATA_LOG_BIZ.LOG_EXCP is '功能操作异常';
comment on column SYS_DATA_LOG_BIZ.CREATE_TIME is '功能操作发生时间';
comment on column SYS_DATA_LOG_BIZ.CREATOR is '功能操作人ID';
