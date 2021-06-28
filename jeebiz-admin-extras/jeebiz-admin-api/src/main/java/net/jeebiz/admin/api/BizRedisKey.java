package net.jeebiz.admin.api;

import org.slf4j.helpers.MessageFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

public enum BizRedisKey {

	/**
	 * redis 应用黑名单缓存
	 */
	APP_BLACKLIST("应用黑名单", (blType, type)->{
		return RedisKeyConstant.getKeyStr(RedisKeyConstant.APP_BLACKLIST_PREFIX, blType);
    }),
    /**
     * 服务器资源使用率记录
     */
    SERVER_USAGE_HISTORY("服务器资源使用率记录", (srType, type)->{
        String prefix = MessageFormatter.format(RedisKeyConstant.SERVER_USAGE_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RedisKeyConstant.YYYYMMDD);
        return RedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
    }),
    /**
     * 服务器资源使用记录
     */
    SERVER_USED_HISTORY("服务器资源使用记录", (srType, type)->{
        String prefix = MessageFormatter.format(RedisKeyConstant.SERVER_USED_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RedisKeyConstant.YYYYMMDD);
        return RedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
    }),
    /**
     * 服务器资源空闲记录
     */
    SERVER_FREE_HISTORY("服务器资源空闲记录", (srType, type)->{
        String prefix = MessageFormatter.format(RedisKeyConstant.SERVER_FREE_HISTORY_PREFIX, srType).getMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RedisKeyConstant.YYYYMMDD);
        return RedisKeyConstant.getKeyStr(prefix, LocalDate.now().format(formatter));
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

    public BiFunction<String, String, String> getFunction() {
        return function;
    }
	
}
