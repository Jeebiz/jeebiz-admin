package io.hiwepy.admin.extras.inform.emums;

import java.util.NoSuchElementException;

import io.hiwepy.boot.api.utils.DateUtils;

public enum InformSendChannel {

	/**
	 * 站内通知
	 */
	NOTICE("notice", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "站内通知"),
	/**
	 * 站内私信
	 */
	LETTER("letter", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "站内私信"),
	/**
	 * 钉钉个人消息
	 */
	DINGTALK_PERSONAL("dingtalk-personal", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "钉钉个人消息"),
	/**
	 * 钉钉群组消息
	 */
	DINGTALK_GROUP("dingtalk-group", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "钉钉群组消息"),
	/**
	 * 钉钉机器人消息
	 */
	DINGTALK_ROBOT("dingtalk-robot", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "钉钉机器人消息"),
	/**
	 * 腾讯QQ
	 */
	QQ("qq", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "腾讯QQ"),
	/**
	 * GoEasy推送
	 */
	GOEASY("goeasy", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "GoEasy推送"),
	/**
	 * 极光推送
	 */
	JPUSH("jpush", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "极光推送"),
	/**
	 * 新浪微博
	 */
	WEIBO("weobo", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "新浪微博"),
	/**
	 * 微信小程序服务消息
	 */
	WXMA_NOTICE("wxma-notice", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "微信小程序:服务消息"),
	/**
	 * 微信小程序订阅消息
	 */
	WXMA_SUBSCRIBE("wxma-subscribe", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "微信小程序:订阅消息"),
	/**
	 * 微信公众号订阅消息
	 */
	WXMP_SUBSCRIBE("wxpm-subscribe", 5 * DateUtils.MILLIS_PER_MINUTE, DateUtils.MILLIS_PER_DAY, "微信公众号:订阅消息"),
	;
	private String code;
	private long period;
	private long expiry;
	private String desc;

	public String getCode() {

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

	private InformSendChannel(String code, long period, long expiry, String desc) {
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
		throw new NoSuchElementException("Cannot found Inform Send Channel with key '" + key + "'.");
	}

}

