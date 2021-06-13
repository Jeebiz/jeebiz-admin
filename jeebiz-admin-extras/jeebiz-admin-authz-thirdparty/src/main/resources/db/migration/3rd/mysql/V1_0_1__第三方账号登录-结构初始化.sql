/* 
 * 第三方账号登录表
 */
-- ----------------------------
-- Table structure for sys_authz_thirdparty
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_thirdparty`;
CREATE TABLE `sys_authz_thirdparty` (
  `u_id` bigint(12) NOT NULL COMMENT '用户id',
  `t_id` bigint(12) NOT NULL COMMENT '第三方账号登录表id',
  `t_type` varchar(50) DEFAULT NULL COMMENT '第三方账号类型：（qq：腾讯QQ，wx：微信）',
  `t_unionid` varchar(500) DEFAULT NULL COMMENT '第三方平台Unionid（通常指第三方账号体系下用户的唯一id）',
  `t_openid` varchar(500) DEFAULT NULL COMMENT '第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）',
  `t_devid` varchar(500) DEFAULT NULL COMMENT '当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）',
  `t_rawdata` varchar(2000) DEFAULT NULL COMMENT '第三方认证账号扩展信息',
  `is_delete` tinyint(5) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `creator` bigint(12) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyer` bigint(12) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方账号登录表';
