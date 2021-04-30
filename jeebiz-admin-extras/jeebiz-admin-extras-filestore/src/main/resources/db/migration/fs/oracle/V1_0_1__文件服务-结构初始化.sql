
-- Create table
create table SYS_DATA_FILES (
  f_id  		VARCHAR2(32) default sys_guid() not null,
  F_UUid     	VARCHAR2(100) not null,
  F_EXT     	VARCHAR2(50) not null,
  f_name    	VARCHAR2(200) not null,
  F_TO  		VARCHAR2(10) not null,
  F_GROUP		VARCHAR2(50) not null,
  f_path   		VARCHAR2(500) not null,
  F_THUMB		VARCHAR2(500),
  F_Uid     	VARCHAR2(32) not null,
  CONSTRAINT SYS_DATA_FILES_PK PRIMARY KEY(f_id)
);     
-- Add comments to the table 
comment on table SYS_DATA_FILES  is '文件存储信息表';
-- Add comments to the columns 
comment on column SYS_DATA_FILES.f_id  is '文件id';
comment on column SYS_DATA_FILES.F_UUid  is '文件UUid';
comment on column SYS_DATA_FILES.F_EXT  is '文件类型';
comment on column SYS_DATA_FILES.f_name  is '文件名';
comment on column SYS_DATA_FILES.F_TO  is '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储';
comment on column SYS_DATA_FILES.F_GROUP  is '文件存储分组';
comment on column SYS_DATA_FILES.f_path  is '文件存储路径';
comment on column SYS_DATA_FILES.F_THUMB  is '缩略图访问地址（图片类型文件）';
comment on column SYS_DATA_FILES.F_Uid  is '文件所属用户id';
