DELETE FROM t_role_list;
DELETE FROM t_role_perms;
DELETE FROM t_user_account;
DELETE FROM t_user_profile;
DELETE FROM t_user_roles;

INSERT INTO t_role_list ( id, `key`, `name`, type, intro, `status`)
VALUES ( '1', 'admin', '超级管理员', '1', '系统初始化的最高权限用户', '1' );
INSERT INTO t_role_list ( id, `key`, `name`, type, intro, `status`)
VALUES ( '2', 'manager', '普通管理员', '1', '普通功能管理员', '1' );
INSERT INTO t_role_list ( id, `key`, `name`, type, intro, `status`)
VALUES ( '3', 'staff', '普通员工', '1', '公司内部员工', '1' );
INSERT INTO t_role_list ( id, `key`, `name`, type, intro, `status`)
VALUES ( '4', 'normal', '注册用户', '1', '系统注册用户', '1' );

INSERT INTO t_role_perms ( id, role_id, role_key, perms)
VALUES ( '1', '1', 'admin', '*');
INSERT INTO t_role_perms ( id, role_id, role_key, perms)
VALUES ( '2', '2', 'manager', '*');
INSERT INTO t_role_perms ( id, role_id, role_key, perms)
VALUES ( '3', '3', 'staff', '*');
INSERT INTO t_role_perms ( id, role_id, role_key, perms)
VALUES ( '4', '4', 'normal', '*');

INSERT INTO t_user_account(id, account, password, type, salt, secret, status, user_id, user_code, app_id, app_channel, app_version, is_online, latest_online)
VALUES (1, 'admin', 'YHLXHSt+CS2gJDU50amvrw==', 'password', 'MTIzNDU2', '', 1, 1, '10000000', '1', 'ASO0001', '1.0.0', NULL, NULL);
INSERT INTO t_user_account(id, account, password, type, salt, secret, status, user_id, user_code, app_id, app_channel, app_version, is_online, latest_online)
VALUES (2, 'guest', 'YHLXHSt+CS2gJDU50amvrw==', 'password', 'MTIzNDU2', '', 2, 4, '10000000', '1', 'ASO0001', '1.0.0', NULL, NULL);

INSERT INTO t_user_profile(id, code, nickname, avatar, region_code, phone, email, birthday, gender, idcard, age, height, weight, language, intro, photos, province, city, area, wgs84_lng, wgs84_lat, degree, app_id, app_channel, app_version)
VALUES (1, '10000000', '超级管理员', '', '86', '13655896263', 'jeebiz@163.com', '1996-10-01', 1, '411526198910010001', 18, '170cm', '70kg', 'zh_CN', '默认最高权限管理员', '[]', '浙江省', '杭州市', '余杭区', 0.0, 0.0, 0, '1', 'ASO0001', '1.0.0');
INSERT INTO t_user_profile(id, code, nickname, avatar, region_code, phone, email, birthday, gender, idcard, age, height, weight, language, intro, photos, province, city, area, wgs84_lng, wgs84_lat, degree, app_id, app_channel, app_version)
VALUES (2, '10000001', '访客', '', '86', '13655896263', 'jeebiz@163.com', '1996-10-01', 1, '411526198910010001', 18, '170cm', '70kg', 'zh_CN', '访客', '[]', '浙江省', '杭州市', '余杭区', 0.0, 0.0, 0, '1', 'ASO0001', '1.0.0');

INSERT INTO t_user_roles ( id, user_id, role_id, priority)
VALUES ( '1', '1', '1', '1');
INSERT INTO t_user_roles ( id, user_id, role_id, priority)
VALUES ( '2', '2', '4', '1');


COMMIT;
