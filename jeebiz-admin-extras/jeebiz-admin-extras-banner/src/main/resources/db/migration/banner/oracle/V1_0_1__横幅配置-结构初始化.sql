
-- ----------------------------
-- Table structure for sys_data_myapp
-- ----------------------------
create table sys_data_myapp(
  	app_id     	VARCHAR2(32) default sys_guid() not null,
  	app_uid		VARCHAR2(50) not null,
  	app_name   	VARCHAR2(100) not null,
  	app_intro 	VARCHAR2(2000),
  	app_lang	VARCHAR2(50),
  	app_addr	VARCHAR2(500),
    app_key	    VARCHAR2(100),
    app_secret	VARCHAR2(500),
  	app_userid 	VARCHAR2(32) not null,
    is_delete		VARCHAR2(2),
    creator			VARCHAR2(32),
    create_time		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    modifyer		VARCHAR2(32),
    modify_time		VARCHAR2(32),
  	PRIMARY KEY (`app_id`)
);
-- Add comments to the table
comment on table sys_data_myapp is '我的应用信息表';
-- Add comments to the columns
comment on column sys_data_myapp.app_id is '应用ID编号';
comment on column sys_data_myapp.app_uid  is '应用UID';
comment on column sys_data_myapp.app_name  is '应用名称';
comment on column sys_data_myapp.app_intro  is '应用描述';
comment on column sys_data_myapp.app_lang  is '应用开发语言';
comment on column sys_data_myapp.app_addr  is '应用部署地址';
comment on column sys_data_myapp.app_key  is '应用Key';
comment on column sys_data_myapp.app_secret  is '应用Secret';
comment on column sys_data_myapp.app_userid  is '应用所属人ID';
comment on column sys_data_myapp.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_data_myapp.creator  is '创建人ID';
comment on column sys_data_myapp.create_time  is '创建时间';
comment on column sys_data_myapp.modifyer  is '修改人ID';
comment on column sys_data_myapp.modify_time  is '修改时间';
