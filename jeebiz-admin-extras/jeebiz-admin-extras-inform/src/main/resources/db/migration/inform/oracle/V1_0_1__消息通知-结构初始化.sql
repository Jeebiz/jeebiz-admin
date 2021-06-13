
-- ----------------------------
-- Create table 
-- ----------------------------
create table sys_inform_templates (
  	t_id      		VARCHAR2(32) default sys_guid() not null,
  	t_uid			VARCHAR2(32) not null,
  	t_target   		VARCHAR2(10) default 'ALL',
  	t_provider   	VARCHAR2(10) not null,
  	t_title			VARCHAR2(200) not null,
  	t_content		VARCHAR2(2000),
  	t_tid			VARCHAR2(200),
  	t_payload		VARCHAR2(2000),
  	t_status		CHAR(1) default '0' not null,
    is_delete		VARCHAR2(2),
    creator		    VARCHAR2(32),
    create_time	    VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    modifyer		VARCHAR2(32),
    modify_time	    VARCHAR2(32),
  	CONSTRAINT INFORMS_PK PRIMARY KEY(t_id)
);
-- Add comments to the table 
comment on table sys_inform_templates is '消息模板信息表';
-- Add comments to the columns 
comment on column sys_inform_templates.t_id is '消息模板id';
comment on column sys_inform_templates.t_uid  is '消息模板创建人id';
comment on column sys_inform_templates.t_target  is '消息模板面向对象';
comment on column sys_inform_templates.t_provider  is '消息模板的发送提供者';
comment on column sys_inform_templates.t_title  is '消息模板标题（可能包含变量）';
comment on column sys_inform_templates.t_content  is '消息模板内容（可能包含变量）';
comment on column sys_inform_templates.t_tid  is '消息模板对应第三方平台内的模板id';
comment on column sys_inform_templates.t_payload  is '消息模板变量载体,JOSN格式的数据';
comment on column sys_inform_templates.t_status  is '消息模板状态：（0:停用、1:启用）';
comment on column sys_inform_targets.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_inform_targets.creator  is '创建人ID';
comment on column sys_inform_targets.create_time  is '创建时间';
comment on column sys_inform_targets.modifyer  is '修改人ID';
comment on column sys_inform_targets.modify_time  is '修改时间';

-- ----------------------------
-- Create table 
-- ----------------------------
create table sys_inform_targets (
  	t_id      		VARCHAR2(32) not null,
  	t_uid			VARCHAR2(32) not null,
  	t_status		CHAR(1) default '0' not null,
  	time24			VARCHAR2(34),
    is_delete		VARCHAR2(2),
    creator		    VARCHAR2(32),
    create_time	    VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    modifyer		VARCHAR2(32),
    modify_time	    VARCHAR2(32),
  	CONSTRAINT INFORM_TARGETS_UK PRIMARY KEY(t_id,t_uid)
);
-- Add comments to the table 
comment on table sys_inform_targets is '消息通知对象表';
-- Add comments to the columns 
comment on column sys_inform_targets.t_id is '消息通知id';
comment on column sys_inform_targets.t_uid  is '消息通知接收人id';
comment on column sys_inform_targets.t_status  is '消息通知发送状态：（0:待发送、1:已发送）';
comment on column sys_inform_targets.time24  is '消息通知发送时间';
comment on column sys_inform_targets.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_inform_targets.creator  is '创建人ID';
comment on column sys_inform_targets.create_time  is '创建时间';
comment on column sys_inform_targets.modifyer  is '修改人ID';
comment on column sys_inform_targets.modify_time  is '修改时间';

-- ----------------------------
-- Create table 
-- ----------------------------
create table sys_inform_records (
  	r_id      		VARCHAR2(32) default sys_guid() not null,
  	r_uid			VARCHAR2(32) not null,
	r_provider     	VARCHAR2(50) not null,
  	r_tag			VARCHAR2(20),
  	r_title			VARCHAR2(200) not null,
  	r_content		VARCHAR2(2000),
  	r_tid			VARCHAR2(200),
  	r_to			VARCHAR2(200) not null,
  	r_payload		VARCHAR2(2000) not null,
  	r_status		CHAR(1) default '0' not null,,
    is_delete		VARCHAR2(2),
    creator		    VARCHAR2(32),
    create_time	    VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    modifyer		VARCHAR2(32),
    modify_time	    VARCHAR2(32),
  	CONSTRAINT INFORM_RECORDS_PK PRIMARY KEY(r_id)
);
-- Add comments to the table 
comment on table sys_inform_records is '消息通知记录表';
-- Add comments to the columns 
comment on column sys_inform_records.r_id is '消息通知记录id';
comment on column sys_inform_records.r_uid is '消息通知发送人id';
comment on column sys_inform_records.r_provider  is '消息通知的发送提供者';
comment on column sys_inform_records.r_tag  is '消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）';
comment on column sys_inform_records.r_title  is '消息通知标题（变量处理后的标题）';
comment on column sys_inform_records.r_content  is '消息通知内容（变量处理后的内容）';
comment on column sys_inform_records.r_tid  is '消息通知模板id（系统内信息模板、微信订阅消息等模板id）';
comment on column sys_inform_records.r_to  is '消息通知接收人id';
comment on column sys_inform_records.r_payload  is '通知信息关联数据载体,JOSN格式的数据';
comment on column sys_inform_records.r_status  is '消息通知阅读状态：（0:未阅读、1:已阅读）';
comment on column sys_inform_records.is_delete  is '是否删除（0：未删除，1：已删除）';
comment on column sys_inform_records.creator  is '消息通知创建人id';
comment on column sys_inform_records.create_time  is '消息通知创建时间';
comment on column sys_inform_records.modifyer  is '消息通知更新人id';
comment on column sys_inform_records.modify_time  is '消息通知更新时间';