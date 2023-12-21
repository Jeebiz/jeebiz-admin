
/* 功能菜单  */
DELETE FROM t_feature_list;
DELETE FROM t_feature_opts;


INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (1, '我的主页', '我的主页', 'home', 'home/homepage2', 'home/homepage2', 1, 'layui-icon-home', 0, 1, 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (10, '通知公告', '通知公告', 'articles', '#', '#', 1, 'layui-icon-notice', 0, 1, 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (11, '公告分类', '公告分类', 'article-category', 'article/category/list', 'rticle/category/list', 1, 'layui-icon-notice', 10, 1, 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (11, '查询', '', '0', 'article-category:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (11, '增加', '', '0', 'article-category:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (11, '删除', '', '0', 'article-category:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (11, '修改', '', '0', 'article-category:renew', 4);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (11, '状态', '', '0', 'article-category:status', 5);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (12, '公告主题', '公告主题', 'article-topic', 'article/topic/list', 'article/topic/list', 1, 'layui-icon-notice', 10, 1, 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (12, '查询', '', '0', 'article-topic:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (12, '增加', '', '0', 'article-topic:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (12, '删除', '', '0', 'article-topic:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (12, '修改', '', '0', 'article-topic:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (13, '公告列表', '公告列表', 'article-list', 'article/list', 'article/list', 1, 'layui-icon-notice', 10, 1, 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '查询', '', '0', 'article:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '增加', '', '0', 'article:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '删除', '', '0', 'article:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '修改', '', '0', 'article:renew', 4);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '审核', '', '0', 'article:review', 5);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (13, '推荐', '', '0', 'article:recommend', 6);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (40, '消息管理', '消息管理', 'inform', '#', '#', 1, 'layui-icon-chat', 0, 1, 40);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (41, '消息模板', '消息模板', 'inform-template', 'inform/template', 'inform/template', 1, '', 40, 1, 41);

INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (41, '查看', '', '0', 'inform-tmpl:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (41, '增加', '', '0', 'inform-tmpl:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (41, '删除', '', '0', 'inform-tmpl:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (41, '修改', '', '0', 'inform-tmpl:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (42, '消息推送', '消息推送', 'inform-push', 'inform/push', 'inform/push', 1, '', 40, 1, 42);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (42, '查看', '', '0', 'inform-tmpl:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (42, '增加', '', '0', 'inform-tmpl:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (42, '删除', '', '0', 'inform-tmpl:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (42, '修改', '', '0', 'inform-tmpl:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (50, '基础设置', '基础设置', 'sets', '#', '#', 1, 'layui-icon-set', 0, 1, 50);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (51, '基础数据', '基础数据', 'dict', '/dict/keyvalue/list', '/dict/keyvalue/list', 1, '', 50, 1, 51);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (51, '查询', '', '0', 'dict:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (51, '增加', '', '0', 'dict:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (51, '删除', '', '0', 'dict:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (51, '修改', '', '0', 'dict:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (52, '系统设置', '系统设置', 'setting', '#', '#', 1, '#', 50, 1, 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (53, '前端设置', '前端设置', 'frontend-setting', '/basedata/settings/frontend', '/basedata/settings/frontend', 1, '', 52, 1, 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (53, '查询', '', '0', 'frontend-setting:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (53, '修改', '', '0', 'frontend-setting:renew', 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (54, '前端设置', '前端设置', 'frontend-setting', '/basedata/settings/frontend', '/basedata/settings/frontend', 1, '', 52, 1, 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (54, '查询', '', '0', 'backend-setting:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (54, '修改', '', '0', 'backend-setting:renew', 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (55, '邮件服务', '邮件服务', 'email-setting', '/basedata/settings/email', '/basedata/settings/email', 1, '', 52, 1, 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (55, '查询', '', '0', 'email-setting:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (55, '修改', '', '0', 'email-setting:renew', 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (58, '上传设置', '上传设置', 'upload-set', 'upload/setting', 'upload/setting', 1, 'layui-icon-rmb', 52, 1, 57);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (58, '查看', '', '0', 'upload:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (58, '修改', '', '0', 'upload:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (65, '我的设置', '我的设置', 'my-sets', '#', '#', 1, '', 50, 1, 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (66, '基本资料', '基本资料', 'my-info', '/authz/rbac0/user/info', '/authz/rbac0/user/info', 1, '', 65, 1, 11);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (66, '查询', '', '0', 'my-info:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (66, '修改', '', '0', 'my-info:new', 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (67, '修改密码', '修改密码', 'my-pwd', '/authz/rbac0/user/password', '/authz/rbac0/user/password', 1, '', 65, 1, 12);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (67, '查询', '', '0', 'my-pwd:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (67, '修改', '', '0', 'my-pwd:new', 2);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (70, '权限管理', '权限管理', 'perms', '#', '#', 1, 'layui-icon-user', 0, 1, 70);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (71, '角色管理', '角色管理', 'role', '/authz/rbac0/role/', '/authz/rbac0/role/', 1, '', 70, 1, 71);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (71, '查看', '', '0', 'role:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (71, '增加', '', '0', 'role:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (71, '删除', '', '0', 'role:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (71, '修改', '', '0', 'role:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (72, '用户管理', '用户管理', 'user', '/authz/rbac0/user/', '/authz/rbac0/user/', 1, '', 70, 1, 72);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (72, '查看', '', '0', 'user:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (72, '增加', '', '0', 'user:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (72, '删除', '', '0', 'user:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (72, '修改', '', '0', 'user:renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (80, '系统安全', '系统安全', 'system-security', '#', '#', 1, 'layui-icon-auz', 0, 1, 80);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (81, '安全审计', '安全审计', 'security-audit', '#', '#', 1, '', 80, 1, 81);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (82, '认证日志', '认证日志', 'authz-logs', '/authz/rbac0/user/list', '/authz/rbac0/user/list', 1, '', 81, 1, 82);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (82, '查询', '', '0', 'logs:authz-list', 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (83, '操作日志', '操作日志', 'opt-logs', '/authz/rbac0/user/list', '/authz/rbac0/user/list', 1, '', 81, 1, 83);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (83, '查询', '', '0', 'logs:opt-list', 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (85, '安全防护', '安全防护', 'security-protection', '#', '#', 1, '', 80, 1, 85);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (86, 'XSS 攻击防护', 'XSS 攻击防护', 'xss-protection', '/authz/rbac0/user/list', '/authz/rbac0/user/list', 1, '', 85, 1, 86);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (86, '保存', '', '0', 'xss:renew', 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (87, 'CSRF 攻击防护', 'CSRF 攻击防护', 'csrf-protection', '/authz/rbac0/user/list', '/authz/rbac0/user/list', 1, '', 85, 1, 87);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (87, '保存', '', '0', 'csrf:renew', 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (90, '系统监控', '系统监控', 'system-monitor', '#', '#', 1, 'layui-icon-auz', 80, 1, 90);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (91, '会话监控', '会话监控', 'session-monitoring', '/authz/rbac0/user/list', '/authz/rbac0/user/list', 1, '', 90, 1, 91);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (91, '查询', '', '0', 'session:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (91, '强制退出', '', '0', 'session:kickout', 1);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (92, '数据监控', '数据监控', 'druid', 'iframe/link/druid', 'iframe/link/druid', 1, '', 90, 1, 92);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (93, '服务监控', '服务监控', 'druid', 'system/monitor', 'system/monitor', 1, '', 90, 1, 93);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (100, '快速开发', '快速开发', 'fast-dev', '#', '#', 1, 'layui-icon-code-circle', 0, 1, 100);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (101, '自定义菜单', '自定义菜单', 'feature', 'webui/feature', 'webui/feature', 1, '', 100, 1, 101);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (101, '查看', '', '0', 'webui:feature-list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (101, '增加', '', '0', 'webui:feature-new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (101, '删除', '', '0', 'webui:feature-delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (101, '修改', '', '0', 'webui:feature-renew', 4);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (108, '代码生成器', '代码生成器', 'code', 'code/builder', 'code/builder', 1, '', 100, 1, 108);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (108, '查看', '', '0', 'formio:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (108, '增加', '', '0', 'formio:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (108, '删除', '', '0', 'formio:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (108, '修改', '', '0', 'formio:renew', 4);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (108, '详情', '', '0', 'formio:detail', 5);

INSERT INTO t_feature_list (f_id, f_name, f_abbr, f_code, f_path, f_component, f_type, f_icon, f_parent, f_visible, f_order)
VALUES (109, '服务生成器', '服务生成器', 'service', 'service/builder', 'service/builder', 1, '', 100, 1, 109);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (109, '查看', '', '0', 'formio:list', 1);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (109, '增加', '', '0', 'formio:new', 2);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (109, '删除', '', '0', 'formio:delete', 3);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (109, '修改', '', '0', 'formio:renew', 4);
INSERT INTO t_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES (109, '详情', '', '0', 'formio:detail', 5);

COMMIT;