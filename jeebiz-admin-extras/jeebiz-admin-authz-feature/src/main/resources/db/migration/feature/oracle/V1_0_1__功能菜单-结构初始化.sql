
/* 菜单核心表：功能菜单信息表、功能操作信息表、功能菜单-功能操作关系表*/

-- Create table
create table sys_authz_feature_list (
	f_id   		VARCHAR2(32) default sys_guid() not null,
  	f_name   	VARCHAR2(50) not null,
  	f_abb 		VARCHAR2(50),
  	f_code 		VARCHAR2(100),
  	f_url     	VARCHAR2(500),
  	f_path     	VARCHAR2(500),
  	f_type  	VARCHAR2(2) default '0',
  	f_icon   	VARCHAR2(200),
  	f_order   	VARCHAR2(5),
  	f_parent   	VARCHAR2(32) not null,
  	f_visible	VARCHAR2(1) default '1',
  	CONSTRAINT PK_FID PRIMARY KEY(f_id)
);
-- Add comments to the table 
comment on table sys_authz_feature_list  is '功能菜单信息表';
-- Add comments to the columns 
comment on column sys_authz_feature_list.f_id  is '功能菜单ID';
comment on column sys_authz_feature_list.f_name  is '功能菜单名称';
comment on column sys_authz_feature_list.f_abb  is '功能菜单简称';
comment on column sys_authz_feature_list.f_code  is '功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据';
comment on column sys_authz_feature_list.f_url  is '功能菜单URL';
comment on column sys_authz_feature_list.f_path  is '功能菜单对应页面相对路径';
comment on column sys_authz_feature_list.f_type  is '菜单类型(1:原生|2:自定义)';
comment on column sys_authz_feature_list.f_icon  is '菜单样式或菜单图标路径';
comment on column sys_authz_feature_list.f_order  is '菜单显示顺序';
comment on column sys_authz_feature_list.f_parent  is '父级功能菜单ID';
comment on column sys_authz_feature_list.f_visible  is '菜单是否可见(1:可见|0:不可见)';

-- Create table
create table sys_authz_feature_opts
(
	f_id    		VARCHAR2(32) not null,
	opt_id   		VARCHAR2(32) default sys_guid() not null,
  	opt_name 		VARCHAR2(100) not null,
  	opt_icon		VARCHAR2(60),
  	opt_order 		VARCHAR2(2),
  	OPT_VISIBLE 	VARCHAR2(1) default '0' not null,
  	opt_perms 		VARCHAR2(50),
  	CONSTRAINT UNIQUE_FID_opt_perms UNIQUE(f_id, opt_perms),
  	CONSTRAINT PK_opt_id PRIMARY KEY(opt_id)
);
-- Add comments to the table 
comment on table sys_authz_feature_opts  is '功能操作信息表';
-- Add comments to the columns 
comment on column sys_authz_feature_opts.f_id  is '功能菜单ID';
comment on column sys_authz_feature_opts.opt_id  is '功能操作信息表ID';
comment on column sys_authz_feature_opts.opt_name  is '功能操作名称';
comment on column sys_authz_feature_opts.opt_icon  is '功能操作图标样式';
comment on column sys_authz_feature_opts.opt_order is '显示顺序';
comment on column sys_authz_feature_opts.OPT_VISIBLE  is '是否可见(1:可见|0:不可见)';
comment on column sys_authz_feature_opts.opt_perms is '权限标记';
