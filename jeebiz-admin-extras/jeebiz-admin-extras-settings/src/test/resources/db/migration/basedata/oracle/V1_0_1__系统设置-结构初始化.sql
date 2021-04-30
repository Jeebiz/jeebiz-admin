
-- Create table
create table SYS_DATA_PAIRVALUE (
  d_id  		VARCHAR2(32) default sys_guid() not null,
  d_group   	VARCHAR2(50) not null,
  d_label		VARCHAR2(50) not null,
  d_key     	VARCHAR2(50) not null,
  d_text    	VARCHAR2(300) not null,
  d_status  	NUMBER default 1 not null,
  d_order   	NUMBER default 1 not null,
  primary key (d_group, d_key)
);
-- Add comments to the table 
comment on table SYS_DATA_PAIRVALUE  is '基础数据信息表';
-- Add comments to the columns 
comment on column SYS_DATA_PAIRVALUE.d_id  is '数据id';
comment on column SYS_DATA_PAIRVALUE.d_group  is '数据分组';
comment on column SYS_DATA_PAIRVALUE.d_label  is '数据标签';
comment on column SYS_DATA_PAIRVALUE.d_key  is '数据键';
comment on column SYS_DATA_PAIRVALUE.d_text  is '数据值';
comment on column SYS_DATA_PAIRVALUE.d_status  is '数据状态:（0:不可用|1：可用）';
comment on column SYS_DATA_PAIRVALUE.d_order  is '数据排序:组内排序';


-- Create table
create table sys_data_settings (
  d_id  		VARCHAR2(32) default sys_guid() not null,
  d_group   	VARCHAR2(50) not null,
  d_label		VARCHAR2(50) not null,
  d_key     	VARCHAR2(50) not null,
  d_text    	VARCHAR2(300) not null,
  d_unit		VARCHAR2(30) default null,
  d_type    	varchar(20) default 'text' not null,
  d_rules   	VARCHAR2(255),
  d_remark  	VARCHAR2(500),
  d_placeholder VARCHAR2(255),
  d_source  	VARCHAR2(1000),
  d_status  	NUMBER default 1 not null,
  d_order   	NUMBER default 1 not null,
  primary key (d_group, d_key)
);
-- Add comments to the table 
comment on table sys_data_settings  is '系统参数设置表';
-- Add comments to the columns 
comment on column sys_data_settings.d_id  is '参数id';
comment on column sys_data_settings.d_group  is '参数分组';
comment on column sys_data_settings.d_label  is '参数标签';
comment on column sys_data_settings.d_key  is '参数键';
comment on column sys_data_settings.d_text  is '参数值';
comment on column sys_data_settings.d_unit  is '参数单位:如 KB';
comment on column sys_data_settings.d_type  is '参数展示类型：（ text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）';
comment on column sys_data_settings.d_rules  is '参数验证规则：如（required|range:[0,100] (多个用|隔开)）';
comment on column sys_data_settings.d_remark  is '参数备注信息';
comment on column sys_data_settings.d_placeholder  is '参数提示信息';
comment on column sys_data_settings.d_source  is '参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]';
comment on column sys_data_settings.d_status  is '参数状态:（0:不可用|1：可用）';
comment on column sys_data_settings.d_order  is '参数排序:组内排序';

