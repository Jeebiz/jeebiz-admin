
-- ----------------------------
-- Table structure for sys_data_banner
-- ----------------------------
create table sys_data_banner(
  	id     	    varchar(32) default sys_guid() not null,
    app_id      varchar(50) DEFAULT NULL COMMENT '客户端应用ID',
    app_channel varchar(20) DEFAULT NULL COMMENT '客户端应用渠道编码',
    region_code varchar(30) DEFAULT NULL COMMENT '地区编码',

  	app_uid		varchar(50) not null,
  	app_name   	varchar(100) not null,
  	app_intro 	varchar(2000),
  	app_lang	varchar(50),
  	app_addr	varchar(500),
    app_key	    varchar(100),
    app_secret	varchar(500),
  	app_userid 	varchar(32) not null,
    is_delete		varchar(2),
    creator			varchar(32),
    create_time		varchar(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    modifyer		varchar(32),
    modify_time		varchar(32),
  	PRIMARY KEY (`app_id`)
);
-- Add comments to the table
comment on table sys_data_banner is '我的应用信息表';
-- Add comments to the columns
comment on column sys_data_banner.app_id is '主键ID';
comment on column sys_data_banner.app_uid  is '应用UID';
comment on column sys_data_banner.app_name  is '应用名称';
comment on column sys_data_banner.app_intro  is '应用描述';
comment on column sys_data_banner.app_lang  is '应用开发语言';
comment on column sys_data_banner.app_addr  is '应用部署地址';
comment on column sys_data_banner.app_key  is '应用Key';
comment on column sys_data_banner.app_secret  is '应用Secret';
comment on column sys_data_banner.app_userid  is '应用所属人ID';
comment on column sys_data_banner.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_data_banner.creator  is '创建人ID';
comment on column sys_data_banner.create_time  is '创建时间';
comment on column sys_data_banner.modifyer  is '修改人ID';
comment on column sys_data_banner.modify_time  is '修改时间';
