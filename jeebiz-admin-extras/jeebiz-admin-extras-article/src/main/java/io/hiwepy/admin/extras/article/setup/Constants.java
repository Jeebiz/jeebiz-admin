package io.hiwepy.admin.extras.article.setup;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Constants {
    
	public static final String GROUP_name = "group1";
    public static Marker logMarker = MarkerFactory.getMarker("Kafka-Log");

    public static final String ARTICLE_CATEGORY = "通知公告分类";
    public static final String ARTICLE_TOPIC = "通知公告栏目";
    public static final String ARTICLE_COMMENT = "通知公告评论";
    public static final String ARTICLE_ATT = "通知公告附件";
    public static final String ARTICLE = "通知公告信息";
    
    public static class Biz {

        // 通知公告分类详情
        public static final String ARTICLE_LIST = "通知公告信息列表";
        public static final String ARTICLE_ALL = "通知公告信息全部获取";
        public static final String ARTICLE_NEW = "通知公告信息新增";
        public static final String ARTICLE_RENEW = "通知公告信息编辑";
        public static final String ARTICLE_DEL = "通知公告信息删除";
        public static final String ARTICLE_VIEW = "通知公告信息详情";
        
        // 通知公告分类详情
        public static final String ARTICLE_CATEGORY_LIST = "通知公告分类信息列表";
        public static final String ARTICLE_CATEGORY_ALL = "通知公告分类信息全部获取";
        public static final String ARTICLE_CATEGORY_NEW = "通知公告分类信息新增";
        public static final String ARTICLE_CATEGORY_RENEW = "通知公告分类信息编辑";
        public static final String ARTICLE_CATEGORY_DEL = "通知公告分类信息删除";
        public static final String ARTICLE_CATEGORY_VIEW = "通知公告分类信息详情";

        // 通知公告栏目详情
        public static final String ARTICLE_TOPIC_LIST = "通知公告栏目信息列表";
        public static final String ARTICLE_TOPIC_ALL = "通知公告栏目信息全部获取";
        public static final String ARTICLE_TOPIC_NEW = "通知公告栏目信息新增";
        public static final String ARTICLE_TOPIC_RENEW = "通知公告栏目信息编辑";
        public static final String ARTICLE_TOPIC_DEL = "通知公告栏目信息删除";
        public static final String ARTICLE_TOPIC_VIEW = "通知公告栏目信息详情";
        public static final String ARTICLE_TOPIC_TREE = "通知公告栏目树";

        // 通知公告回复详情
        public static final String ARTICLE_COMMENT_LIST = "通知公告回复信息列表";
        public static final String ARTICLE_COMMENT_ALL = "通知公告回复信息全部获取";
        public static final String ARTICLE_COMMENT_NEW = "通知公告回复信息新增";
        public static final String ARTICLE_COMMENT_RENEW = "通知公告回复信息编辑";
        public static final String ARTICLE_COMMENT_DEL = "通知公告回复信息删除";
        public static final String ARTICLE_COMMENT_VIEW = "通知公告回复信息详情";
        public static final String ARTICLE_COMMENT_TREE = "通知公告回复树";
        
        // 通知公告附件详情
        public static final String ARTICLE_ATT_LIST = "通知公告附件信息列表";
        public static final String ARTICLE_ATT_ALL = "通知公告附件信息全部获取";
        public static final String ARTICLE_ATT_NEW = "通知公告附件信息新增";
        public static final String ARTICLE_ATT_RENEW = "通知公告附件信息编辑";
        public static final String ARTICLE_ATT_DEL = "通知公告附件信息删除";
        public static final String ARTICLE_ATT_VIEW = "通知公告附件信息详情";

    }
    public static class Nums {

        //默认页面大小
        public static final Integer DEFAULTPAGESIZE = 15;

        //通知公告分类默认参数
        public static final Integer CATEGORY_DEFAULTGRADE = 1;//默认级别
        public static final Integer CATEGORY_DEFAULTORDERLIST = 1;//默认排序

        //通知公告频道
        public static final Integer TOPIC_DEFAULTORDERLIST = 1;//默认排序
    }
}
