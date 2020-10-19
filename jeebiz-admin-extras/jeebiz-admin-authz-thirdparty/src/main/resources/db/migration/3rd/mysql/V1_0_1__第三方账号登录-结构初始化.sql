/* 
 * 第三方账号登录表
 */

-- ----------------------------
-- Table structure for SYS_AUTHZ_USER_THIRDPARTY
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_USER_THIRDPARTY`;
CREATE TABLE `SYS_AUTHZ_USER_THIRDPARTY` (
  `U_ID` int(11) NOT NULL COMMENT '用户ID',
  `T_ID` int(11) NOT NULL COMMENT '第三方账号登录表ID',
  `T_TYPE` varchar(50) DEFAULT NULL COMMENT '第三方账号类型：（qq：腾讯QQ，wx：微信）',
  `T_UNIONID` varchar(500) DEFAULT NULL COMMENT '第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）',
  `T_OPENID` varchar(500) DEFAULT NULL COMMENT '第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）',
  `T_DEVID` varchar(500) DEFAULT NULL COMMENT '当前认证对接平台开发者账号ID（通常指与第三方平台进行认证对接的开发者账号的唯一ID）',
  `T_RAWDATA` varchar(4000) DEFAULT NULL COMMENT '第三方认证账号扩展信息',
  `T_TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第三方账号绑定时间',
  PRIMARY KEY (`T_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方账号登录表';
