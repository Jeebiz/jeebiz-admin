/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.strategy;

import java.util.Calendar;
import java.util.Date;

import com.aliyun.oss.OSS;

public class AliyunOssTemplate {
	
	private OSS ossClient;
	
	public AliyunOssTemplate(OSS ossClient) {
		this.ossClient = ossClient;
	}

	public String getAccsssURL(String bucket, String path) throws Exception {
		// 过期时间为当日23:59:59
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date expiration = new Date(cal.getTimeInMillis());
		return getOssClient().generatePresignedUrl(bucket, path, expiration).toString();
	}
	
	public String getAccsssURL(AliyunOssStorePath storePath) throws Exception {
		return this.getAccsssURL(storePath.getBucket(), storePath.getPath());
	}
	
	public String getThumbAccsssURL(AliyunOssStorePath storePath) throws Exception {
		return this.getAccsssURL(storePath.getBucket(), storePath.getThumb());
	}
	
	public OSS getOssClient() {
		return ossClient;
	}

}
