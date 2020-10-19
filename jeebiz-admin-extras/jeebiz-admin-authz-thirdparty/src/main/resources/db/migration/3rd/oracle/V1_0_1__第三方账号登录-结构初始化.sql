/* 
 * 第三方账号登录表
 */

-- Create table
create table SYS_AUTHZ_USER_THIRDPARTY (
  U_ID   		VARCHAR2(32) not null,
  T_ID   		VARCHAR2(32) default sys_guid() not null,
  T_TYPE   		VARCHAR2(50) not null,
  T_UNIONID   	VARCHAR2(500) not null,
  T_OPENID   	VARCHAR2(500) not null,
  T_DEVID   	VARCHAR2(500),
  T_RAWDATA	    VARCHAR2(4000),
  T_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT UK_THIRDPARTY UNIQUE(T_ID,T_TYPE),
  CONSTRAINT PK_THIRDPARTY_TID PRIMARY KEY(T_ID)
);
-- Add comments to the table 
comment on table SYS_AUTHZ_USER_THIRDPARTY  is '第三方账号登录表';
-- Add comments to the columns 
comment on column SYS_AUTHZ_USER_THIRDPARTY.U_ID  is '用户ID';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_ID  is '第三方账号登录表ID';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_TYPE  is '第三方账号类型：（qq：腾讯QQ，wx：微信）';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_UNIONID  is '第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_OPENID  is '第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_DEVID  is '当前认证对接平台开发者账号ID（通常指与第三方平台进行认证对接的开发者账号的唯一ID）';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_RAWDATA  is '第三方认证账号扩展信息';
comment on column SYS_AUTHZ_USER_THIRDPARTY.T_TIME24  is '第三方账号绑定时间';
