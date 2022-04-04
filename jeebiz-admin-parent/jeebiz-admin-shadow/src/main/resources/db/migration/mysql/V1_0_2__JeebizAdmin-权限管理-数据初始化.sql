DELETE FROM sys_authz_role_list;
DELETE FROM sys_authz_user_list;
DELETE FROM sys_authz_user_profile;
DELETE FROM sys_authz_role_perms;
DELETE FROM sys_authz_user_role_relation;

INSERT INTO sys_authz_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '1', 'admin', '超级管理员', '1', '系统初始化的最高权限用户', '1' );
INSERT INTO sys_authz_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '2', 'manager', '普通管理员', '1', '普通功能管理员', '1' );
INSERT INTO sys_authz_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '3', 'staff', '普通员工', '1', '公司内部员工', '1' );
INSERT INTO sys_authz_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '4', 'normal', '注册用户', '1', '系统注册用户', '1' );

INSERT INTO sys_authz_role_perms ( r_id, perms)
VALUES ( '1', '*');
INSERT INTO sys_authz_role_perms ( r_id, perms)
VALUES ( '4', '*');

INSERT INTO sys_authz_user_list(u_id, u_username, u_password, u_salt, u_secret, u_status, u_uid, u_code, u_app_id, u_app_channel, u_app_version, u_online, u_latest_online)
VALUES (1, 'admin', 'YHLXHSt+CS2gJDU50amvrw==', 'MTIzNDU2', '', 1, '666666', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO sys_authz_user_profile(u_pid, u_id, u_nickname, u_avatar, u_region_code, u_phone, u_email, u_birthday, u_gender, u_idcard, u_age, u_height, u_weight, u_language, u_intro, u_photos, u_province, u_city, u_area, u_wgs84_lng, u_wgs84_lat, u_degree)
VALUES (1, 1, '超级管理员', '', '86', '13735896863', 'jeebiz@163.com', '1989-10-01', 1, '411526198910010001', 18, '170cm', '70kg', 'zh_CN', '默认最高权限管理员', '[]', '浙江省', '杭州市', '余杭区', 0.0, 0.0, 0);

INSERT INTO sys_authz_user_role_relation ( u_id, r_id, r_prty)
VALUES ( '1', '1', '0');

COMMIT;
