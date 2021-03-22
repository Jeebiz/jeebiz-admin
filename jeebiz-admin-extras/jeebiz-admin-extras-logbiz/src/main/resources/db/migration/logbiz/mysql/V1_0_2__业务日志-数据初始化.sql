
-- ---------------------------------------------
-- Records of SYS_FEATURE_LIST、SYS_FEATURE_OPTS
-- ---------------------------------------------

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('11', '监控', '监控', 'monitor', '#', '1', '', '0', '1', '2');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('12', '系统监控', '系统监控', 'sys-monitor', '', '1', '', '11', '1', '21');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查看', '', '0', 'monitor:list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('13', '服务监控', '服务监控', 'serv-monitor', '', '1', '', '11', '1', '22');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('13', '查看', '', '0', 'serv:stats-list', 1);


INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('14', '系统日志', '系统日志', 'bizLogs', '#', '1', '', '11', '1', '23');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('15', '系统异常', '系统异常', 'bizExcps', '', '1', '', '14', '1', '231');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查看', '', '0', 'logs:excp-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('16', '服务异常', '服务异常', 'servAcces', '', '1', '', '14', '1', '232');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查看', '', '0', 'logs:acces-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('17', '登录日志', '登录日志', 'authzs', '', '1', '', '14', '1', '233');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查看', '', '0', 'logs:authz-list', 1);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('18', '操作日志', '操作日志', 'bizOpts', '', '1', '', '14', '1', '234');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('18', '查看', '', '0', 'logs:opt-list', 1);


COMMIT;