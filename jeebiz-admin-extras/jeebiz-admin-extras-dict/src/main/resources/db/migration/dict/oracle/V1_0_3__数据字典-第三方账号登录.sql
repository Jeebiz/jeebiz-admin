
-- ----------------------------
-- Records of SYS_DATA_PAIRGROUP
-- ----------------------------

INSERT INTO SYS_DATA_PAIRGROUP ( G_KEY, G_TEXT, G_STATUS, G_ORDER) VALUES ('DSFDLFS', '第三方登录方式', '1', '15');

-- ----------------------------
-- Records of SYS_DATA_PAIRVALUE
-- ----------------------------

INSERT INTO SYS_DATA_PAIRVALUE ( d_group, d_label, d_key, D_VALUE, D_TEXT, d_status, d_order) VALUES ( 'DSFDLFS', '腾讯QQ', 'qq', 'qq','腾讯QQ授权登录', '1', '24');
INSERT INTO SYS_DATA_PAIRVALUE ( d_group, d_label, d_key, D_VALUE, D_TEXT, d_status, d_order) VALUES ( 'DSFDLFS', '微信', 'wx', 'wx','微信授权登录', '1', '25');


COMMIT;