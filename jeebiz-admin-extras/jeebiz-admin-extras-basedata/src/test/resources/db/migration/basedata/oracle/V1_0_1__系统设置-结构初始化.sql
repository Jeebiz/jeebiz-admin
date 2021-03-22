
-- Create table
create table SYS_EXTRAS_PAIRVALUE (
  D_ID  		VARCHAR2(32) default sys_guid() not null,
  D_GROUP   	VARCHAR2(50) not null,
  D_LABEL		VARCHAR2(50) not null,
  D_KEY     	VARCHAR2(50) not null,
  D_TEXT    	VARCHAR2(300) not null,
  D_STATUS  	NUMBER default 1 not null,
  D_ORDER   	NUMBER default 1 not null,
  primary key (D_GROUP, D_KEY)
);
-- Add comments to the table 
comment on table SYS_EXTRAS_PAIRVALUE  is '基础数据信息表';
-- Add comments to the columns 
comment on column SYS_EXTRAS_PAIRVALUE.D_ID  is '数据ID';
comment on column SYS_EXTRAS_PAIRVALUE.D_GROUP  is '数据分组';
comment on column SYS_EXTRAS_PAIRVALUE.D_LABEL  is '数据标签';
comment on column SYS_EXTRAS_PAIRVALUE.D_KEY  is '数据键';
comment on column SYS_EXTRAS_PAIRVALUE.D_TEXT  is '数据值';
comment on column SYS_EXTRAS_PAIRVALUE.D_STATUS  is '数据状态:（0:不可用|1：可用）';
comment on column SYS_EXTRAS_PAIRVALUE.D_ORDER  is '数据排序:组内排序';


-- Create table
create table SYS_EXTRAS_SETTINGS (
  D_ID  		VARCHAR2(32) default sys_guid() not null,
  D_GROUP   	VARCHAR2(50) not null,
  D_LABEL		VARCHAR2(50) not null,
  D_KEY     	VARCHAR2(50) not null,
  D_TEXT    	VARCHAR2(300) not null,
  D_UNIT		VARCHAR2(30) default null,
  D_TYPE    	varchar(20) default 'text' not null,
  D_RULES   	VARCHAR2(255),
  D_REMARK  	VARCHAR2(500),
  D_PLACEHOLDER VARCHAR2(255),
  D_SOURCE  	VARCHAR2(1000),
  D_STATUS  	NUMBER default 1 not null,
  D_ORDER   	NUMBER default 1 not null,
  primary key (D_GROUP, D_KEY)
);
-- Add comments to the table 
comment on table SYS_EXTRAS_SETTINGS  is '系统参数设置表';
-- Add comments to the columns 
comment on column SYS_EXTRAS_SETTINGS.D_ID  is '参数ID';
comment on column SYS_EXTRAS_SETTINGS.D_GROUP  is '参数分组';
comment on column SYS_EXTRAS_SETTINGS.D_LABEL  is '参数标签';
comment on column SYS_EXTRAS_SETTINGS.D_KEY  is '参数键';
comment on column SYS_EXTRAS_SETTINGS.D_TEXT  is '参数值';
comment on column SYS_EXTRAS_SETTINGS.D_UNIT  is '参数单位:如 KB';
comment on column SYS_EXTRAS_SETTINGS.D_TYPE  is '参数展示类型：（ text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）';
comment on column SYS_EXTRAS_SETTINGS.D_RULES  is '参数验证规则：如（required|range:[0,100] (多个用|隔开)）';
comment on column SYS_EXTRAS_SETTINGS.D_REMARK  is '参数备注信息';
comment on column SYS_EXTRAS_SETTINGS.D_PLACEHOLDER  is '参数提示信息';
comment on column SYS_EXTRAS_SETTINGS.D_SOURCE  is '参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]'; 
comment on column SYS_EXTRAS_SETTINGS.D_STATUS  is '参数状态:（0:不可用|1：可用）';
comment on column SYS_EXTRAS_SETTINGS.D_ORDER  is '参数排序:组内排序';

