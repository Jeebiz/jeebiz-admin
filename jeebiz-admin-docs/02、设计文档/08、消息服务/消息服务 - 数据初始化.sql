
/*网络代理配置*/
delete zftal_xtgl_csszb t where t.zdm in('net.proxySet','net.proxyType','net.proxyUser','net.proxyPassword','net.proxyHost','net.proxyPort','net.nonProxyHosts');
/*指定是否使用网络代理*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxySet', '1', 'MS-NetWork', '是否使用网络代理', '指定应用服务是否使用网络代理;该设置在无法访问外网下很有用，比如发送邮件和短信等;1:使用网络代理 0: 不使用网络代理', '', '{required:true}', 3, 'fixed:1,0');
/*指定网络代理类型 nomoral,http,https,socks*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyType', 'http', 'MS-NetWork', '网络代理类型', '网络代理类型，如果拥有发送邮件必须是socks类型的代理!', '', '{required:true}', 3, 'fixed:nomoral,http,https,socks');
/*指定网络代理认证账户*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyUser', '', 'MS-NetWork', '网络代理认证账户', '如果使用的代理服务有安全认证，必须设置才能通过代理', '', '{required:false}', 2, '');
/*指定网络代理认证账户密码*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPassword', '', 'MS-NetWork', '网络代理认证账户密码', '', '', '{required:false}', 2, '');
/*指定要使用的网络代理主机IP*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyHost', '192.168.1.1', 'MS-NetWork', '网络代理主机IP', '应用程序要使用的网络代理主机的IP地址!', '', '{required:true}', 2, '');
/*指定要使用的网络代理主机端口*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.proxyPort', '1080', 'MS-NetWork', '网络代理主机端口', '应用程序要使用的网络代理端口', '', '{required:true}', 2, '');
/*指定不需要通过代理服务器访问的主机IP地址，可以使用*通配符，多个地址用|分隔*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('net.nonProxyHosts', '*', 'MS-NetWork', '不需要通过代理服务器访问的主机IP地址', '不需要通过代理服务器访问的主机IP地址，可以使用*通配符，多个地址用|分隔', '', '{required:true}', 2, '');


/*邮件客户端配置*/
delete zftal_xtgl_csszb t where t.zdm in('mail.from.desc', 'mail.host','mail.port','mail.user','mail.password','mail.transport.protocol','mail.smtp.auth','mail.smtp.ssl.enable','mail.smtp.timeout');
/*指定密码找回发送邮件的发送方描述*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.from.desc', 'xxx大学-网络安全中心', 'MS-Email', '密码找回发送邮件的发送方描述', '通常指学校的网络中心官方称呼;如：xxx大学-网络中心', '', '{required:true}', 2, '');
/*指定邮件服务地址*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.host', 'smtp.163.com', 'MS-Email', '邮件服务地址', '发送邮件的邮件服务器地址，如smtp.163.com', '', '{required:true}', 2, '');
/*指定邮件服务端口*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.port', '25', 'MS-Email', '邮件服务端口', '邮件服务器smtp端口，默认：25', '', '{required:true}', 2, '');
/*指定邮件服务账户名*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.user', 'zfsoft@163.com', 'MS-Email', '邮箱服务账户名', '邮箱服务账户名;如：zfsoft@163.com', '', '{required:true}', 2, '');
/*指定邮件服务账户密码：加密存储*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.password', 'xxx', 'MS-Email', '邮箱服务账户密码', '邮箱服务账户密码!', '', '{required:true}', 2, '');
/*指定邮件服务邮件协议 smtp、nntp、pop3、imap，默认： smtp*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.transport.protocol', 'smtp', 'MS-Email', '邮箱服务邮件协议', 'smtp、nntp、pop3、imap;详情参见：http://blog.163.com/zhangjie_it/blog/static/1167348920112268333769/', '', '{required:true}', 3, 'fixed:smtp,nntp,pop3,imap');

/*指定是否需要权限验证*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)  
values ('mail.smtp.auth', '1', 'MS-Email', '是否需要权限验证', '是否需要权限验证(1:验证;0:不验证)', '', '{required:true}', 3, 'fixed:1,0');
/*指定是否需要权限验证*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.ssl.enable', '0', 'MS-Email', '是否使用SSL加密通信', '是否使用SSL加密通信(1:使用;0:不使用)', '', '{required:true}', 3, 'fixed:1,0');
/*指定是否需要权限验证*/  
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('mail.smtp.timeout', '25000', 'MS-Email', '发送超时时间', '邮件发送超时时间，默认25000（单位：毫秒）', '', '{required:true}', 2, '25000');

/*短信服务配置：不同的服务提供商参数不同，配置也将不相同*/
delete zftal_xtgl_csszb t where t.zdm in('sms.provider','sms.appid','sms.password','sms.token','sms.msgtype','sms.source');
/*SMS服务商*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.provider', 'zdt', 'MS-SMS', 'SMS服务商', 'SMS服务商类型，请注意不同的服务商客户端实现参数可能不同。', '', '{required:true}', 1, 'fixed:zdt:浙大短信平台,baidu-106:凯信通');
/*SMS服务商提供的appid*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.appid', '', 'MS-SMS', 'SMS服务商提供的appid', '', '', '{required:true}', 2, '');
/*SMS服务商提供的appid对应的password*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.password', '', 'MS-SMS', 'SMS服务商提供的appid对应的password', '', '', '{required:true}', 2, '');
/*SMS服务商提供的appid对应的token*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.token', '', 'MS-SMS', 'SMS服务商提供的appid对应的token', '', '', '{required:true}', 2, '');
/*发送类型 1:普通短信 2:长短信(超过64字符) */
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.msgtype', '1', 'MS-SMS', '短信发送类型', '短信发送类型； 1:普通短信 2:长短信(超过64字符) ', '','{required:true}', 3, 'fixed:1,2');
/*调用SMS接口的来源，即请求方标记*/
insert into zftal_xtgl_csszb (ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('sms.source', '', 'MS-SMS', '调用SMS接口的来源，即请求方标记', '', '', '{required:true}', 2, '');
 

/*消息服务*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0115','N011510','N011515','N011520');

insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N0115','消息服务','N01','','1'); 

/*网络环境参数*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011510','网络环境参数','N0115','/ms/network/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011510';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011510','xg','N011510_xg','ms-net:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011510','#','N011510_#','ms-net:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011510%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011510_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011510_#');

/*短信服务参数*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011515','短信服务参数','N0115','/ms/sms/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011515';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011515','xg','N011515_xg','ms-sms:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011515','#','N011515_#','ms-sms:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011515%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011515_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011515_#');

/*邮件服务参数*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N011520','邮件服务参数','N0115','/ms/mailclient/settings.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N011520';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011520','xg','N011520_xg','ms-mailclient:xg','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N011520','#','N011520_#','ms-mailclient:cx','0');

delete from zftal_xtgl_jsgnmkczb where gnczid like 'N011520%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011520_xg'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N011520_#');
 
 
commit;
