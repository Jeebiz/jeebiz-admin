package net.jeebiz.admin.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author Administrator
 */

public enum BizRedisKey {

	/**
	 * redis 应用黑名单缓存
	 */
	APP_BLACKLIST("应用黑名单", (blType, type)->{
		return BizRedisKeyConstant.getKeyStr(BizRedisKeyConstant.APP_BLACKLIST_PREFIX, blType);
    }),
    /**
     * 数据字典缓存
     */
    APP_DICT("数据字典缓存", (blType, type)->{
        return BizRedisKeyConstant.getKeyStr(BizRedisKeyConstant.APP_DICT_PREFIX, blType);
    }),

    /**
     * 轮播图
     */
    BANNER_LIST("轮播图", (code, lang) -> {
        return BizRedisKeyConstant.getKeyStr(BizRedisKeyConstant.BANNER_LIST, code, lang);
    }),
    /**
     * 服务器资源使用率记录
     */
    SERVER_USAGE_HISTORY("服务器资源使用率记录", (srType, type)->{
        String prefix = MessageFormatter.format(BizRedisKeyConstant.SERVER_USAGE_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BizRedisKeyConstant.YYYYMMDD);
        return BizRedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
    }),
    /**
     * 服务器资源使用记录
     */
    SERVER_USED_HISTORY("服务器资源使用记录", (srType, type)->{
        String prefix = MessageFormatter.format(BizRedisKeyConstant.SERVER_USED_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BizRedisKeyConstant.YYYYMMDD);
        return BizRedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
    }),
    /**
     * 服务器资源空闲记录
     */
    SERVER_FREE_HISTORY("服务器资源空闲记录", (srType, type)->{
        String prefix = MessageFormatter.format(BizRedisKeyConstant.SERVER_FREE_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BizRedisKeyConstant.YYYYMMDD);
        return BizRedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
    });

    private String desc;
    private BiFunction<String, String, String> function;

    BizRedisKey(String desc, BiFunction<String, String, String> function) {
        this.desc = desc;
        this.function = function;
    }

    public String getDesc() {
		return desc;
	}

    /**
     * 1、获取全名称key
     * @return
     */
    public String getKey() {
        return this.function.apply(null, null);
    }

    /**
     * 1、获取全名称key
     * @param key
     * @return
     */
    public String getKey(String key) {
        return this.function.apply(key, null);
    }

    /**
     * 2、获取全名称key
     * @param key
     * @return
     */
    public String getKey(String key, String key2) {
        return this.function.apply(key, key2);
    }

}
