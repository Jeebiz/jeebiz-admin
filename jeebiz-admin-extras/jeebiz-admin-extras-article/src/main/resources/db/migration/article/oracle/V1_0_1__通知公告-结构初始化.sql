/*==============================================================*/
/* 通知公告表结构                                      			*/
/*==============================================================*/

-- Create table
create table SYS_ARTICLE_CATEGORY (
  C_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  C_UID 		VARCHAR2(32) NOT NULL,
  C_NAME 		VARCHAR2(500) NOT NULL,
  C_GRADE		NUMBER(3) DEFAULT 0,
  C_INTRO		VARCHAR2(2000) DEFAULT NULL,
  C_KEYWORDS	VARCHAR2(2000) DEFAULT NULL,
  C_ORDER		NUMBER(3) DEFAULT 0,
  C_STATUS		NUMBER(1) DEFAULT 0,
  C_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CATEGORY_CID PRIMARY KEY (C_ID)
);

comment on table SYS_ARTICLE_CATEGORY IS '文章分类表';
comment on column SYS_ARTICLE_CATEGORY.C_ID IS '文章分类ID';
comment on column SYS_ARTICLE_CATEGORY.C_UID IS '文章分类创建者ID';
comment on column SYS_ARTICLE_CATEGORY.C_NAME IS '文章分类名称';
comment on column SYS_ARTICLE_CATEGORY.C_GRADE IS '文章分类等级';
comment on column SYS_ARTICLE_CATEGORY.C_INTRO IS '文章分类简介';
comment on column SYS_ARTICLE_CATEGORY.C_KEYWORDS IS '文章分类关键字';
comment on column SYS_ARTICLE_CATEGORY.C_ORDER IS '文章分类排序';
comment on column SYS_ARTICLE_CATEGORY.C_STATUS IS '文章分类状态（0:禁用|1:可用）';
comment on column SYS_ARTICLE_CATEGORY.C_TIME24 IS '文章分类创建时间'; 

-- Create table
create table SYS_ARTICLE_TOPIC (
  T_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  T_PID 		VARCHAR2(32) DEFAULT NULL,
  T_UID 		VARCHAR2(32) NOT NULL,
  T_NAME 		VARCHAR2(500) NOT NULL,
  T_CID			VARCHAR2(32) NOT NULL,
  T_REMARK		CLOB,
  T_ORDER		NUMBER(3) DEFAULT 0,
  T_STATUS		NUMBER(1) DEFAULT 0,
  T_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_TOPIC_TID PRIMARY KEY (T_ID)
);

comment on table SYS_ARTICLE_TOPIC IS '文章栏目表';
comment on column SYS_ARTICLE_TOPIC.T_ID IS '文章栏目ID';
comment on column SYS_ARTICLE_TOPIC.T_PID IS '上级文章栏目ID';
comment on column SYS_ARTICLE_TOPIC.T_UID IS '文章栏目创建者ID';
comment on column SYS_ARTICLE_TOPIC.T_NAME IS '文章栏目名称';
comment on column SYS_ARTICLE_TOPIC.T_CID IS '文章分类ID';
comment on column SYS_ARTICLE_TOPIC.T_REMARK IS '文章栏目备注';
comment on column SYS_ARTICLE_TOPIC.T_STATUS IS '文章栏目状态（0:禁用|1:可用）';
comment on column SYS_ARTICLE_TOPIC.T_ORDER IS '文章栏目排序';
comment on column SYS_ARTICLE_TOPIC.T_TIME24 IS '文章栏目创建时间'; 

-- Create table
create table SYS_ARTICLE_CONTENTS (
  C_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  C_TID 		VARCHAR2(32) NOT NULL,
  C_UID 		VARCHAR2(32) NOT NULL,
  C_CID 		VARCHAR2(32) NOT NULL,
  C_ORGID 		VARCHAR2(32) NOT NULL,
  C_TITLE		VARCHAR2(500) NOT NULL,
  C_DIGEST		VARCHAR2(2000),
  C_CONTENT		CLOB,
  C_REVIEW		NUMBER(1) DEFAULT 0,
  C_STATUS		NUMBER(1) DEFAULT 1,
  C_ORDER		NUMBER(3) DEFAULT 0,
  C_RCMD		NUMBER(1) DEFAULT 0,
  C_BROWSE		NUMBER(5) DEFAULT 0,
  C_COLLECT		NUMBER(5) DEFAULT 0,
  C_LIKED		NUMBER(5) DEFAULT 0,   
  C_TTYPE		NUMBER(1) DEFAULT 1,
  C_PTIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  C_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CONTENT_CID PRIMARY KEY (C_ID)
);

comment on table SYS_ARTICLE_CONTENTS IS '文章消息表';
comment on column SYS_ARTICLE_CONTENTS.C_ID IS '文章ID';
comment on column SYS_ARTICLE_CONTENTS.C_TID IS '文章栏目ID';
comment on column SYS_ARTICLE_CONTENTS.C_UID IS '文章发布者ID';
comment on column SYS_ARTICLE_CONTENTS.C_CID IS '文章分类ID';
comment on column SYS_ARTICLE_CONTENTS.C_ORGID IS '文章所属单位ID/编号';
comment on column SYS_ARTICLE_CONTENTS.C_TITLE IS '文章标题';
comment on column SYS_ARTICLE_CONTENTS.C_DIGEST IS '文章摘要';
comment on column SYS_ARTICLE_CONTENTS.C_CONTENT IS '文章内容';
comment on column SYS_ARTICLE_CONTENTS.C_REVIEW IS '文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）';
comment on column SYS_ARTICLE_CONTENTS.C_STATUS IS '文章状态（0:禁用|1:可用）';
comment on column SYS_ARTICLE_CONTENTS.C_ORDER IS '文章排序';
comment on column SYS_ARTICLE_CONTENTS.C_RCMD IS '文章是否推荐（0:未推荐|1:推荐）';
comment on column SYS_ARTICLE_CONTENTS.C_BROWSE IS '文章浏览数';
comment on column SYS_ARTICLE_CONTENTS.C_COLLECT IS '文章收藏数';
comment on column SYS_ARTICLE_CONTENTS.C_LIKED IS '文章点赞数';
comment on column SYS_ARTICLE_CONTENTS.C_TTYPE IS '文章发布对象（1：全部，2：指定学院，3：指定个人）';
comment on column SYS_ARTICLE_CONTENTS.C_PTIME24 IS '文章发布时间';
comment on column SYS_ARTICLE_CONTENTS.C_TIME24 IS '文章创建时间'; 

-- Create table
create table SYS_ARTICLE_CONTENT_TARGETS (
  T_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  T_CID   		VARCHAR2(32)  NOT NULL,
  T_TID 		VARCHAR2(32)  NOT NULL,
  CONSTRAINT PK_ARTICLE_TARGET_ID PRIMARY KEY (T_ID)
);

comment on table SYS_ARTICLE_CONTENT_TARGETS IS '文章发布对象表';
comment on column SYS_ARTICLE_CONTENT_TARGETS.T_ID IS '文章发布对象记录ID';
comment on column SYS_ARTICLE_CONTENT_TARGETS.T_CID IS '文章ID';
comment on column SYS_ARTICLE_CONTENT_TARGETS.T_TID IS '文章发布对象ID（学院ID|专业ID|班级ID|账户ID）';

-- Create table
create table SYS_ARTICLE_CONTENT_ATTS (
  A_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  A_CID 		VARCHAR2(32)                    NOT NULL,
  A_TYPE		NUMBER(1) DEFAULT 0,
  A_NAME		VARCHAR2(500),
  A_PATH		VARCHAR2(500),
  A_ORDER		NUMBER(3) DEFAULT 0,
  A_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_ATTID PRIMARY KEY (A_ID)
);

comment on table SYS_ARTICLE_CONTENT_ATTS IS '文章内容附件表';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_ID IS '文章ID';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_CID IS '文章附件ID';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_TYPE IS '文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_NAME IS '文章附件名称';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_PATH IS '文章附件存储路径（相对地址）';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_ORDER IS '文章附件排序';
comment on column SYS_ARTICLE_CONTENT_ATTS.A_TIME24 IS '文章附件上传时间'; 

-- Create table
create table SYS_ARTICLE_CONTENT_COMMENTS (
  C_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  C_CID 		VARCHAR2(32) NOT NULL,
  C_PID 		VARCHAR2(32) DEFAULT NULL,
  C_UID 		VARCHAR2(32) NOT NULL,
  C_TYPE		NUMBER(3) DEFAULT 0,
  C_TEXT		VARCHAR2(2000) NOT NULL,
  C_REVIEW		NUMBER(1) DEFAULT 0,
  C_STATUS		NUMBER(1) DEFAULT 1,
  C_RCMD		NUMBER(1) DEFAULT 0,
  C_ORDER		NUMBER(3) DEFAULT 0,
  C_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_CMMT_CID PRIMARY KEY (C_ID)
);

comment on table SYS_ARTICLE_CONTENT_COMMENTS IS '文章内容评论表';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_ID IS '文章评论ID';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_CID IS '文章ID';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_PID IS '上级文章评论ID';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_UID IS '文章评论者ID';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_TYPE IS '文章评论类型';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_TEXT IS '文章评论内容';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_REVIEW IS '文章评论审核状态（0:未通过|1:通过）';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_STATUS IS '文章评论状态（0:删除|1:正常）';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_RCMD IS '文章评论推荐（0:未推荐|1:推荐）';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_ORDER IS '文章评论排序';
comment on column SYS_ARTICLE_CONTENT_COMMENTS.C_TIME24 IS '文章评论时间'; 
 
-- Create table
create table SYS_ARTICLE_CONTENT_TAGS (
  T_ID   VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  T_CID  VARCHAR2(32) NOT NULL,
  T_NAME VARCHAR2(20),
  T_TIME24 	VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_TAG_TID PRIMARY KEY (T_ID)
);

comment on table SYS_ARTICLE_CONTENT_TAGS is '文章标签表';
comment on column SYS_ARTICLE_CONTENT_TAGS.T_ID is '文章标签ID';
comment on column SYS_ARTICLE_CONTENT_TAGS.T_CID is '文章ID';
comment on column SYS_ARTICLE_CONTENT_TAGS.T_NAME is '文章标签名称';
comment on column SYS_ARTICLE_CONTENT_TAGS.T_TIME24 is '文章标签设置时间';

-- Create table
create table SYS_ARTICLE_MESSAGES (
  M_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  M_CID 		VARCHAR2(32)                    NOT NULL,
  M_UID 		VARCHAR2(32)                    NOT NULL,
  M_STATUS		NUMBER(1) DEFAULT 0,
  M_MSG			VARCHAR2(2000),
  M_TIME24		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_MSG_MID PRIMARY KEY (M_ID)
);

comment on table SYS_ARTICLE_MESSAGES IS '文章消息表';
comment on column SYS_ARTICLE_MESSAGES.M_ID IS '文章消息ID';
comment on column SYS_ARTICLE_MESSAGES.M_CID IS '文章ID';
comment on column SYS_ARTICLE_MESSAGES.M_UID IS '文章消息接收者ID';
comment on column SYS_ARTICLE_MESSAGES.M_STATUS IS '文章消息状态（0:未读|1:已读）';
comment on column SYS_ARTICLE_MESSAGES.M_MSG IS '文章消息内容';
comment on column SYS_ARTICLE_MESSAGES.M_TIME24 IS '文章消息发送时间';

-- Create table
create table SYS_ARTICLE_VISITS (
  V_ID   		VARCHAR2(32) DEFAULT sys_guid() NOT NULL,
  V_CID 		VARCHAR2(32)                    NOT NULL,
  V_UID 		VARCHAR2(32)                    NOT NULL,
  V_ADDR		VARCHAR2(64),
  V_LOCATION 	VARCHAR2(200),
  V_AGENT 		VARCHAR2(500),
  V_TIME24 		VARCHAR2(32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
  CONSTRAINT PK_ARTICLE_VISIT_VID PRIMARY KEY (V_ID)
);

comment on table SYS_ARTICLE_VISITS IS '文章访问日志表';
comment on column SYS_ARTICLE_VISITS.V_ID IS '文章访问ID';
comment on column SYS_ARTICLE_VISITS.V_CID IS '文章ID';
comment on column SYS_ARTICLE_VISITS.V_UID IS '文章访问者ID';
comment on column SYS_ARTICLE_VISITS.V_ADDR IS '访问来源IP';
comment on column SYS_ARTICLE_VISITS.V_LOCATION IS '访问来源地址';
comment on column SYS_ARTICLE_VISITS.V_AGENT IS '访问来源User-Agent';
comment on column SYS_ARTICLE_VISITS.V_TIME24 IS '文章访问时间';
