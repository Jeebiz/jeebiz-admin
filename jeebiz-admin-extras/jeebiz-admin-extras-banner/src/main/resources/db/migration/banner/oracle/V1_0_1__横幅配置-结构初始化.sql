
-- ----------------------------
-- Table structure for sys_data_banner
-- ----------------------------
create table sys_data_banner(
  	id     	    varchar(32) default sys_guid() not null,
    app_id      varchar(50) not null,
    app_channel varchar(200) not null,
    region_code varchar(300) not null,
    language	varchar(50) not null,
    title       varchar(50) not null,
    desc	    clob,
    icon_url   	varchar(255) not null,
    img_url 	varchar(255) not null,
    jump_url 	varchar(255) not null,
    type    	number(4) not null,
    status	    number(4) not null,
    link_type	number(3) not null,
    extend   	varchar(255) default null,
    priority   	number(5) default 999,
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
comment on column sys_data_banner.id is '主键ID';
comment on column sys_data_banner.app_id is '客户端应用ID；多个客户端使用,拼接';
comment on column sys_data_banner.app_channel  is '客户端应用渠道编码；多个编码使用,拼接';
comment on column sys_data_banner.region_code  is '地区编码；多个编码使用,拼接';
comment on column sys_data_banner.language  is '语区标签；多个语区使用,拼接';
comment on column sys_data_banner.title  is '横幅图片路径';
comment on column sys_data_banner.desc  is '该横幅对应的描述';
comment on column sys_data_banner.icon_url  is '横幅图标路径';
comment on column sys_data_banner.img_url  is '横幅图片路径';
comment on column sys_data_banner.jump_url  is '跳转路径';
comment on column sys_data_banner.type  is '横幅类型（1：首页轮播图、2：我的页面轮播图、3：搜索页面轮播图）';
comment on column sys_data_banner.status  is '显示状态（0：不显示、1：显示）';
comment on column sys_data_banner.link_type  is '链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）';
comment on column sys_data_banner.extend  is '扩展字段：过期时间、等待时间';
comment on column sys_data_banner.priority  is '权重值，数字越小越靠前';
comment on column sys_data_banner.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_data_banner.creator  is '创建人ID';
comment on column sys_data_banner.create_time  is '创建时间';
comment on column sys_data_banner.modifyer  is '修改人ID';
comment on column sys_data_banner.modify_time  is '修改时间';
