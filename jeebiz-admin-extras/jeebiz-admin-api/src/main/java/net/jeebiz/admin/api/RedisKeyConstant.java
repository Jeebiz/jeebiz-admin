package net.jeebiz.admin.api;

public class RedisKeyConstant extends net.jeebiz.boot.extras.redis.setup.RedisKeyConstant {
	 
	/**
	 * 用户经验增量缓存
	 */
	public static String USER_EXP_AMOUNT_KEY = "user:exp:amount:{}";
	/**
	 * 用户会员权益缓存
	 */
	public static String USER_RITHTS_KEY = "user:rights:{}";
	 
	
}
