
/*�����������*/
delete zftal_xtgl_csszb t where t.zdm in('net.proxySet','net.proxyType','net.proxyUser','net.proxyPassword','net.proxyHost','net.proxyPort','net.nonProxyHosts');
/*ָ���Ƿ�ʹ���������*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxySet', '1', 'MS-NetWork', '�Ƿ�ʹ���������', 'ָ��Ӧ�÷����Ƿ�ʹ���������;���������޷����������º����ã����緢���ʼ��Ͷ��ŵ�;1:ʹ��������� 0: ��ʹ���������', '', '{required:true}', 3, 'fixed:1,0');
/*ָ������������� nomoral,http,https,socks*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyType', 'http', 'MS-NetWork', '�����������', '����������ͣ����ӵ�з����ʼ�������socks���͵Ĵ���!', '', '{required:true}', 3, 'fixed:nomoral,http,https,socks');
/*ָ�����������֤�˻�*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyUser', '', 'MS-NetWork', '���������֤�˻�', '���ʹ�õĴ�������а�ȫ��֤���������ò���ͨ������', '', '{required:false}', 2, '');
/*ָ�����������֤�˻�����*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPassword', '', 'MS-NetWork', '���������֤�˻�����', '', '', '{required:false}', 2, '');
/*ָ��Ҫʹ�õ������������IP*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyHost', '192.168.1.1', 'MS-NetWork', '�����������IP', 'Ӧ�ó���Ҫʹ�õ��������������IP��ַ!', '', '{required:true}', 2, '');
/*ָ��Ҫʹ�õ�������������˿�*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPort', '1080', 'MS-NetWork', '������������˿�', 'Ӧ�ó���Ҫʹ�õ��������˿�', '', '{required:true}', 2, '');
/*ָ������Ҫͨ��������������ʵ�����IP��ַ������ʹ��*ͨ����������ַ��|�ָ�*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.nonProxyHosts', '*', 'MS-NetWork', '����Ҫͨ��������������ʵ�����IP��ַ', '����Ҫͨ��������������ʵ�����IP��ַ������ʹ��*ͨ����������ַ��|�ָ�', '', '{required:true}', 2, '');


/*�ʼ��ͻ�������*/
delete zftal_xtgl_csszb t where t.zdm in('mail.from.desc', 'mail.host','mail.port','mail.user','mail.password','mail.transport.protocol','mail.smtp.auth','mail.smtp.ssl.enable','mail.smtp.timeout');
/*ָ�������һط����ʼ��ķ��ͷ�����*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.from.desc', 'xxx��ѧ-���簲ȫ����', 'MS-Email', '�����һط����ʼ��ķ��ͷ�����', 'ͨ��ָѧУ���������Ĺٷ��ƺ�;�磺xxx��ѧ-��������', '', '{required:true}', 2, '');
/*ָ���ʼ������ַ*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.host', 'smtp.163.com', 'MS-Email', '�ʼ������ַ', '�����ʼ����ʼ���������ַ����smtp.163.com', '', '{required:true}', 2, '');
/*ָ���ʼ�����˿�*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.port', '25', 'MS-Email', '�ʼ�����˿�', '�ʼ�������smtp�˿ڣ�Ĭ�ϣ�25', '', '{required:true}', 2, '');
/*ָ���ʼ������˻���*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.user', 'zfsoft@163.com', 'MS-Email', '��������˻���', '��������˻���;�磺zfsoft@163.com', '', '{required:true}', 2, '');
/*ָ���ʼ������˻����룺���ܴ洢*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.password', 'xxx', 'MS-Email', '��������˻�����', '��������˻�����!', '', '{required:true}', 2, '');
/*ָ���ʼ������ʼ�Э�� smtp��nntp��pop3��imap��Ĭ�ϣ� smtp*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.transport.protocol', 'smtp', 'MS-Email', '��������ʼ�Э��', 'smtp��nntp��pop3��imap;����μ���http://blog.163.com/zhangjie_it/blog/static/1167348920112268333769/', '', '{required:true}', 3, 'fixed:smtp,nntp,pop3,imap');

/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)  
values ('mail.smtp.auth', '1', 'MS-Email', '�Ƿ���ҪȨ����֤', '�Ƿ���ҪȨ����֤(1:��֤;0:����֤)', '', '{required:true}', 3, 'fixed:1,0');
/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.ssl.enable', '0', 'MS-Email', '�Ƿ�ʹ��SSL����ͨ��', '�Ƿ�ʹ��SSL����ͨ��(1:ʹ��;0:��ʹ��)', '', '{required:true}', 3, 'fixed:1,0');
/*ָ���Ƿ���ҪȨ����֤*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.timeout', '25000', 'MS-Email', '���ͳ�ʱʱ��', '�ʼ����ͳ�ʱʱ�䣬Ĭ��25000����λ�����룩', '', '{required:true}', 2, '25000');

/*���ŷ������ã���ͬ�ķ����ṩ�̲�����ͬ������Ҳ������ͬ*/
delete zftal_xtgl_csszb t where t.zdm in('sms.provider','sms.appid','sms.password','sms.token','sms.msgtype','sms.source');
/*SMS������*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.provider', 'zdt', 'MS-SMS', 'SMS������', 'SMS���������ͣ���ע�ⲻͬ�ķ����̿ͻ���ʵ�ֲ������ܲ�ͬ��', '', '{required:true}', 1, 'fixed:zdt:������ƽ̨,baidu-106:����ͨ');
/*SMS�������ṩ��appid*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.appid', '', 'MS-SMS', 'SMS�������ṩ��appid', '', '', '{required:true}', 2, '');
/*SMS�������ṩ��appid��Ӧ��password*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.password', '', 'MS-SMS', 'SMS�������ṩ��appid��Ӧ��password', '', '', '{required:true}', 2, '');
/*SMS�������ṩ��appid��Ӧ��token*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.token', '', 'MS-SMS', 'SMS�������ṩ��appid��Ӧ��token', '', '', '{required:true}', 2, '');
/*�������� 1:��ͨ���� 2:������(����64�ַ�) */
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.msgtype', '1', 'MS-SMS', '���ŷ�������', '���ŷ������ͣ� 1:��ͨ���� 2:������(����64�ַ�) ', '','{required:true}', 3, 'fixed:1,2');
/*����SMS�ӿڵ���Դ�������󷽱��*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.source', '', 'MS-SMS', '����SMS�ӿڵ���Դ�������󷽱��', '', '', '{required:true}', 2, '');
 

/*��Ϣ����*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0115','N011510','N011515','N011520');

insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N0115','��Ϣ����','N01','','1'); 

/*���绷������*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011510','���绷������','N0115','/ms/network/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011510';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011510','xg','N011510_xg','ms-net:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011510','#','N011510_#','ms-net:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011510%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011510_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011510_#');

/*���ŷ������*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011515','���ŷ������','N0115','/ms/sms/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011515';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011515','xg','N011515_xg','ms-sms:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011515','#','N011515_#','ms-sms:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011515%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011515_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011515_#');

/*�ʼ��������*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011520','�ʼ��������','N0115','/ms/mailclient/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011520';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011520','xg','N011520_xg','ms-mailclient:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011520','#','N011520_#','ms-mailclient:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011520%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011520_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011520_#');
 
 
commit;
