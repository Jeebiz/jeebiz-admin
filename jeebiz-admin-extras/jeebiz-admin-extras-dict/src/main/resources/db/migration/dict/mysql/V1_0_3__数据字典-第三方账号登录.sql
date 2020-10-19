
-- ----------------------------
-- Records of SYS_DATA_PAIRGROUP
-- ----------------------------

INSERT INTO SYS_DATA_PAIRGROUP ( G_KEY, G_TEXT, G_STATUS, G_ORDER) VALUES ('DSFDLFS', '第三方登录方式', '1', '15');

-- ----------------------------
-- Records of SYS_DATA_PAIRVALUE
-- ----------------------------

INSERT INTO SYS_DATA_PAIRVALUE ( D_GROUP, D_LABEL, D_KEY, D_VALUE, D_TEXT, D_STATUS, D_ORDER) VALUES ( 'DSFDLFS', '腾讯QQ', 'qq', 'qq','腾讯QQ授权登录', '1', '24');
INSERT INTO SYS_DATA_PAIRVALUE ( D_GROUP, D_LABEL, D_KEY, D_VALUE, D_TEXT, D_STATUS, D_ORDER) VALUES ( 'DSFDLFS', '微信', 'wx', 'wx','微信授权登录', '1', '25');


COMMIT;