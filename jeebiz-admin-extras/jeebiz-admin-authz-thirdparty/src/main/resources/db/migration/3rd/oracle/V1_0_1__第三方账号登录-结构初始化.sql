/* 
 * 第三方账号登录表
 */

-- Create table
create table sys_authz_user_thirdparty (
  u_id   		VARCHAR2(32) not null,
  t_id   		VARCHAR2(32) default sys_guid() not null,
  t_type   		VARCHAR2(50) not null,
  t_unionid   	VARCHAR2(500) not null,
  t_openid   	VARCHAR2(500) not null,
  t_devid   	VARCHAR2(500),
  t_rawdata	    VARCHAR2(4000),
  T_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT UK_THIRDPARTY UNIQUE(t_id,t_type),
  CONSTRAINT PK_THIRDPARTY_TID PRIMARY KEY(t_id)
);
-- Add comments to the table 
comment on table sys_authz_user_thirdparty  is '第三方账号登录表';
-- Add comments to the columns 
comment on column sys_authz_user_thirdparty.u_id  is '用户ID';
comment on column sys_authz_user_thirdparty.t_id  is '第三方账号登录表ID';
comment on column sys_authz_user_thirdparty.t_type  is '第三方账号类型：（qq：腾讯QQ，wx：微信）';
comment on column sys_authz_user_thirdparty.t_unionid  is '第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）';
comment on column sys_authz_user_thirdparty.t_openid  is '第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）';
comment on column sys_authz_user_thirdparty.t_devid  is '当前认证对接平台开发者账号ID（通常指与第三方平台进行认证对接的开发者账号的唯一ID）';
comment on column sys_authz_user_thirdparty.t_rawdata  is '第三方认证账号扩展信息';
comment on column sys_authz_user_thirdparty.T_time24  is '第三方账号绑定时间';
