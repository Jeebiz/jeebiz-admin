package net.jeebiz.admin.extras.core.setup.redis;

public interface RedisConstant {

	/**
	 * 用户登陆次数
	 */
	String STR_LOGIN_NUM = "STR:LOGIN:NUM:";

	/**
	 * 手机号黑名单
	 */
	String SET_SMS_BLACK_LIST = "SET:SMS:BLACK:LIST";
	/**
	 * 短信发送次数最大值
	 */
	Integer SMS_TIME_MAX = 10;
	/**
	 * 手机/设备发送短信次数记录过期时间
	 */
	Long SMS_TIME_EXPIRE = 3500 * 6L;
	/**
	 * 验证码过期时间
	 */
	Long SMS_EXPIRE = 60 * 5L;
	/**
	 * 记录手机号发短信次数
	 */
	String STR_SMS_MOBILE_TIME = "STR:SMS:MOBILE:TIME:";
	/**
	 * 记录设备发短信次数
	 */
	String STR_SMS_DEV_TIME = "STR:SMS:DEV:TIME:";
	/**
	 * 发送短信锁
	 */
	String STR_SMS_LOCK_MOBILE = "STR:SMS:LOCK:";
	/**
	 * 短信验证码 type + 手机号
	 */
	String STR_SMS_CODE = "STR:SMS:CODE:";
	/**
	 * 用户token过期时间
	 */
	Long TOKEN_EXPIRE = 7 * 86400L;
	/**
	 * 用户token
	 */
	String STR_TOKEN = "STR:TOKEN:";
	/**
	 * redis 用户信息前缀
	 */
	String USER_INFO_PREFIX = "USER_INFO_PREFIX";
	/**
	 * redis 用户单点登录状态
	 */
	String USER_SSO_STATE = "USER_SSO_STATE";
	/**
	 * 用户查询前缀
	 */
	String USER_QUERY_PREFIX = "USER_QUERY_PREFIX";
	/**
	 * 用户坐标缓存
	 */
	String USER_GEO_LOCATION = "USER_GEO_LOCATION";
	/**
	 * 锁过期时间5秒钟
	 */
	Long FIVE_SECONDS = 5 * 1000L;
	/**
	 * 查询数据库用户信息时加锁
	 */
	String USER_INFO_LOCK = "USER_INFO_LOCK";
	/**
	 * 用户信息解锁缓存
	 */
	String USER_INFO_UNLOCK = "USER_INFO_UNLOCK";
	/**
	 * 用户信息预览缓存
	 */
	String USER_INFO_PREVIEW = "USER_INFO_PREVIEW";
	/**
	 * 用户任务列表
	 */
	String USER_TASK_LIST = "USER_TASK_LIST";
	/**
	 * 用户金币增量缓存
	 */
	String USER_COIN_AMOUNT = "USER_COIN_AMOUNT";
	/**
	 * 用户珍珠增量缓存
	 */
	String USER_PEARL_AMOUNT = "USER_PEARL_AMOUNT";
	/**
	 * 用户经验增量缓存
	 */
	String USER_EXP_AMOUNT = "USER_EXP_AMOUNT";
	/**
	 * 用户会员类型缓存
	 */
	String USER_VIP_TYPES = "USER_VIP_TYPES";
	/**
	 * 用户会员权益缓存
	 */
	String USER_VIP_INTEREST = "USER_VIP_INTEREST";

}
