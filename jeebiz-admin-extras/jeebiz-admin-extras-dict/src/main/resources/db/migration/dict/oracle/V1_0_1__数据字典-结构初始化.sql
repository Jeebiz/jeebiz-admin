
-- Create table
create table sys_data_pairgroup (
  g_id  		VARCHAR2(32) default sys_guid() not null,
  g_key     	VARCHAR2(50) not null,
  g_text    	VARCHAR2(500) not null,
  g_status  	NUMBER default 1 not null,
  g_order   	NUMBER default 1 not null,
  CONSTRAINT sys_data_pairgroup_UNIQUE UNIQUE(g_key),
  CONSTRAINT sys_data_pairgroup_PK PRIMARY KEY(g_id)
);     
-- Add comments to the table 
comment on table sys_data_pairgroup  is '基础数据分组信息表';
-- Add comments to the columns 
comment on column sys_data_pairgroup.g_id  is '数据分组id';
comment on column sys_data_pairgroup.g_key  is '数据分组键';
comment on column sys_data_pairgroup.g_text  is '数据分组值';
comment on column sys_data_pairgroup.g_status  is '数据分组状态:（0:禁用|1：可用）';
comment on column sys_data_pairgroup.g_order  is '数据分组排序';

-- Create table
create table sys_data_pairvalue (
  d_id  		VARCHAR2(32) default SYS_guid() not null,
  d_group   	VARCHAR2(100) not null,
  d_label		VARCHAR2(200) not null,
  d_key     	VARCHAR2(50) not null,
  d_value    	VARCHAR2(500) not null,
  d_text    	VARCHAR2(2000),
  d_status  	NUMBER(1) default 1 not null,
  d_order   	NUMBER(10) default 1 not null,
  CONSTRAINT sys_data_pairvalue_UNIQUE UNIQUE(d_group, d_key),
  CONSTRAINT sys_data_pairvalue_PK PRIMARY KEY(d_id)
);
-- Add comments to the table 
comment on table sys_data_pairvalue  is '基础数据信息表';
-- Add comments to the columns 
comment on column sys_data_pairvalue.d_id  is '数据id';
comment on column sys_data_pairvalue.d_group  is '数据分组';
comment on column sys_data_pairvalue.d_label  is '数据标签';
comment on column sys_data_pairvalue.d_key  is '数据键';
comment on column sys_data_pairvalue.d_value  is '数据值';
comment on column sys_data_pairvalue.d_text  is '数据描述';
comment on column sys_data_pairvalue.d_status  is '数据状态:（0:不可用|1：可用）';
comment on column sys_data_pairvalue.d_order  is '数据排序:组内排序';
