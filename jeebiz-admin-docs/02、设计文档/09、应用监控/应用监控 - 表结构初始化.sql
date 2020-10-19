
-- Create table
create table ZFTAL_MONITOR_MEMORYS
(
  MEMORY_ID     VARCHAR2(32) default sys_guid() not null ,
  MEMORY_KEY   	VARCHAR2(30),
  MEMORY_TIME   VARCHAR2(30),
  MEMORY_HOST   VARCHAR2(200),
  MEMORY_JVM    VARCHAR2(200),
  MEMORY_VALUE  VARCHAR2(50)
);
-- Add comments to the table 
comment on table ZFTAL_MONITOR_MEMORYS  is '应用监控内存记录表';
-- Add comments to the columns 
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_ID  is '记录ID';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_KEY  is '标记使用率的key，如 os.ram.total ';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_TIME  is '产生当前数据的时间戳';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_HOST  is '主机名称';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_JVM  is 'JVM名称：产生此记录的JVM';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_VALUE  is '记录值';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_MONITOR_MEMORYS add constraint PK_ZFTAL_MONITOR_MEMORYS primary key (MEMORY_ID);


-- Create table
create table ZFTAL_MONITOR_USAGES
(
  USAGE_ID     VARCHAR2(32) default sys_guid() not null ,
  USAGE_KEY   VARCHAR2(30),
  USAGE_TIME   VARCHAR2(30),
  USAGE_HOST   VARCHAR2(200),
  USAGE_JVM   VARCHAR2(200),
  USAGE_VALUE  VARCHAR2(50)
);
-- Add comments to the table 
comment on table ZFTAL_MONITOR_USAGES  is '应用监控使用率记录表';
-- Add comments to the columns 
comment on column ZFTAL_MONITOR_USAGES.USAGE_ID  is '记录ID';
comment on column ZFTAL_MONITOR_USAGES.USAGE_KEY  is '标记使用率的key，如 os.ram.usage 表示内存的使用率';
comment on column ZFTAL_MONITOR_USAGES.USAGE_TIME  is '产生当前数据的时间戳';
comment on column ZFTAL_MONITOR_USAGES.USAGE_HOST  is '策略描述，简述该策略的实现方式';
comment on column ZFTAL_MONITOR_USAGES.USAGE_JVM  is 'JVM名称：产生此记录的JVM';
comment on column ZFTAL_MONITOR_USAGES.USAGE_VALUE  is '使用率：';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_MONITOR_USAGES add constraint PK_ZFTAL_MONITOR_USAGES primary key (USAGE_ID);
