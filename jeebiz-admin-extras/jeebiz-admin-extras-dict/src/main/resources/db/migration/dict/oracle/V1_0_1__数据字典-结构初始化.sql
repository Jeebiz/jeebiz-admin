
-- Create table
create table SYS_DATA_PAIRGROUP (
  G_ID  		VARCHAR2(32) default sys_guid() not null,
  G_KEY     	VARCHAR2(50) not null,
  G_TEXT    	VARCHAR2(500) not null,
  G_STATUS  	NUMBER default 1 not null,
  G_ORDER   	NUMBER default 1 not null,
  CONSTRAINT SYS_DATA_PAIRGROUP_UNIQUE UNIQUE(G_KEY),
  CONSTRAINT SYS_DATA_PAIRGROUP_PK PRIMARY KEY(G_ID)
);     
-- Add comments to the table 
comment on table SYS_DATA_PAIRGROUP  is '基础数据分组信息表';
-- Add comments to the columns 
comment on column SYS_DATA_PAIRGROUP.G_ID  is '数据分组ID';
comment on column SYS_DATA_PAIRGROUP.G_KEY  is '数据分组键';
comment on column SYS_DATA_PAIRGROUP.G_TEXT  is '数据分组值';
comment on column SYS_DATA_PAIRGROUP.G_STATUS  is '数据分组状态:（0:禁用|1：可用）';
comment on column SYS_DATA_PAIRGROUP.G_ORDER  is '数据分组排序';

-- Create table
create table SYS_DATA_PAIRVALUE (
  D_ID  		VARCHAR2(32) default SYS_guid() not null,
  d_group   	VARCHAR2(100) not null,
  D_LABEL		VARCHAR2(200) not null,
  D_KEY     	VARCHAR2(50) not null,
  D_VALUE    	VARCHAR2(500) not null,
  D_TEXT    	VARCHAR2(2000),
  d_status  	NUMBER(1) default 1 not null,
  d_order   	NUMBER(10) default 1 not null,
  CONSTRAINT SYS_DATA_PAIRVALUE_UNIQUE UNIQUE(d_group, D_KEY),
  CONSTRAINT SYS_DATA_PAIRVALUE_PK PRIMARY KEY(D_ID)
);
-- Add comments to the table 
comment on table SYS_DATA_PAIRVALUE  is '基础数据信息表';
-- Add comments to the columns 
comment on column SYS_DATA_PAIRVALUE.D_ID  is '数据ID';
comment on column SYS_DATA_PAIRVALUE.d_group  is '数据分组';
comment on column SYS_DATA_PAIRVALUE.D_LABEL  is '数据标签';
comment on column SYS_DATA_PAIRVALUE.D_KEY  is '数据键';
comment on column SYS_DATA_PAIRVALUE.D_VALUE  is '数据值';
comment on column SYS_DATA_PAIRVALUE.D_TEXT  is '数据描述';
comment on column SYS_DATA_PAIRVALUE.d_status  is '数据状态:（0:不可用|1：可用）';
comment on column SYS_DATA_PAIRVALUE.d_order  is '数据排序:组内排序';
