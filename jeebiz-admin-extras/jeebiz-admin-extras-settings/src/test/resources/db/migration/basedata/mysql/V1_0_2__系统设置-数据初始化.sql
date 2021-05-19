
DELETE FROM sys_data_settings;

INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('frontend', '首页标题', 'title', 'Jeebiz Admin', 'text', 'required', '前端首页标题', '', '', 1, 1);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('frontend', 'META关键词', 'keywords', '© 2018 jeebiz.net MIT license', 'textarea', 'required', '', '多个关键词用英文状态 , 号分割','', 1, 2);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('frontend', 'META描述', 'description', 'Jeebiz-Admin 是 jeebiz 官方出品的通用型Java快速开发平台，采用模块化开发模式可实现按需定制功能。Jeebiz-Admin 基于目前非常流行的 Spring Boot、 Mybatis、Druid、Thymeleaf 等框架实现，实现了 Element、IVew、Layui、Mui 主流前端框架的节目适配。', 'textarea', 'required', '', '','', 1, 3);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('frontend', '版权信息', 'copyright', '© 2018 jeebiz.net MIT license', 'text', 'required', '服务提供商版权信息', '', '', 1, 4);

INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('backtend', '系统标题', 'title', 'Jeebiz Admin', 'text', 'required', '后台首页系统标题', '', '', 1, 1);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('backtend', '版权信息', 'copyright', '© 2018 jeebiz.net MIT license', 'text', 'required', '供应商版权信息', '', '', 1, 1);

INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', 'SMTP服务器', 'mail.smtp.host', '127.0.0.1', 'text', 'required', '如：smtp.163.com', '', '', 1, 1);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', 'SMTP端口号', 'mail.smtp.port', '25', 'text', 'required', '一般为 25 或 465', '', '', 1, 2);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', '发件人邮箱', 'mail.smtp.user', 'jeebiz@163.com', 'text', 'required', '如：jeebiz@163.com', '', '', 1, 3);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', '发件人昵称', 'mail.smtp.sender', 'Jeebiz', 'text', 'required', '收件人看到的邮件发送人', '', '', 1, 4);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', '邮箱登入密码', 'mail.smtp.password', '123456', 'text', 'required', '邮件账号的登录密码', '','',  1, 5);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', 'Socks网络代理', 'proxySet', 'OFF', 'switch', 'required', '通过Socks网络代理进行邮件发送', '', '', 1, 6);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', 'Socks代理地址', 'socksProxyHost', '127.0.0.1', 'text', 'required', '', '', '', 1, 7);
INSERT INTO sys_data_settings (d_group, d_label, d_key, d_text, d_type, d_rules, d_remark, d_placeholder, d_source, d_status, d_order)
VALUES ('email', 'Socks代理端口', 'socksProxyPort', '1080', 'text', 'required', '', '', '', 1, 8);

INSERT INTO sys_authz_feature_list (f_id, f_name, f_abb, f_code, f_url, f_type, f_icon, f_parent, f_visible, f_order)
VALUES ('2', '基础设置', '基础设置', 'sets', '', '1', '', '0', '1', '4');
INSERT INTO sys_authz_feature_list (f_id, f_name, f_abb, f_code, f_url, f_type, f_icon, f_parent, f_visible, f_order)
VALUES ('3', '系统设置', '系统设置', 'appsets', '', '1', '', '2', '1', '4');
INSERT INTO sys_authz_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES ('3', '查看', '', '0', 'sets:list', 1);
INSERT INTO sys_authz_feature_opts (f_id, opt_name, opt_icon, opt_visible, opt_perms, opt_order)
VALUES ('3', '保存', '', '0', 'sets:reset', 2);

COMMIT;