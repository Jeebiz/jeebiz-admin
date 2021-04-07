DELETE FROM SYS_AUTHZ_ROLE_LIST;
DELETE FROM SYS_AUTHZ_USER_LIST;
DELETE FROM SYS_AUTHZ_USER_PROFILE;
DELETE FROM SYS_AUTHZ_ROLE_PERMS;
DELETE FROM SYS_AUTHZ_USER_ROLE_RELATION;

INSERT INTO SYS_AUTHZ_ROLE_LIST ( R_ID, R_KEY, R_NAME, R_TYPE, R_INTRO, R_STATUS)
VALUES ( '1', 'admin', '超级管理员', '1', '系统初始化的最高权限用户', '1' );
INSERT INTO SYS_AUTHZ_ROLE_LIST ( R_ID, R_KEY, R_NAME, R_TYPE, R_INTRO, R_STATUS)
VALUES ( '2', 'manager', '普通管理员', '1', '普通功能管理员', '1' );
INSERT INTO SYS_AUTHZ_ROLE_LIST ( R_ID, R_KEY, R_NAME, R_TYPE, R_INTRO, R_STATUS)
VALUES ( '3', 'staff', '普通员工', '1', '公司内部员工', '1' );
INSERT INTO SYS_AUTHZ_ROLE_LIST ( R_ID, R_KEY, R_NAME, R_TYPE, R_INTRO, R_STATUS)
VALUES ( '4', 'normal', '注册用户', '1', '系统注册用户', '1' );

INSERT INTO SYS_AUTHZ_ROLE_PERMS ( R_ID, PERMS)
VALUES ( '1', '*');
INSERT INTO SYS_AUTHZ_ROLE_PERMS ( R_ID, PERMS)
VALUES ( '4', '*');

INSERT INTO SYS_AUTHZ_USER_LIST(U_ID, U_USERNAME, U_PASSWORD, U_SALT, U_SECRET, U_STATUS, U_UID, U_CODE, U_APP_ID, U_APP_CHANNEL, U_APP_VERSION, U_ONLINE, U_LATEST_ONLINE) 
VALUES (1, 'admin', 'YHLXHSt+CS2gJDU50amvrw==', 'MTIzNDU2', '', 1, '666666', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO SYS_AUTHZ_USER_PROFILE(U_PID, U_ID, U_NICKNAME, U_AVATAR, U_COUNTRY_CODE, U_PHONE, U_EMAIL, U_BIRTHDAY, U_GENDER, U_IDCARD, U_AGE, U_HEIGHT, U_WEIGHT, U_LANGUAGE, U_INTRO, U_PHOTOS, U_PROVINCE, U_CITY, U_AREA, U_WGS84_LNG, U_WGS84_LAT, U_DEGREE) 
VALUES (1, 1, '超级管理员', '', '86', '13735896863', 'jeebiz@163.com', '1989-10-01', 1, '411526198910010001', 18, '170cm', '70kg', 'zh_CN', '默认最高权限管理员', '[]', '浙江省', '杭州市', '余杭区', 0.0, 0.0, 0);

INSERT INTO SYS_AUTHZ_USER_ROLE_RELATION ( U_ID, R_ID, R_PRTY)
VALUES ( '1', '1', '0');

COMMIT;