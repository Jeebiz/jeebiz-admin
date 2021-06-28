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

	/**
	 * 应用黑名单缓存
	 */
	public final static String APP_BLACKLIST_PREFIX = "app:blacklist";

	/**
	 * 服务器资源使用率记录
	 */
	public final static String SERVER_USAGE_HISTORY_PREFIX = "server:usage:{}";
	/**
	 * 服务器资源使用记录
	 */
	public final static String SERVER_USED_HISTORY_PREFIX = "server:used:{}";
	/**
	 * 服务器资源空闲记录
	 */
	public final static String SERVER_FREE_HISTORY_PREFIX = "server:free:{}";

	public final static String SET_APPS = "user:apps";

	public final static String DBMATA_CATALOG = "dbmata:catalog";
	public final static String DBMATA_CATALOG_LODING = "dbmata:catalog:loding";
	 
	
}
