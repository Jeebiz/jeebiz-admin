package net.jeebiz.admin.extras.filestore;

import java.util.function.BiFunction;

import org.springframework.data.redis.core.RedisKey;

public enum FilestoreRedisKey {

	/**
	 * redis 文件上传黑名单缓存
	 */
	UPLOAD_BLACKLIST("文件上传黑名单", (mobile, type)->{
		return RedisKey.getKeyStr(FilestoreRedisKeyConstant.SET_UPLOAD_BLACK_LIST);
    }),
	;
	
	private String desc;
    private BiFunction<String, String, String> function;
    
    FilestoreRedisKey(String desc, BiFunction<String, String, String> function) {
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
        return this.function.apply(null,null);
    }
    
    
    /**
     * 1、获取全名称key
     * @param key
     * @return
     */
    public String getKey(String key1) {
        return this.function.apply(key1,null);
    }
    
    /**
     * 1、获取全名称key
     * @param key
     * @return
     */
    public String getKey(String key1,String key2) {
        return this.function.apply(key1,key2);
    }

    public BiFunction<String, String, String> getFunction() {
        return function;
    }
	
}
