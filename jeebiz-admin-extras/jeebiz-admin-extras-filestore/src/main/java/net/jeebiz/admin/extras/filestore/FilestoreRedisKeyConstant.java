package io.hiwepy.admin.extras.filestore;

import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;

public class FilestoreRedisKeyConstant extends BizRedisKeyConstant {

	/**
	 * 文件上传黑名单
	 */
	public static String SET_UPLOAD_BLACK_LIST = "set:upload:blacklist";
	
}
