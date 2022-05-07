package io.hiwepy.admin.extras.inform.emums;

import java.util.NoSuchElementException;

import io.hiwepy.boot.api.utils.DateUtils;

public enum InformSendChannel {

	/**
	 * 站内通知
	 */
	NOTICE(1, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "站内通知"),
	/**
	 * 站内私信
	 */
	LETTER(2, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "站内私信"), 
	/**
	 * 钉钉消息
	 */
	DINGTALK(3, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "钉钉消息"), 
	/**
	 * 腾讯QQ
	 */
	QQ(11, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "腾讯QQ"), 
	/**
	 * GoEasy推送
	 */
	GOEASY(14, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "GoEasy推送"),
	/**
	 * 极光推送
	 */
	JPUSH(13, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "极光推送"),
	/**
	 * 新浪微博
	 */
	WEIBO(12, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "新浪微博"),
	/**
	 * 微信小程序
	 */
	WXMA(4, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "微信小程序"), 
	/**
	 * 微信公众号
	 */
	WXMP(10, 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "微信公众号"), 
	;
    private Integer code;
    private long period;
    private long expiry;
    private String desc;

    public Integer getCode() {
    	
        return code;
    }
    
    public long getPeriod() {
		return period;
	}
    
    public long getExpiry() {
		return expiry;
	}

    public String getDesc() {
        return desc;
    }

	private InformSendChannel(Integer code, long period, long expiry, String desc) {
        this.code = code;
        this.period  = period;
        this.expiry  = expiry;
        this.desc = desc;
    }

	public boolean equals(InformSendChannel channel) {
		return this.compareTo(channel) == 0;
	}

	public boolean equals(String type) {
		return this.compareTo(InformSendChannel.valueOfIgnoreCase(type)) == 0;
	}

	public static InformSendChannel valueOfIgnoreCase(String key) {
		for (InformSendChannel channel : InformSendChannel.values()) {
			if (channel.name().equals(key)) {
				return channel;
			}
		}
		throw new NoSuchElementException("Cannot found Payment Channel with key '" + key + "'.");
	}

}
