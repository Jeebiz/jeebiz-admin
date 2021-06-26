
-- Create table
create table sys_data_settings (
  d_id  		VARCHAR2(32) default sys_guid() not null,
  d_group   	VARCHAR2(50) not null,
  d_label		VARCHAR2(50) not null,
  d_key     	VARCHAR2(50) not null,
  d_text    	VARCHAR2(500) not null,
  d_unit		VARCHAR2(30) default null,
  d_type    	varchar(20) default 'text' not null,
  d_rules   	VARCHAR2(255),
  d_remark  	VARCHAR2(500),
  d_placeholder VARCHAR2(255),
  d_source  	VARCHAR2(1000),
  d_status  	NUMBER(1) default 1 not null,
  d_order   	NUMBER(3) default 1 not null,
  CONSTRAINT sys_data_settings_UNIQUE UNIQUE(d_group, d_key),
  CONSTRAINT sys_data_settings_PK PRIMARY KEY(d_id)
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
comment on column sys_data_settings.d_type  is '参数展示类型：（ text,textarea,password,checkbox,radio,file,image,color,date,timestamp,email,month,number,range,select,switch,tel,time,week,url）';
comment on column sys_data_settings.d_rules  is '参数验证规则：如（required|range:[0,100] (多个用|隔开)）';
comment on column sys_data_settings.d_remark  is '参数备注信息';
comment on column sys_data_settings.d_placeholder  is '参数提示信息';
comment on column sys_data_settings.d_source  is '参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]';
comment on column sys_data_settings.d_status  is '参数状态:（0:不可用|1：可用）';
comment on column sys_data_settings.d_order  is '参数排序:组内排序';

