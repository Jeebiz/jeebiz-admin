
DELETE FROM t_role_list;
DELETE FROM t_user_account;
DELETE FROM t_role_perms WHERE r_id = '8BA8036C5A8B05E1E050007F010072D3';
DELETE FROM t_user_roles WHERE r_id = '8BA8036C5A8B05E1E050007F010072D3';

INSERT INTO t_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '8BA8036C5A8B05E1E050007F010072D3', 'admin','超级管理员', '1', '系统初始化的最高权限用户', '1' );
INSERT INTO t_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '8BA8036C5A8C05E1E050007F010072D3', 'teacher', '教师', '1', '教师角色', '1' );
INSERT INTO t_role_list ( r_id, r_key, r_name, r_type, r_intro, r_status)
VALUES ( '8BA8036C5A8D05E1E050007F010072D3', 'student', '学生', '1', '学生角色', '1' );

INSERT INTO t_role_perms ( r_id, perms)
VALUES ( '8BA8036C5A8B05E1E050007F010072D3', '*');

INSERT INTO t_user_account ( u_id, u_account, u_password, u_salt, u_secret, u_alias, u_avatar, u_phone, u_email, U_REMARK, u_status  )
VALUES ( '8BA7FCC7C934C6EDE050007F01006EF9', 'admin', '$2a$10$7Q074TSzglthSwIeynXEpeXWVQs6tFDVZ/g7gAmjkTCtz5jx6AdC.', 'MTIzNDU2', 'MTIzNDU2', '超级管理员', '','13655896263', 'jeebiz@163.com', '默认最高权限管理员', '1');

INSERT INTO t_user_roles ( u_id, r_id, r_prty)
VALUES ( '8BA7FCC7C934C6EDE050007F01006EF9', '8BA8036C5A8B05E1E050007F010072D3', '0');

COMMIT;
