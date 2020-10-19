
/*系统监控预警配置*/
delete zftal_xtgl_csszb t where t.zdm in('watch.cup.threshold','watch.ram.threshold','watch.jvm.threshold','watch.notice.status','watch.notice.period','watch.notice.type','watch.mail.to','watch.sms.to');
/*指定预设的CPU使用率警告阈值*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.cup.threshold', '95', 'MONITOR', 'CPU使用率警告阈值', '预设的CPU使用率警告阈值，当CPU使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预设的Ram使用率警告阈值*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.ram.threshold', '95', 'MONITOR', '内存使用率警告阈值', '预设的内存使用率警告阈值，当内存使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预设的JVM使用率警告阈值*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.jvm.threshold', '95', 'MONITOR', 'JVM使用率警告阈值', '预设的JVM使用率警告阈值，当JVM使用率超过该阈值时将会发送短信或者邮件通知。', '', '{required:true}', 2, '');
/*指定预警服务状态 on,off*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.status', 'off', 'MONITOR', '预警服务状态', '预警服务状态；开启/关闭', '', '{required:true}', 4, 'on:开启,off:关闭');
/*指定预警周期*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.period', '10m', 'MONITOR', '预警周期', '指定多少时间内进行一次预警通知；格式如：1s、1m、1h、1d；分别表示：1秒、1分钟、1小时、1天', '', '{required:true}', 2, '');
/*指定预警信息通知方式 email,sms,app*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.type', 'email', 'MONITOR', '预警信息通知方式', '预警信息通知方式；none：不通知、email：电子邮件、sms：手机短信、app：移动校园消息推送（待实现）', '', '{required:true}', 4, 'none:不通知,email:电子邮件,sms:手机短信,app:移动校园消息推送（待实现）');
/*指定预警信息接收邮件*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.mail.to', 'zfsoft@163.com', 'MONITOR', '预警信息接收邮件', '邮箱账户名;如：zfsoft@163.com。该预留邮箱将在开启了邮件通知的情况下接收到预警邮件。', '', '{required:true}', 2, '');
/*指定预警短信接收号码*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.sms.to', '13000000000', 'MONITOR', '预警短信接收号码', '该预留号码将在开启了短信通知的情况下接收到预警短信。', '', '{required:true}', 2, '');


/*应用监控*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0120','N012010','N012015','N012020');

insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N0120','应用监控','N01','','1'); 

/*实时状态*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N012010','监控预警','N0120','/monitor/watch/index.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N012010';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012010','#','N012010_#','monitor-watch:cx','0');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012010','#','N012010_xg','monitor-watch:xg','2');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N012010%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012010_#');
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012010_xg'); 

/*Druid监控*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N012015','Druid监控','N0120','/druid','1');

delete from zftal_xtgl_gnmkczb where gnmkdm='N012015';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012015','#','N012015_#','monitor-druid:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N012015%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012015_#');

/*缓存监控*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N012020','缓存监控','N0120','/monitor/cache/index.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N012020';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012020','xg','N012020_xg','monitor-cache:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012020','#','N012020_#','monitor-cache:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N012020%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012020_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012020_#');
 
 
commit;
