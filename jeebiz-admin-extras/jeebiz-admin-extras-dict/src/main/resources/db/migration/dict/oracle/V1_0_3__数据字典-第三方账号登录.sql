
-- ----------------------------
-- Records of sys_data_pairgroup
-- ----------------------------

INSERT INTO sys_data_pairgroup ( g_key, g_text, g_status, g_order) VALUES ('DSFDLFS', '第三方登录方式', '1', '15');

-- ----------------------------
-- Records of sys_data_pairvalue
-- ----------------------------

INSERT INTO sys_data_pairvalue ( d_group, d_label, d_key, d_value, d_text, d_status, d_order) VALUES ( 'DSFDLFS', '腾讯QQ', 'qq', 'qq','腾讯QQ授权登录', '1', '24');
INSERT INTO sys_data_pairvalue ( d_group, d_label, d_key, d_value, d_text, d_status, d_order) VALUES ( 'DSFDLFS', '微信', 'wx', 'wx','微信授权登录', '1', '25');


COMMIT;