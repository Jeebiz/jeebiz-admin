/* 
 * 通知公告表结构   
 */

-- ----------------------------
-- Table structure for sys_article_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_category`;
CREATE TABLE `sys_article_category` (
  `c_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章分类id',
  `c_name` varchar(500) NOT NULL COMMENT '文章分类名称',
  `c_grade` int(3) DEFAULT 0 COMMENT '文章分类等级',
  `c_intro` varchar(2000) DEFAULT NULL COMMENT '文章分类简介',
  `c_keywords` varchar(2000) DEFAULT NULL COMMENT '文章分类关键字',
  `c_order` int(3) DEFAULT 0 COMMENT '文章分类排序',
  `c_status` int(1) NOT NULL DEFAULT 1 COMMENT '文章分类状态（0:禁用|1:可用）',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';
 
-- ----------------------------
-- Table structure for sys_article_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_topic`;
CREATE TABLE `sys_article_topic` (
  `t_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章栏目id',
  `t_pid` bigint(12) DEFAULT 0 COMMENT '上级文章栏目id',
  `t_name` varchar(500) NOT NULL COMMENT '文章栏目名称',
  `t_cid` bigint(12) NOT NULL COMMENT '文章分类id',
  `t_remark` text DEFAULT NULL COMMENT '文章栏目备注',
  `t_status` int(1) NOT NULL DEFAULT 1 COMMENT '文章栏目状态（0:禁用|1:可用）',
  `t_order` int(3) DEFAULT 0 COMMENT '文章栏目排序',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章栏目表';
    
-- ----------------------------
-- Table structure for sys_article_contents
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_contents`;
CREATE TABLE `sys_article_contents` (
  `c_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `c_tid` bigint(12) NOT NULL COMMENT '文章栏目id',
  `c_uid` bigint(12) NOT NULL COMMENT '文章发布者id',
  `c_cid` bigint(12) NOT NULL COMMENT '文章分类id',
  `c_orgId` bigint(12) NOT NULL COMMENT '文章所属单位id/编号',
  `c_title` varchar(500) NOT NULL COMMENT '文章标题',
  `c_digest` text NOT NULL COMMENT '文章摘要',
  `c_content` text NOT NULL COMMENT '文章内容',
  `c_review` int(1) DEFAULT 0 COMMENT '文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）',
  `c_status` int(1) DEFAULT 1 COMMENT '文章状态（0:禁用|1:可用）',
  `c_order` int(3) DEFAULT 0 COMMENT '文章排序',
  `c_recommend` int(1) DEFAULT 0 COMMENT '文章是否推荐（0:未推荐|1:推荐）',
  `c_browse` int(5) DEFAULT 0 COMMENT '文章浏览数',
  `c_collect` int(5) DEFAULT 0 COMMENT '文章收藏数',
  `c_liked` int(5) DEFAULT 0 COMMENT '文章点赞数',
  `c_target` int(1) DEFAULT 1 COMMENT '文章发布对象（1：全部，2：指定学院，3：指定个人）',
  `c_ptime24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容表';

-- ----------------------------
-- Table structure for sys_article_content_targets
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_content_targets`;
CREATE TABLE `sys_article_content_targets` (
  `t_cid` bigint(12) NOT NULL COMMENT '文章id',
  `t_tid` bigint(12) NOT NULL COMMENT '文章发布对象id（学院id|专业id|班级id|账户id）',
   UNIQUE KEY (`t_cid`,`t_tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章发布对象表';

-- ----------------------------
-- Table structure for sys_article_content_atts
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_content_atts`;
CREATE TABLE `sys_article_content_atts` (
  `a_cid` bigint(12) NOT NULL COMMENT '文章id',
  `a_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章附件id',
  `a_type` int(1) DEFAULT 0 COMMENT '文章附件类型（1：标题图片，2：内容图片，3：文件普通附件）',
  `a_name` varchar(500) NOT NULL COMMENT '文章附件名称',
  `a_path` varchar(500) NOT NULL COMMENT '文章附件存储路径（相对地址）',
  `a_order` int(3) DEFAULT 0 COMMENT '文章附件排序',
  `a_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章附件上传时间',
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容附件表';

-- ----------------------------
-- Table structure for sys_article_content_comments
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_content_comments`;
CREATE TABLE `sys_article_content_comments` (
  `c_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章评论id',
  `c_cid` bigint(12) NOT NULL COMMENT '文章id',
  `c_pid` bigint(12) DEFAULT NULL COMMENT '上级文章评论id',
  `c_uid` bigint(12) NOT NULL COMMENT '文章评论者id',
  `C_TYPE` int(3) DEFAULT 0 COMMENT '文章评论类型',
  `C_TEXT` varchar(2000) NOT NULL COMMENT '文章评论内容',
  `c_review` int(1) DEFAULT 0 COMMENT '文章评论审核状态（0:未通过|1:通过）',
  `c_status` int(1) DEFAULT 1 COMMENT '文章评论状态（0:删除|1:正常）',
  `c_recommend` int(1) DEFAULT 0 COMMENT '文章评论推荐（0:未推荐|1:推荐）',
  `c_order` int(3) DEFAULT 0 COMMENT '文章评论排序',
  `c_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章评论时间',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容评论表';

-- ----------------------------
-- Table structure for sys_article_content_tags
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_content_tags`;
CREATE TABLE `sys_article_content_tags` (
  `t_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章标签id',
  `t_cid` bigint(12) NOT NULL COMMENT '文章id',
  `t_name` varchar(100) NOT NULL COMMENT '文章标签名称',
  `t_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章标签设置时间',
	PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签表';
  
-- ----------------------------
-- Table structure for sys_article_messages
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_messages`;
CREATE TABLE `sys_article_messages` (
  `M_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章消息id',
  `M_Cid` bigint(12) NOT NULL COMMENT '文章id',
  `M_Uid` bigint(12) NOT NULL COMMENT '文章消息接收者id',
  `M_MSG` text NOT NULL COMMENT '文章消息内容',
  `M_STATUS` int(1) DEFAULT 0 COMMENT '文章消息状态（0:未读|1:已读）',
  `M_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章消息发送时间',
	PRIMARY KEY (`M_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章消息表';

-- ----------------------------
-- Table structure for sys_article_visits
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_visits`;
CREATE TABLE `sys_article_visits` (
  `v_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '文章访问id',
  `v_cid` bigint(12) NOT NULL COMMENT '文章id',
  `v_uid` bigint(12) NOT NULL COMMENT '文章访问者id',
  `V_TYPE` int(1) DEFAULT 0 COMMENT '文章访问类型（0:点击|1:收藏|2:点赞）',
  `v_addr` varchar(64) NOT NULL COMMENT '文章访问来源IP',
  `v_location` varchar(200) NOT NULL COMMENT '文章访问来源地址',
  `v_agent` varchar(500) NOT NULL COMMENT '文章访问来源User-Agent',
  `v_time24` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '文章访问时间',
	PRIMARY KEY (`v_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章访问日志表';
