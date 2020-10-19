
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
comment on table ZFTAL_MONITOR_MEMORYS  is 'Ӧ�ü���ڴ��¼��';
-- Add comments to the columns 
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_ID  is '��¼ID';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_KEY  is '���ʹ���ʵ�key���� os.ram.total ';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_TIME  is '������ǰ���ݵ�ʱ���';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_HOST  is '��������';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_JVM  is 'JVM���ƣ������˼�¼��JVM';
comment on column ZFTAL_MONITOR_MEMORYS.MEMORY_VALUE  is '��¼ֵ';
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
comment on table ZFTAL_MONITOR_USAGES  is 'Ӧ�ü��ʹ���ʼ�¼��';
-- Add comments to the columns 
comment on column ZFTAL_MONITOR_USAGES.USAGE_ID  is '��¼ID';
comment on column ZFTAL_MONITOR_USAGES.USAGE_KEY  is '���ʹ���ʵ�key���� os.ram.usage ��ʾ�ڴ��ʹ����';
comment on column ZFTAL_MONITOR_USAGES.USAGE_TIME  is '������ǰ���ݵ�ʱ���';
comment on column ZFTAL_MONITOR_USAGES.USAGE_HOST  is '���������������ò��Ե�ʵ�ַ�ʽ';
comment on column ZFTAL_MONITOR_USAGES.USAGE_JVM  is 'JVM���ƣ������˼�¼��JVM';
comment on column ZFTAL_MONITOR_USAGES.USAGE_VALUE  is 'ʹ���ʣ�';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_MONITOR_USAGES add constraint PK_ZFTAL_MONITOR_USAGES primary key (USAGE_ID);
