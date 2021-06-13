/*==============================================================*/
/* 通知公告表结构                                      			*/
/*==============================================================*/

-- Create table
create table sys_article_category (
  c_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  c_uid 		VARCHAR2(32) NOT NULL,
  C_name 		VARCHAR2(500) NOT NULL,
  C_GRADE		NUMBER(3) DEFAULT 0,
  C_INTRO		VARCHAR2(2000) DEFAULT NULL,
  C_KEYWORDS	VARCHAR2(2000) DEFAULT NULL,
  c_order		NUMBER(3) DEFAULT 0,
  c_status		NUMBER(1) DEFAULT 0,
  c_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CATEGORY_Cid PRIMARY KEY (c_id)
);

comment on table sys_article_category IS '文章分类表';
comment on column sys_article_category.c_id IS '文章分类id';
comment on column sys_article_category.c_uid IS '文章分类创建者id';
comment on column sys_article_category.C_name IS '文章分类名称';
comment on column sys_article_category.C_GRADE IS '文章分类等级';
comment on column sys_article_category.C_INTRO IS '文章分类简介';
comment on column sys_article_category.C_KEYWORDS IS '文章分类关键字';
comment on column sys_article_category.c_order IS '文章分类排序';
comment on column sys_article_category.c_status IS '文章分类状态（0:禁用|1:可用）';
comment on column sys_article_category.c_time24 IS '文章分类创建时间';

-- Create table
create table sys_article_topic (
  t_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  t_pid 		VARCHAR2(32) DEFAULT NULL,
  t_uid 		VARCHAR2(32) NOT NULL,
  t_name 		VARCHAR2(500) NOT NULL,
  t_cid			VARCHAR2(32) NOT NULL,
  t_remark		CLOB,
  t_order		NUMBER(3) DEFAULT 0,
  t_status		NUMBER(1) DEFAULT 0,
  t_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_TOPIc_tid PRIMARY KEY (t_id)
);

comment on table sys_article_topic IS '文章栏目表';
comment on column sys_article_topic.t_id IS '文章栏目id';
comment on column sys_article_topic.t_pid IS '上级文章栏目id';
comment on column sys_article_topic.t_uid IS '文章栏目创建者id';
comment on column sys_article_topic.t_name IS '文章栏目名称';
comment on column sys_article_topic.t_cid IS '文章分类id';
comment on column sys_article_topic.t_remark IS '文章栏目备注';
comment on column sys_article_topic.t_status IS '文章栏目状态（0:禁用|1:可用）';
comment on column sys_article_topic.t_order IS '文章栏目排序';
comment on column sys_article_topic.t_time24 IS '文章栏目创建时间';

-- Create table
create table sys_article_contents (
  c_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  c_tid 		VARCHAR2(32) NOT NULL,
  c_uid 		VARCHAR2(32) NOT NULL,
  c_cid 		VARCHAR2(32) NOT NULL,
  C_ORGid 		VARCHAR2(32) NOT NULL,
  c_title		VARCHAR2(500) NOT NULL,
  c_digest		VARCHAR2(2000),
  c_content		CLOB,
  c_review		NUMBER(1) DEFAULT 0,
  c_status		NUMBER(1) DEFAULT 1,
  c_order		NUMBER(3) DEFAULT 0,
  c_rcmd		NUMBER(1) DEFAULT 0,
  c_browse		NUMBER(5) DEFAULT 0,
  c_collect		NUMBER(5) DEFAULT 0,
  c_liked		NUMBER(5) DEFAULT 0,
  C_TTYPE		NUMBER(1) DEFAULT 1,
  c_ptime24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  c_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CONTENt_cid PRIMARY KEY (c_id)
);

comment on table sys_article_contents IS '文章消息表';
comment on column sys_article_contents.c_id IS '文章id';
comment on column sys_article_contents.c_tid IS '文章栏目id';
comment on column sys_article_contents.c_uid IS '文章发布者id';
comment on column sys_article_contents.c_cid IS '文章分类id';
comment on column sys_article_contents.C_ORGid IS '文章所属单位id/编号';
comment on column sys_article_contents.c_title IS '文章标题';
comment on column sys_article_contents.c_digest IS '文章摘要';
comment on column sys_article_contents.c_content IS '文章内容';
comment on column sys_article_contents.c_review IS '文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）';
comment on column sys_article_contents.c_status IS '文章状态（0:禁用|1:可用）';
comment on column sys_article_contents.c_order IS '文章排序';
comment on column sys_article_contents.c_rcmd IS '文章是否推荐（0:未推荐|1:推荐）';
comment on column sys_article_contents.c_browse IS '文章浏览数';
comment on column sys_article_contents.c_collect IS '文章收藏数';
comment on column sys_article_contents.c_liked IS '文章点赞数';
comment on column sys_article_contents.C_TTYPE IS '文章发布对象（1：全部，2：指定学院，3：指定个人）';
comment on column sys_article_contents.c_ptime24 IS '文章发布时间';
comment on column sys_article_contents.c_time24 IS '文章创建时间';

-- Create table
create table sys_article_content_targets (
  t_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  t_cid   		VARCHAR2(32)  NOT NULL,
  t_tid 		VARCHAR2(32)  NOT NULL,
  CONSTRAINT PK_ARTICLE_TARGEt_id PRIMARY KEY (t_id)
);

comment on table sys_article_content_targets IS '文章发布对象表';
comment on column sys_article_content_targets.t_id IS '文章发布对象记录id';
comment on column sys_article_content_targets.t_cid IS '文章id';
comment on column sys_article_content_targets.t_tid IS '文章发布对象id（学院id|专业id|班级id|账户id）';

-- Create table
create table sys_article_content_atts (
  a_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  a_cid 		VARCHAR2(32)                    NOT NULL,
  a_type		NUMBER(1) DEFAULT 0,
  a_name		VARCHAR2(500),
  a_path		VARCHAR2(500),
  a_order		NUMBER(3) DEFAULT 0,
  a_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_ATTid PRIMARY KEY (a_id)
);

comment on table sys_article_content_atts IS '文章内容附件表';
comment on column sys_article_content_atts.a_id IS '文章id';
comment on column sys_article_content_atts.a_cid IS '文章附件id';
comment on column sys_article_content_atts.a_type IS '文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）';
comment on column sys_article_content_atts.a_name IS '文章附件名称';
comment on column sys_article_content_atts.a_path IS '文章附件存储路径（相对地址）';
comment on column sys_article_content_atts.a_order IS '文章附件排序';
comment on column sys_article_content_atts.a_time24 IS '文章附件上传时间';

-- Create table
create table sys_article_content_comments (
  c_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  c_cid 		VARCHAR2(32) NOT NULL,
  c_pid 		VARCHAR2(32) DEFAULT NULL,
  c_uid 		VARCHAR2(32) NOT NULL,
  C_TYPE		NUMBER(3) DEFAULT 0,
  C_TEXT		VARCHAR2(2000) NOT NULL,
  c_review		NUMBER(1) DEFAULT 0,
  c_status		NUMBER(1) DEFAULT 1,
  c_rcmd		NUMBER(1) DEFAULT 0,
  c_order		NUMBER(3) DEFAULT 0,
  c_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CMMt_cid PRIMARY KEY (c_id)
);

comment on table sys_article_content_comments IS '文章内容评论表';
comment on column sys_article_content_comments.c_id IS '文章评论id';
comment on column sys_article_content_comments.c_cid IS '文章id';
comment on column sys_article_content_comments.c_pid IS '上级文章评论id';
comment on column sys_article_content_comments.c_uid IS '文章评论者id';
comment on column sys_article_content_comments.C_TYPE IS '文章评论类型';
comment on column sys_article_content_comments.C_TEXT IS '文章评论内容';
comment on column sys_article_content_comments.c_review IS '文章评论审核状态（0:未通过|1:通过）';
comment on column sys_article_content_comments.c_status IS '文章评论状态（0:删除|1:正常）';
comment on column sys_article_content_comments.c_rcmd IS '文章评论推荐（0:未推荐|1:推荐）';
comment on column sys_article_content_comments.c_order IS '文章评论排序';
comment on column sys_article_content_comments.c_time24 IS '文章评论时间';
 
-- Create table
create table sys_article_content_tags (
  t_id   VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  t_cid  VARCHAR2(32) NOT NULL,
  t_name VARCHAR2(20),
  t_time24 	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_TAG_Tid PRIMARY KEY (t_id)
);

comment on table sys_article_content_tags is '文章标签表';
comment on column sys_article_content_tags.t_id is '文章标签id';
comment on column sys_article_content_tags.t_cid is '文章id';
comment on column sys_article_content_tags.t_name is '文章标签名称';
comment on column sys_article_content_tags.t_time24 is '文章标签设置时间';

-- Create table
create table sys_article_messages (
  M_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  M_Cid 		VARCHAR2(32)                    NOT NULL,
  M_Uid 		VARCHAR2(32)                    NOT NULL,
  M_STATUS		NUMBER(1) DEFAULT 0,
  M_MSG			VARCHAR2(2000),
  M_time24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_MSG_Mid PRIMARY KEY (M_id)
);

comment on table sys_article_messages IS '文章消息表';
comment on column sys_article_messages.M_id IS '文章消息id';
comment on column sys_article_messages.M_Cid IS '文章id';
comment on column sys_article_messages.M_Uid IS '文章消息接收者id';
comment on column sys_article_messages.M_STATUS IS '文章消息状态（0:未读|1:已读）';
comment on column sys_article_messages.M_MSG IS '文章消息内容';
comment on column sys_article_messages.M_time24 IS '文章消息发送时间';

-- Create table
create table sys_article_visits (
  v_id   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  v_cid 		VARCHAR2(32)                    NOT NULL,
  v_uid 		VARCHAR2(32)                    NOT NULL,
  v_addr		VARCHAR2(64),
  v_location 	VARCHAR2(200),
  v_agent 		VARCHAR2(500),
  v_time24 		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_VISIT_Vid PRIMARY KEY (v_id)
);

comment on table sys_article_visits IS '文章访问日志表';
comment on column sys_article_visits.v_id IS '文章访问id';
comment on column sys_article_visits.v_cid IS '文章id';
comment on column sys_article_visits.v_uid IS '文章访问者id';
comment on column sys_article_visits.v_addr IS '访问来源IP';
comment on column sys_article_visits.v_location IS '访问来源地址';
comment on column sys_article_visits.v_agent IS '访问来源User-Agent';
comment on column sys_article_visits.v_time24 IS '文章访问时间';
