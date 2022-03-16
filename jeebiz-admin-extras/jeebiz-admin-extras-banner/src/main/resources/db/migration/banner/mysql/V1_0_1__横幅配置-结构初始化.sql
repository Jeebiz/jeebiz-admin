
-- ----------------------------
-- Table structure for 横幅广告配置表
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_banner`;
CREATE TABLE `sys_data_banner` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(50) DEFAULT NULL COMMENT '客户端应用ID；多个客户端使用,拼接',
  `app_channel` varchar(200) DEFAULT NULL COMMENT '客户端应用渠道编码；多个编码使用,拼接',
  `region_code` varchar(300) DEFAULT NULL COMMENT '地区编码；多个编码使用,拼接',
  `language` varchar(30) DEFAULT NULL COMMENT '语区标签；多个语区使用,拼接',
  `title` varchar(50) DEFAULT NULL COMMENT '该横幅对应的标题',
  `desc` text DEFAULT NULL COMMENT '该横幅对应的描述',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '横幅图标路径',
  `img_url` varchar(255) DEFAULT NULL COMMENT '横幅图片路径',
  `jump_url` varchar(255) DEFAULT NULL COMMENT '跳转路径',
  `type` tinyint(4) DEFAULT NULL COMMENT '横幅类型（1：首页轮播图、2：我的页面轮播图、3：搜索页面轮播图）',
  `status` tinyint(4) DEFAULT NULL COMMENT '显示状态（0：不显示、1：显示）',
  `link_type` tinyint(3) DEFAULT NULL COMMENT '链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）',
  `extend` varchar(255) DEFAULT NULL COMMENT '扩展字段：过期时间、等待时间',
  `priority` int(5) DEFAULT '999' COMMENT '权重值，数字越小越靠前',
  `open_time` timestamp NOT NULL COMMENT '开放时间',
  `close_time` timestamp NOT NULL COMMENT '关闭时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='横幅广告配置表';
