/* 
 * 第三方账号登录表
 */

-- ----------------------------
-- Table structure for sys_authz_user_thirdparty
-- ----------------------------
DROP TABLE IF EXISTS `sys_authz_user_thirdparty`;
CREATE TABLE `sys_authz_user_thirdparty` (
  `u_id` int(11) NOT NULL COMMENT '用户id',
  `t_id` int(11) NOT NULL COMMENT '第三方账号登录表id',
  `t_type` varchar(50) DEFAULT NULL COMMENT '第三方账号类型：（qq：腾讯QQ，wx：微信）',
  `t_unionid` varchar(500) DEFAULT NULL COMMENT '第三方平台Unionid（通常指第三方账号体系下用户的唯一id）',
  `t_openid` varchar(500) DEFAULT NULL COMMENT '第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）',
  `t_devid` varchar(500) DEFAULT NULL COMMENT '当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）',
  `t_rawdata` varchar(4000) DEFAULT NULL COMMENT '第三方认证账号扩展信息',
  `T_time24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第三方账号绑定时间',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方账号登录表';
