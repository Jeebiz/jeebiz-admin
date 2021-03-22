
/* 功能菜单  */
DELETE FROM SYS_FEATURE_LIST;
DELETE FROM SYS_FEATURE_OPTS;

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('1', '基础设置', '基础设置', 'sets', '#', '1', 'layui-icon-set', '0', '1', '1');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('2', '基础数据', '基础数据', 'basedata', '/extras/basedata/keyvalue/ui/list', '1', '', '1', '1', '2');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('2', '查询', '', '0', 'basedata:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('2', '增加', '', '0', 'basedata:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('2', '删除', '', '0', 'basedata:delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('2', '修改', '', '0', 'basedata:renew', 4);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('3', '系统设置', '系统设置', 'setting', '#', '1', '#', '1', '1', '3');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('4', '前端设置', '前端设置', 'frontend-setting', '/extras/basedata/settings/ui/frontend', '1', '', '3', '1', '4');

INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('4', '查询', '', '0', 'frontend-setting:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('4', '修改', '', '0', 'frontend-setting:renew', 2);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('5', '后端设置', '后端设置', 'backend-setting', '/extras/basedata/settings/ui/backend', '1', '', '3', '1', '5');

INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('5', '查询', '', '0', 'backend-setting:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('5', '修改', '', '0', 'backend-setting:renew', 2);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('6', '邮件服务', '邮件服务', 'email-setting', '/extras/basedata/settings/ui/email', '1', '', '3', '1', '6');

INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '查询', '', '0', 'email-setting:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '修改', '', '0', 'email-setting:renew', 2);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('10', '我的设置', '我的设置', 'my-sets', '#', '1', '', '1', '1', '10');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('11', '基本资料', '基本资料', 'my-info', '/authz/user/ui/info', '1', '', '10', '1', '11');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '查询', '', '0', 'my-info:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '修改', '', '0', 'my-info:new', 2);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('12', '修改密码', '修改密码', 'my-pwd', '/authz/user/ui/password', '1', '', '10', '1', '12');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查询', '', '0', 'my-pwd:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '修改', '', '0', 'my-pwd:new', 2);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('20', '权限管理', '权限管理', 'perms', '#', '1', 'layui-icon-user', '0', '1', '20');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('21', '角色管理', '角色管理', 'role', '/authz/role/ui/list', '1', '', '20', '1', '21');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '查看', '', '0', 'role:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '增加', '', '0', 'role:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '删除', '', '0', 'role:delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '修改', '', '0', 'role:renew', 4);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('22', '用户管理', '用户管理', 'user', '/authz/user/ui/list', '1', '', '20', '1', '22');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '查看', '', '0', 'user:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '增加', '', '0', 'user:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '删除', '', '0', 'user:delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '修改', '', '0', 'user:renew', 4);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('30', '系统安全', '系统安全', 'system-security', '#', '1', 'layui-icon-auz', '0', '1', '30');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('31', '安全审计', '安全审计', 'security-audit' ,'#', '1', '', '30', '1', '31');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('32', '系统异常', '系统异常', 'biz-excps', '', '1', '', '31', '1', '32');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('32', '查询', '', '0', 'logs:excp-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('33', '认证日志', '认证日志', 'authz-logs', '', '1', '', '31', '1', '33');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('33', '查询', '', '0', 'logs:authz-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('34', '操作日志', '操作日志', 'opt-logs', '', '1', '', '31', '1', '34');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('34', '查询', '', '0', 'logs:opt-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('35', '安全防护', '安全防护', 'security-protection', '#', '1', '', '31', '1', '35');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('36', 'XSS 攻击防护', 'XSS 攻击防护', 'xss-protection', '', '1', '', '35', '1', '36');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('36', '保存', '', '0', 'xss:renew', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('37', 'CSRF 攻击防护', 'CSRF 攻击防护', 'csrf-protection', '', '1', '', '35', '1', '37');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('37', '保存', '', '0', 'csrf:renew', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('38', '会话监控', '会话监控', 'session-monitoring', '', '1', '', '30', '1', '38');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('38', '查询', '', '0', 'session:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('38', '强制退出', '', '0', 'session:kickout', 1);


INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('40', '自定义功能', '自定义功能', 'self-defined', '#', '1', 'layui-icon-component', '0', '1', '40');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('41', '数据源设置', '数据源设置', 'datasource', 'extras/datasource/ui/list', '1', '', '40', '1', '41');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '查看', '', '0', 'datasource:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '增加', '', '0', 'datasource:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '删除', '', '0', 'datasource:delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '修改', '', '0', 'datasource:renew', 4);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '详情', '', '0', 'datasource:detail', 5);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('41', '状态', '', '0', 'datasource:status', 6);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('42', '自定义菜单', '自定义菜单', 'webui-feature', 'extras/webui/ui/feature', '1', '', '40', '1', '42');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('42', '查看', '', '0', 'webui:feature-list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('42', '增加', '', '0', 'webui:feature-new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('42', '删除', '', '0', 'webui:feature-delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('42', '修改', '', '0', 'webui:feature-renew', 4);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('43', '自定义界面', '自定义界面', 'webui', 'extras/webui/ui/list', '1', '', '40', '1', '43');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '查看', '', '0', 'webui:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '增加', '', '0', 'webui:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '删除', '', '0', 'webui:delete', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '修改', '', '0', 'webui:renew', 4);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '详情', '', '0', 'webui:detail', 5);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '状态', '', '0', 'webui:status', 6);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '设计', '', '0', 'webui:design', 7);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('43', '发布', '', '0', 'webui:release', 8);


COMMIT;
