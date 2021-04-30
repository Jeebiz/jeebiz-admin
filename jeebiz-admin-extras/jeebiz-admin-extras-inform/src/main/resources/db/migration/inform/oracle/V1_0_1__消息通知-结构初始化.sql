
-- ----------------------------
-- Create table 
-- ----------------------------
create table SYS_INFORM_TEMPlatES (
  	t_id      		VARCHAR2(32) default sys_guid() not null,
  	t_uid			VARCHAR2(32) not null,
  	T_TARGET   		VARCHAR2(10) default 'ALL',
  	T_PROVidER   	VARCHAR2(10) not null,
  	T_TITLE			VARCHAR2(200) not null,
  	T_CONTENT		VARCHAR2(2000),
  	t_tid			VARCHAR2(200),
  	T_PAYLOAD		VARCHAR2(2000),
  	t_status		CHAR(1) default '0' not null,
  	time24			VARCHAR2(34) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT INFORMS_PK PRIMARY KEY(t_id)
);
-- Add comments to the table 
comment on table SYS_INFORM_TEMPlatES is '消息通知信息表';
-- Add comments to the columns 
comment on column SYS_INFORM_TEMPlatES.t_id is '消息通知id';
comment on column SYS_INFORM_TEMPlatES.t_uid  is '消息通知创建人id';
comment on column SYS_INFORM_TEMPlatES.T_TARGET  is '消息通知面向对象';
comment on column SYS_INFORM_TEMPlatES.T_PROVidER  is '消息通知的发送提供者';
comment on column SYS_INFORM_TEMPlatES.T_TITLE  is '消息通知标题（可能包含变量）';
comment on column SYS_INFORM_TEMPlatES.T_CONTENT  is '消息通知内容（可能包含变量）';
comment on column SYS_INFORM_TEMPlatES.t_tid  is '消息通知对应第三方平台内的模板id';
comment on column SYS_INFORM_TEMPlatES.T_PAYLOAD  is '消息通知变量载体,JOSN格式的数据';
comment on column SYS_INFORM_TEMPlatES.t_status  is '消息通知状态：（0:停用、1:启用）';
comment on column SYS_INFORM_TEMPlatES.time24  is '消息通知创建时间';

-- ----------------------------
-- Create table 
-- ----------------------------
create table SYS_INFORM_TARGETS (
  	t_id      		VARCHAR2(32) not null,
  	t_uid			VARCHAR2(32) not null,
  	t_status		CHAR(1) default '0' not null,
  	time24			VARCHAR2(34),
  	CONSTRAINT INFORM_TARGETS_UK PRIMARY KEY(t_id,t_uid)
);
-- Add comments to the table 
comment on table SYS_INFORM_TARGETS is '消息通知对象表';
-- Add comments to the columns 
comment on column SYS_INFORM_TARGETS.t_id is '消息通知id';
comment on column SYS_INFORM_TARGETS.t_uid  is '消息通知接收人id';
comment on column SYS_INFORM_TARGETS.t_status  is '消息通知发送状态：（0:待发送、1:已发送）';
comment on column SYS_INFORM_TARGETS.time24  is '消息通知发送时间';

-- ----------------------------
-- Create table 
-- ----------------------------
create table SYS_INFORM_RECORDS (
  	r_id      		VARCHAR2(32) default sys_guid() not null,
  	R_Uid			VARCHAR2(32) not null,
	R_PROVidER     	VARCHAR2(50) not null,
  	R_TAG			VARCHAR2(20),
  	R_TITLE			VARCHAR2(200) not null,
  	R_CONTENT		VARCHAR2(2000),
  	R_Tid			VARCHAR2(200),
  	R_TO			VARCHAR2(200) not null,
  	R_PAYLOAD		VARCHAR2(2000) not null,
  	r_status		CHAR(1) default '0' not null,
  	time24			VARCHAR2(34) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	CONSTRAINT INFORM_RECORDS_PK PRIMARY KEY(r_id)
);
-- Add comments to the table 
comment on table SYS_INFORM_RECORDS is '消息通知记录表';
-- Add comments to the columns 
comment on column SYS_INFORM_RECORDS.r_id is '消息通知记录id';
comment on column SYS_INFORM_RECORDS.R_Uid is '消息通知发送人id';
comment on column SYS_INFORM_RECORDS.R_PROVidER  is '消息通知的发送提供者';
comment on column SYS_INFORM_RECORDS.R_TAG  is '消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）';
comment on column SYS_INFORM_RECORDS.R_TITLE  is '消息通知标题（变量处理后的标题）';
comment on column SYS_INFORM_RECORDS.R_CONTENT  is '消息通知内容（变量处理后的内容）';
comment on column SYS_INFORM_RECORDS.R_Tid  is '消息通知模板id（系统内信息模板、微信订阅消息等模板id）';
comment on column SYS_INFORM_RECORDS.R_TO  is '消息通知接收人id';
comment on column SYS_INFORM_RECORDS.R_PAYLOAD  is '通知信息关联数据载体,JOSN格式的数据';
comment on column SYS_INFORM_RECORDS.r_status  is '消息通知阅读状态：（0:未阅读、1:已阅读）';
comment on column SYS_INFORM_RECORDS.time24  is '消息通知发送时间';
  