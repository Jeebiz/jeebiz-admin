package net.jeebiz.admin.api;

import java.util.function.BiFunction;

public enum BizRedisKey {

	/**
	 * redis 应用黑名单缓存
	 */
	APP_BLACKLIST("应用黑名单", (blType, type)->{
		return RedisKeyConstant.getKeyStr(RedisKeyConstant.APP_BLACKLIST_PREFIX, blType);
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
     * @param key
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
