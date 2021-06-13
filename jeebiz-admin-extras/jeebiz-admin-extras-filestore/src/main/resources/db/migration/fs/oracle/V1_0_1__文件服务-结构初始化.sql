
-- Create table
create table sys_data_files (
  f_id  		VARCHAR2(32) default sys_guid() not null,
  f_uuid     	VARCHAR2(100) not null,
  f_ext     	VARCHAR2(50) not null,
  f_name    	VARCHAR2(200) not null,
  f_to  		VARCHAR2(10) not null,
  f_group		VARCHAR2(50) DEFAULT 'group1' not null,
  f_path   		VARCHAR2(500) not null,
  f_thumb		VARCHAR2(500),
  f_uid     	VARCHAR2(32) not null,
  f_order		VARCHAR2(11),
  is_delete		VARCHAR2(11),
  creator		VARCHAR2(32),
  create_time	VARCHAR2(32),
  modifyer		VARCHAR2(32),
  modify_time	VARCHAR2(32),
  CONSTRAINT sys_data_files_pk PRIMARY KEY(f_id)
);     
-- Add comments to the table 
comment on table sys_data_files  is '文件存储信息表';
-- Add comments to the columns 
comment on column sys_data_files.f_id  is '文件id';
comment on column sys_data_files.f_uuid  is '文件UUid';
comment on column sys_data_files.f_ext  is '文件类型';
comment on column sys_data_files.f_name  is '文件名';
comment on column sys_data_files.f_to  is '文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储';
comment on column sys_data_files.f_group  is '文件存储分组';
comment on column sys_data_files.f_path  is '文件存储路径';
comment on column sys_data_files.f_thumb  is '缩略图访问地址（图片类型文件）';
comment on column sys_data_files.f_uid  is '文件所属用户id';
comment on column sys_data_files.f_order  is '文件同批次的顺序编号';
comment on column sys_data_files.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_data_files.creator  is '创建人ID';
comment on column sys_data_files.create_time  is '创建时间';
comment on column sys_data_files.modifyer  is '修改人ID';
comment on column sys_data_files.modify_time  is '修改时间';
