
/*系统监控预警配置*/
delete zftal_xtgl_xtszb t where t.zdm in('watch.cup.threshold','watch.ram.threshold','watch.jvm.threshold','watch.notice.status','watch.notice.period','watch.notice.type','watch.mail.to','watch.sms.to');
/*指定预设的CPU使用率警告阈值*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.cup.threshold', '95', 'MONITOR', 'CPU使用率警告阈值', '预设的CPU使用率警告阈值，当CPU使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预设的Ram使用率警告阈值*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.ram.threshold', '95', 'MONITOR', '内存使用率警告阈值', '预设的内存使用率警告阈值，当内存使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预设的JVM使用率警告阈值*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.jvm.threshold', '95', 'MONITOR', 'JVM使用率警告阈值', '预设的JVM使用率警告阈值，当JVM使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预警服务状态 on,off*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.status', 'off', 'MONITOR', '预警服务状态', '预警服务状态；开启/关闭', '', '{required:true}', 4, 'on:开启,off:关闭');
/*指定预警周期*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.period', '10m', 'MONITOR', '预警周期', '指定多少时间内进行一次预警通知；格式如：1s、1m、1h、1d；分别表示：1秒、1分钟、1小时、1天', '', '{required:true}', 2, '');
/*指定预警信息通知方式 email,sms,app*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.type', 'email', 'MONITOR', '预警信息通知方式', '预警信息通知方式；none：不通知、email：电子邮件、sms：手机短信、app：移动校园消息推送（待实现）', '', '{required:true}', 4, 'none:不通知,email:电子邮件,sms:手机短信,app:移动校园消息推送（待实现）');
/*指定预警信息接收邮件*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.mail.to', 'zfsoft@163.com', 'MONITOR', '预警信息接收邮件', '邮箱账户名;如：zfsoft@163.com。该预留邮箱将在开启了邮件通知的情况下接收到预警邮件。', '', '{required:true}', 2, '');
/*指定预警短信接收号码*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.sms.to', '13000000000', 'MONITOR', '预警短信接收号码', '该预留号码将在开启了短信通知的情况下接收到预警短信。', '', '{required:true}', 2, '');


/*应用监控*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0165','N016510','N016515','N016520');

insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX,CDJB,GNSYDX) values ('N0165','应用监控','N01','','1', '1', 'gl'); 

/*实时状态*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016510','监控预警','N0165','/monitor/watch/index.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016510';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016510','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016510','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016510%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016510', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016510', 'cx');

/*Druid监控*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016515','Druid监控','N0165','/druid', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016515';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016515','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016515%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016515', 'cx');

/*缓存监控*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016520','缓存监控','N0165','/monitor/cache/index.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016520';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016520','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016520','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016520%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016520', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016520', 'cx'); 
 
commit;
