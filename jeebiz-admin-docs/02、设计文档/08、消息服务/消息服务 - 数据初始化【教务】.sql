

/*�����������*/
delete zftal_xtgl_xtszb t where t.zdm in('net.proxySet','net.proxyType','net.proxyUser','net.proxyPassword','net.proxyHost','net.proxyPort','net.nonProxyHosts');
/*ָ���Ƿ�ʹ���������*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxySet', '1', 'MS-NetWork', '�Ƿ�ʹ���������', 'ָ��Ӧ�÷����Ƿ�ʹ���������;���������޷����������º����ã����緢���ʼ��Ͷ��ŵ�;1:ʹ��������� 0: ��ʹ���������', '', '{required:true}', 3, 'fixed:1,0');
/*ָ������������� nomoral,http,https,socks*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyType', 'http', 'MS-NetWork', '�����������', '����������ͣ����ӵ�з����ʼ�������socks���͵Ĵ���!', '', '{required:true}', 3, 'fixed:nomoral,http,https,socks');
/*ָ�����������֤�˻�*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyUser', '', 'MS-NetWork', '���������֤�˻�', '���ʹ�õĴ�������а�ȫ��֤���������ò���ͨ������', '', '{required:false}', 2, '');
/*ָ�����������֤�˻�����*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPassword', '', 'MS-NetWork', '���������֤�˻�����', '', '', '{required:false}', 2, '');
/*ָ��Ҫʹ�õ������������IP*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyHost', '192.168.1.1', 'MS-NetWork', '�����������IP', 'Ӧ�ó���Ҫʹ�õ��������������IP��ַ!', '', '{required:true}', 2, '');
/*ָ��Ҫʹ�õ�������������˿�*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPort', '1080', 'MS-NetWork', '������������˿�', 'Ӧ�ó���Ҫʹ�õ��������˿�', '', '{required:true}', 2, '');
/*ָ������Ҫͨ��������������ʵ�����IP��ַ������ʹ��*ͨ����������ַ��|�ָ�*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.nonProxyHosts', '*', 'MS-NetWork', '����Ҫͨ��������������ʵ�����IP��ַ', '����Ҫͨ��������������ʵ�����IP��ַ������ʹ��*ͨ����������ַ��|�ָ�', '', '{required:true}', 2, '');


/*�ʼ��ͻ�������*/
delete zftal_xtgl_xtszb t where t.zdm in('mail.from.desc', 'mail.host','mail.port','mail.user','mail.password','mail.transport.protocol','mail.smtp.auth','mail.smtp.ssl.enable','mail.smtp.timeout');
/*ָ�������һط����ʼ��ķ��ͷ�����*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.from.desc', 'xxx��ѧ-���簲ȫ����', 'MS-Email', '�����һط����ʼ��ķ��ͷ�����', 'ͨ��ָѧУ���������Ĺٷ��ƺ�;�磺xxx��ѧ-��������', '', '{required:true}', 2, '');
/*ָ���ʼ������ַ*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.host', 'smtp.163.com', 'MS-Email', '�ʼ������ַ', '�����ʼ����ʼ���������ַ����smtp.163.com', '', '{required:true}', 2, '');
/*ָ���ʼ�����˿�*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.port', '25', 'MS-Email', '�ʼ�����˿�', '�ʼ�������smtp�˿ڣ�Ĭ�ϣ�25', '', '{required:true}', 2, '');
/*ָ���ʼ������˻���*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.user', 'zfsoft@163.com', 'MS-Email', '��������˻���', '��������˻���;�磺zfsoft@163.com', '', '{required:true}', 2, '');
/*ָ���ʼ������˻����룺���ܴ洢*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.password', 'xxx', 'MS-Email', '��������˻�����', '��������˻�����!', '', '{required:true}', 2, '');
/*ָ���ʼ������ʼ�Э�� smtp��nntp��pop3��imap��Ĭ�ϣ� smtp*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.transport.protocol', 'smtp', 'MS-Email', '��������ʼ�Э��', 'smtp��nntp��pop3��imap;����μ���http://blog.163.com/zhangjie_it/blog/static/1167348920112268333769/', '', '{required:true}', 3, 'fixed:smtp,nntp,pop3,imap');

/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)  
values ('mail.smtp.auth', '1', 'MS-Email', '�Ƿ���ҪȨ����֤', '�Ƿ���ҪȨ����֤(1:��֤;0:����֤)', '', '{required:true}', 3, 'fixed:1,0');
/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.ssl.enable', '0', 'MS-Email', '�Ƿ�ʹ��SSL����ͨ��', '�Ƿ�ʹ��SSL����ͨ��(1:ʹ��;0:��ʹ��)', '', '{required:true}', 3, 'fixed:1,0');
/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.timeout', '25000', 'MS-Email', '���ͳ�ʱʱ��', '�ʼ����ͳ�ʱʱ�䣬Ĭ��25000����λ�����룩', '', '{required:true}', 2, '25000');

/*���ŷ������ã���ͬ�ķ����ṩ�̲�����ͬ������Ҳ������ͬ*/
delete zftal_xtgl_xtszb t where t.zdm in('sms.provider','sms.appid','sms.password','sms.token','sms.msgtype','sms.source');
/*SMS������*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.provider', 'zdt', 'MS-SMS', 'SMS������', 'SMS���������ͣ���ע�ⲻͬ�ķ����̿ͻ���ʵ�ֲ������ܲ�ͬ��', '', '{required:true}', 1, 'fixed:zdt:������ƽ̨,baidu-106:����ͨ');
/*SMS�������ṩ��appid*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.appid', '', 'MS-SMS', 'SMS�������ṩ��appid', '', '', '{required:true}', 2, '');
/*SMS�������ṩ��appid��Ӧ��password*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.password', '', 'MS-SMS', 'SMS�������ṩ��appid��Ӧ��password', '', '', '{required:true}', 2, '');
/*SMS�������ṩ��appid��Ӧ��token*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.token', '', 'MS-SMS', 'SMS�������ṩ��appid��Ӧ��token', '', '', '{required:true}', 2, '');
/*�������� 1:��ͨ���� 2:������(����64�ַ�) */
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.msgtype', '1', 'MS-SMS', '���ŷ�������', '���ŷ������ͣ� 1:��ͨ���� 2:������(����64�ַ�) ', '','{required:true}', 3, 'fixed:1,2');
/*����SMS�ӿڵ���Դ�������󷽱��*/
insert into zftal_xtgl_xtszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.source', '', 'MS-SMS', '����SMS�ӿڵ���Դ�������󷽱��', '', '', '{required:true}', 2, '');
 


/*��Ϣ����*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0160','N016010','N016015','N016020');

insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX,CDJB,GNSYDX) values ('N0160','��Ϣ����','N01', '', '1', '1', 'gl'); 

/*���绷������*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016010','���绷������','N0160','/ms/network/settings.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016010';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016010','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016010','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016010%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016010', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016010', 'cx');

/*���ŷ������*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016015','���ŷ������','N0160','/ms/sms/settings.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016015';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016015','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016015','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016015%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016015', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016015', 'cx');

/*�ʼ��������*/
insert into zftal_xtgl_jsgnmkdmb(GNMKDM, GNMKMC,FJGNDM, DYYM, XSSX, SFXS, SFZDYMK, XSLX) values ('N016020','�ʼ��������','N0160','/ms/mailclient/settings.zf', '1', '1', '0', '0'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N016020';
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016020','xg', '1', '0');
insert into zftal_xtgl_gnmkczb(GNMKDM, CZDM, SFXS, SFZDYCZ) values ('N016020','cx', '0', '0');

delete from zftal_xtgl_jsgnmkczb where gnmkdm like 'N016020%';
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016020', 'xg'); 
insert into zftal_xtgl_jsgnmkczb(JSDM, GNMKDM, CZDM) values ('admin','N016020', 'cx');
 
 
commit;
