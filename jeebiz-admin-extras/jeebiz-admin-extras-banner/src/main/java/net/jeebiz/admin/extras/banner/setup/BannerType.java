package net.jeebiz.admin.extras.banner.setup;

/**
 * 轮播图类型枚举
 */
public enum BannerType {

    HOME_PAGE(1, "homepage", "首页轮播图"),
    MY_CENTER(2, "myCenter", "我的页面轮播图"),
    SEARCH_PAGE(3, "search", "搜索页面轮播图");

    private Integer code;
    private String redisKey;
    private String desc;

    BannerType(Integer code, String redisKey, String desc) {
        this.code = code;
        this.redisKey = redisKey;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public String getDesc() {
        return desc;
    }

    public static BannerType getByCode(int code){
        for (BannerType value : BannerType.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return HOME_PAGE;
    }

}
