CREATE TABLE `cnarea_2019` (
  `id` mediumint(7) NOT NULL AUTO_INCREMENT,
  `level` tinyint(1) NOT NULL COMMENT '层级',
  `parent_code` bigint(14) NOT NULL DEFAULT '0' COMMENT '父级行政代码',
  `area_code` bigint(14) NOT NULL DEFAULT '0' COMMENT '行政代码',
  `zip_code` mediumint(6) zerofill NOT NULL DEFAULT '000000' COMMENT '邮政编码',
  `city_code` char(6) NOT NULL DEFAULT '' COMMENT '区号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `short_name` varchar(50) NOT NULL DEFAULT '' COMMENT '简称',
  `merger_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组合名',
  `pinyin` varchar(30) NOT NULL DEFAULT '' COMMENT '拼音',
  `lng` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '经度',
  `lat` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '纬度',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`area_code`) USING BTREE,
  KEY `idx_parent_code` (`parent_code`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='中国行政地区表';