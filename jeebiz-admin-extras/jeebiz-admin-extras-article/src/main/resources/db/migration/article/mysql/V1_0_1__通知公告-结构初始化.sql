/* 
 * 通知公告表结构   
 */

-- ----------------------------
-- Table structure for SYS_ARTICLE_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CATEGORY`;
CREATE TABLE `SYS_ARTICLE_CATEGORY` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章分类ID',
  `C_UID` int(11) NOT NULL COMMENT '文章分类创建者ID',
  `C_NAME` varchar(500) NOT NULL COMMENT '文章分类名称',
  `C_GRADE` int(3) DEFAULT 0 COMMENT '文章分类等级',
  `C_INTRO` varchar(2000) DEFAULT NULL COMMENT '文章分类简介',
  `C_KEYWORDS` varchar(2000) DEFAULT NULL COMMENT '文章分类关键字',
  `C_ORDER` int(3) DEFAULT 0 COMMENT '文章分类排序',
  `C_STATUS` int(1) NOT NULL DEFAULT 1 COMMENT '文章分类状态（0:禁用|1:可用）',
  `C_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章分类创建时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';
 
-- ----------------------------
-- Table structure for SYS_ARTICLE_TOPIC
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_TOPIC`;
CREATE TABLE `SYS_ARTICLE_TOPIC` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章栏目ID',
  `T_PID` int(11) DEFAULT 0 COMMENT '上级文章栏目ID',
  `T_UID` int(11) NOT NULL COMMENT '文章栏目创建者ID',
  `T_NAME` varchar(500) NOT NULL COMMENT '文章栏目名称',
  `T_CID` int(11) NOT NULL COMMENT '文章分类ID',
  `T_REMARK` text DEFAULT NULL COMMENT '文章栏目备注',
  `T_STATUS` int(1) NOT NULL DEFAULT 1 COMMENT '文章栏目状态（0:禁用|1:可用）',
  `T_ORDER` int(3) DEFAULT 0 COMMENT '文章栏目排序',
  `T_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章栏目创建时间',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章栏目表';
    
-- ----------------------------
-- Table structure for SYS_ARTICLE_CONTENTS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CONTENTS`;
CREATE TABLE `SYS_ARTICLE_CONTENTS` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `C_TID` int(11) NOT NULL COMMENT '文章栏目ID',
  `C_UID` int(11) NOT NULL COMMENT '文章发布者ID',
  `C_CID` int(11) NOT NULL COMMENT '文章分类ID',
  `C_ORGID` int(11) NOT NULL COMMENT '文章所属单位ID/编号',
  `C_TITLE` varchar(500) NOT NULL COMMENT '文章标题',
  `C_DIGEST` text NOT NULL COMMENT '文章摘要',
  `C_CONTENT` text NOT NULL COMMENT '文章内容',
  `C_REVIEW` int(1) DEFAULT 0 COMMENT '文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）',
  `C_STATUS` int(1) DEFAULT 1 COMMENT '文章状态（0:禁用|1:可用）',
  `C_ORDER` int(3) DEFAULT 0 COMMENT '文章排序',
  `C_RCMD` int(1) DEFAULT 0 COMMENT '文章是否推荐（0:未推荐|1:推荐）',
  `C_BROWSE` int(5) DEFAULT 0 COMMENT '文章浏览数',
  `C_COLLECT` int(5) DEFAULT 0 COMMENT '文章收藏数',
  `C_LIKED` int(5) DEFAULT 0 COMMENT '文章点赞数',
  `C_TTYPE` int(1) DEFAULT 1 COMMENT '文章发布对象（1：全部，2：指定学院，3：指定个人）',
  `C_Ptime24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
  `C_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章创建时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容表';

-- ----------------------------
-- Table structure for SYS_ARTICLE_CONTENT_TARGETS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CONTENT_TARGETS`;
CREATE TABLE `SYS_ARTICLE_CONTENT_TARGETS` (
  `T_CID` int(11) NOT NULL COMMENT '文章ID',
  `T_TID` int(11) NOT NULL COMMENT '文章发布对象ID（学院ID|专业ID|班级ID|账户ID）',
   UNIQUE KEY (`T_CID`,`T_TID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章发布对象表';

-- ----------------------------
-- Table structure for SYS_ARTICLE_CONTENT_ATTS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CONTENT_ATTS`;
CREATE TABLE `SYS_ARTICLE_CONTENT_ATTS` (
  `A_CID` int(11) NOT NULL COMMENT '文章ID',
  `A_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章附件ID',
  `A_TYPE` int(1) DEFAULT 0 COMMENT '文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）',
  `A_NAME` varchar(500) NOT NULL COMMENT '文章附件名称',
  `A_PATH` varchar(500) NOT NULL COMMENT '文章附件存储路径（相对地址）',
  `A_ORDER` int(3) DEFAULT 0 COMMENT '文章附件排序',
  `A_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章附件上传时间',
  PRIMARY KEY (`A_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容附件表';

-- ----------------------------
-- Table structure for SYS_ARTICLE_CONTENT_COMMENTS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CONTENT_COMMENTS`;
CREATE TABLE `SYS_ARTICLE_CONTENT_COMMENTS` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章评论ID',
  `C_CID` int(11) NOT NULL COMMENT '文章ID',
  `C_PID` int(11) DEFAULT NULL COMMENT '上级文章评论ID',
  `C_UID` int(11) NOT NULL COMMENT '文章评论者ID',
  `C_TYPE` int(3) DEFAULT 0 COMMENT '文章评论类型',
  `C_TEXT` varchar(2000) NOT NULL COMMENT '文章评论内容',
  `C_REVIEW` int(1) DEFAULT 0 COMMENT '文章评论审核状态（0:未通过|1:通过）',
  `C_STATUS` int(1) DEFAULT 1 COMMENT '文章评论状态（0:删除|1:正常）',
  `C_RCMD` int(1) DEFAULT 0 COMMENT '文章评论推荐（0:未推荐|1:推荐）',
  `C_ORDER` int(3) DEFAULT 0 COMMENT '文章评论排序',
  `C_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章评论时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容评论表';

-- ----------------------------
-- Table structure for SYS_ARTICLE_CONTENT_TAGS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_CONTENT_TAGS`;
CREATE TABLE `SYS_ARTICLE_CONTENT_TAGS` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章标签ID',
  `T_CID` int(11) NOT NULL COMMENT '文章ID',
  `T_NAME` varchar(100) NOT NULL COMMENT '文章标签名称',
  `T_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章标签设置时间',
	PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签表';
  
-- ----------------------------
-- Table structure for SYS_ARTICLE_MESSAGES
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_MESSAGES`;
CREATE TABLE `SYS_ARTICLE_MESSAGES` (
  `M_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章消息ID',
  `M_CID` int(11) NOT NULL COMMENT '文章ID',
  `M_UID` int(11) NOT NULL COMMENT '文章消息接收者ID',
  `M_MSG` text NOT NULL COMMENT '文章消息内容',
  `M_STATUS` int(1) DEFAULT 0 COMMENT '文章消息状态（0:未读|1:已读）',
  `M_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章消息发送时间',
	PRIMARY KEY (`M_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章消息表';

-- ----------------------------
-- Table structure for SYS_ARTICLE_VISITS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ARTICLE_VISITS`;
CREATE TABLE `SYS_ARTICLE_VISITS` (
  `V_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章访问ID',
  `V_CID` int(11) NOT NULL COMMENT '文章ID',
  `V_UID` int(11) NOT NULL COMMENT '文章访问者ID',
  `V_TYPE` int(1) DEFAULT 0 COMMENT '文章访问类型（0:点击|1:收藏|2:点赞）',
  `V_ADDR` varchar(64) NOT NULL COMMENT '文章访问来源IP',
  `V_LOCATION` varchar(200) NOT NULL COMMENT '文章访问来源地址',
  `V_AGENT` varchar(500) NOT NULL COMMENT '文章访问来源User-Agent',
  `V_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章访问时间',
	PRIMARY KEY (`V_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章访问日志表';
