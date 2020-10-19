
-- Create table
create table ZFTAL_FILE_INFO
(
  FILE_UID    VARCHAR2(32) default sys_guid() not null ,
  FILE_UKEY   VARCHAR2(32),
  FILE_SERV   VARCHAR2(20),
  FILE_NAME   VARCHAR2(100),
  FILE_PATH   VARCHAR2(300)
);
-- Add comments to the table 
comment on table ZFTAL_FILE_INFO  is '�ļ�������Ϣ��';
-- Add comments to the columns 
comment on column ZFTAL_FILE_INFO.FILE_UID  is '�ļ�ΨһID�����ڻ�ȡ�ļ�';
comment on column ZFTAL_FILE_INFO.FILE_UKEY  is '�û�ΨһID�����ڹ�����ǰ�ļ��ϴ���ID';
comment on column ZFTAL_FILE_INFO.FILE_SERV  is '�ļ��洢�������ͣ�ftp,smba,dir';
comment on column ZFTAL_FILE_INFO.FILE_NAME  is '�ļ�ԭʼ���ƣ�����ҳ�����';
comment on column ZFTAL_FILE_INFO.FILE_PATH  is '�������洢·��������Ϊ�ļ������ܵ�Ŀ¼�ṹ������Դ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_FILE_INFO add constraint PK_ZFTAL_FILE_INFO primary key (FILE_UID);
