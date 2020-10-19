
-- Create table
create table SYS_DATA_FILES (
  F_ID  		VARCHAR2(32) default sys_guid() not null,
  F_UUID     	VARCHAR2(100) not null,
  F_EXT     	VARCHAR2(50) not null,
  F_NAME    	VARCHAR2(200) not null,
  F_TO  		VARCHAR2(10) not null,
  F_GROUP		VARCHAR2(50) not null,
  F_PATH   		VARCHAR2(500) not null,
  F_THUMB		VARCHAR2(500),
  F_UID     	VARCHAR2(32) not null,
  CONSTRAINT SYS_DATA_FILES_PK PRIMARY KEY(F_ID)
);     
-- Add comments to the table 
comment on table SYS_DATA_FILES  is '文件存储信息表';
-- Add comments to the columns 
comment on column SYS_DATA_FILES.F_ID  is '文件ID';
comment on column SYS_DATA_FILES.F_UUID  is '文件UUID';
comment on column SYS_DATA_FILES.F_EXT  is '文件类型';
comment on column SYS_DATA_FILES.F_NAME  is '文件名';
comment on column SYS_DATA_FILES.F_TO  is '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储';
comment on column SYS_DATA_FILES.F_GROUP  is '文件存储分组';
comment on column SYS_DATA_FILES.F_PATH  is '文件存储路径';
comment on column SYS_DATA_FILES.F_THUMB  is '缩略图访问地址（图片类型文件）';
comment on column SYS_DATA_FILES.F_UID  is '文件所属用户ID';
