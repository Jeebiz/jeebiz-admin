


-- Add/modify columns 
alter table ZFTAL_XTGL_GNMKCZB modify CZDM VARCHAR2(20);
alter table ZFTAL_XTGL_GNMKCZB modify QXDM VARCHAR2(50);

/*�ϴ������ذ�ť*/

delete zftal_xtgl_czdmb where czdm in ('upload','download');

insert into zftal_xtgl_czdmb (CZDM, CZMC, ANYS)
values ('upload', '�ϴ��ļ�', 'glyphicon glyphicon-upload');
insert into zftal_xtgl_czdmb (CZDM, CZMC,ANYS)
values ('download', '�����ļ�', 'glyphicon glyphicon-download');

/*�ļ�����*/

delete zftal_xtgl_jsgnmkdmb where gnmkdm in ('N0125','N012510');

insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N0125','�ļ�����','N01','','1'); 

/*�ҵ��ļ�*/
insert into zftal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx) values ('N012510','�ҵ��ļ�','N0125','/filemgr/index.zf','1'); 

delete from zftal_xtgl_gnmkczb where gnmkdm='N012510';
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012510','#','N012510_#','filemgr-file:cx','0');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012510','upload','N012510_upload','filemgr-file:upload','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012510','download','N012510_download','filemgr-file:download','2');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012510','sc','N012510_sc','filemgr-file:del','0');
insert into zftal_xtgl_gnmkczb(gnmkdm,czdm,gnczid,qxdm,xssx) values ('N012510','xg','N012510_xg','filemgr-file:update','0');


delete from zftal_xtgl_jsgnmkczb where gnczid like 'N012510%';
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012510_#');
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012510_upload');
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012510_download'); 
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012510_sc');
insert into zftal_xtgl_jsgnmkczb(jsdm,gnczid) values ('admin','N012510_xg');

commit;
