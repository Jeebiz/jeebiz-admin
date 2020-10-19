
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
comment on table ZFTAL_FILE_INFO  is '文件对象信息表';
-- Add comments to the columns 
comment on column ZFTAL_FILE_INFO.FILE_UID  is '文件唯一ID：用于获取文件';
comment on column ZFTAL_FILE_INFO.FILE_UKEY  is '用户唯一ID：用于关联当前文件上传者ID';
comment on column ZFTAL_FILE_INFO.FILE_SERV  is '文件存储服务类型：ftp,smba,dir';
comment on column ZFTAL_FILE_INFO.FILE_NAME  is '文件原始名称：用于页面回显';
comment on column ZFTAL_FILE_INFO.FILE_PATH  is '服务器存储路径：可作为文件管理功能的目录结构数据来源';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_FILE_INFO add constraint PK_ZFTAL_FILE_INFO primary key (FILE_UID);
