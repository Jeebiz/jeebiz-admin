
/*ϵͳ���Ԥ������*/
delete zftal_xtgl_xtszb t where t.zdm in('watch.cup.threshold','watch.ram.threshold','watch.jvm.threshold','watch.notice.status','watch.notice.period','watch.notice.type','watch.mail.to','watch.sms.to');
/*ָ��Ԥ���CPUʹ���ʾ�����ֵ*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.cup.threshold', '95', 'MONITOR', 'CPUʹ���ʾ�����ֵ', 'Ԥ���CPUʹ���ʾ�����ֵ����CPUʹ���ʳ�������ֵʱ���ᷢ�Ͷ��Ż����ʼ�֪ͨ��', '', '{required:true}', 2, '');
/*ָ��Ԥ���Ramʹ���ʾ�����ֵ*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.ram.threshold', '95', 'MONITOR', '�ڴ�ʹ���ʾ�����ֵ', 'Ԥ����ڴ�ʹ���ʾ�����ֵ�����ڴ�ʹ���ʳ�������ֵʱ���ᷢ�Ͷ��Ż����ʼ�֪ͨ��', '', '{required:true}', 2, '');
/*ָ��Ԥ���JVMʹ���ʾ�����ֵ*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.jvm.threshold', '95', 'MONITOR', 'JVMʹ���ʾ�����ֵ', 'Ԥ���JVMʹ���ʾ�����ֵ����JVMʹ���ʳ�������ֵʱ���ᷢ�Ͷ��Ż����ʼ�֪ͨ��', '', '{required:true}', 2, '');
/*ָ��Ԥ������״̬ on,off*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.status', 'off', 'MONITOR', 'Ԥ������״̬', 'Ԥ������״̬������/�ر�', '', '{required:true}', 4, 'on:����,off:�ر�');
/*ָ��Ԥ������*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.period', '10m', 'MONITOR', 'Ԥ������', 'ָ������ʱ���ڽ���һ��Ԥ��֪ͨ����ʽ�磺1s��1m��1h��1d���ֱ��ʾ��1�롢1���ӡ�1Сʱ��1��', '', '{required:true}', 2, '');
/*ָ��Ԥ����Ϣ֪ͨ��ʽ email,sms,app*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.notice.type', 'email', 'MONITOR', 'Ԥ����Ϣ֪ͨ��ʽ', 'Ԥ����Ϣ֪ͨ��ʽ��none����֪ͨ��email�������ʼ���sms���ֻ����š�app���ƶ�У԰��Ϣ���ͣ���ʵ�֣�', '', '{required:true}', 4, 'none:��֪ͨ,email:�����ʼ�,sms:�ֻ�����,app:�ƶ�У԰��Ϣ���ͣ���ʵ�֣�');
/*ָ��Ԥ����Ϣ�����ʼ�*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.mail.to', 'zfsoft@163.com', 'MONITOR', 'Ԥ����Ϣ�����ʼ�', '�����˻���;�磺zfsoft@163.com����Ԥ�����佫�ڿ������ʼ�֪ͨ������½��յ�Ԥ���ʼ���', '', '{required:true}', 2, '');
/*ָ��Ԥ�����Ž��պ���*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('watch.sms.to', '13000000000', 'MONITOR', 'Ԥ�����Ž��պ���', '��Ԥ�����뽫�ڿ����˶���֪ͨ������½��յ�Ԥ�����š�', '', '{required:true}', 2, '');


/*Ӧ�ü��*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0165','N016510','N016515','N016520');

insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX,CDJB,GNSYDX) values ('N0165','Ӧ�ü��','N01','','1', '1', 'gl'); 

/*ʵʱ״̬*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016510','���Ԥ��','N0165','/monitor/watch/index.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016510';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016510','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016510','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016510%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016510', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016510', 'cx');

/*Druid���*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016515','Druid���','N0165','/druid', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016515';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016515','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016515%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016515', 'cx');

/*������*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016520','������','N0165','/monitor/cache/index.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016520';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016520','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016520','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016520%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016520', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016520', 'cx'); 
 
commit;
