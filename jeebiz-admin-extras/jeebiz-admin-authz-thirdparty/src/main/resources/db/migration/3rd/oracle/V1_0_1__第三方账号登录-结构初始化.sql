/* 
 * 第三方账号登录表
 */
-- Create table
create table sys_authz_thirdparty (
  u_id   		VARCHAR2(32) not null,
  t_id   		VARCHAR2(32) default sys_guid() not null,
  t_type   		VARCHAR2(50) not null,
  t_unionid   	VARCHAR2(500) not null,
  t_openid   	VARCHAR2(500) not null,
  t_devid   	VARCHAR2(500),
  t_rawdata	    VARCHAR2(2000),
  is_delete		VARCHAR2(2),
  creator		VARCHAR2(32),
  create_time	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  modifyer		VARCHAR2(32),
  modify_time	VARCHAR2(32),
  CONSTRAINT UK_THIRDPARTY UNIQUE(t_id,t_type),
  CONSTRAINT PK_THIRDPARTY_Tid PRIMARY KEY(t_id)
);
-- Add comments to the table 
comment on table sys_authz_thirdparty  is '第三方账号登录表';
-- Add comments to the columns 
comment on column sys_authz_thirdparty.u_id  is '用户id';
comment on column sys_authz_thirdparty.t_id  is '第三方账号登录表id';
comment on column sys_authz_thirdparty.t_type  is '第三方账号类型：（qq：腾讯QQ，wx：微信）';
comment on column sys_authz_thirdparty.t_unionid  is '第三方平台Unionid（通常指第三方账号体系下用户的唯一id）';
comment on column sys_authz_thirdparty.t_openid  is '第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）';
comment on column sys_authz_thirdparty.t_devid  is '当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）';
comment on column sys_authz_thirdparty.t_rawdata  is '第三方认证账号扩展信息';
comment on column sys_authz_thirdparty.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_authz_thirdparty.creator  is '创建人ID';
comment on column sys_authz_thirdparty.create_time  is '创建时间';
comment on column sys_authz_thirdparty.modifyer  is '修改人ID';
comment on column sys_authz_thirdparty.modify_time  is '修改时间';
