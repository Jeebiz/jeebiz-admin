
-- ----------------------------
-- Create table 
-- ----------------------------
create table SYS_EXTRAS_INFORMS(
  	INFO_ID      	VARCHAR2(32) default sys_guid() not null,  
  	INFO_USERID		VARCHAR2(32) not null,  
  	INFO_TYPE   	VARCHAR2(10) not null, 
  	INFO_TITLE		VARCHAR2(200) not null,
  	INFO_DETAIL		CLOB not null,
  	INFO_STATUS		CHAR(1) default '1' not null,
  	TIME24			VARCHAR2(34) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),	
  	CONSTRAINT SYS_EXTRAS_INFORMS_PK PRIMARY KEY(INFO_ID)
);
-- Add comments to the table 
comment on table SYS_EXTRAS_INFORMS is '消息通知信息表';
-- Add comments to the columns 
comment on column SYS_EXTRAS_INFORMS.INFO_ID is '消息通知ID编号';
comment on column SYS_EXTRAS_INFORMS.INFO_USERID  is '消息通知通知对象ID';
comment on column SYS_EXTRAS_INFORMS.INFO_TYPE  is '消息通知类型：（notice：通知、letter：私信）';
comment on column SYS_EXTRAS_INFORMS.INFO_TITLE  is '消息通知标题';
comment on column SYS_EXTRAS_INFORMS.INFO_DETAIL  is '消息通知内容';
comment on column SYS_EXTRAS_INFORMS.INFO_STATUS  is '参数状态:（0:不可用|1：可用）';
comment on column SYS_EXTRAS_INFORMS.TIME24  is '参数排序:组内排序';
