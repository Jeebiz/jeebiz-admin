package io.hiwepy.admin.extras.inform.emums;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.utils.DateUtils;
import org.springframework.data.redis.core.RedisKey;

public enum InformSendChannel {

	/**
	 * 站内通知
	 */
	NOTICE("notice", "站内通知",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 站内私信
	 */
	LETTER("letter", "站内私信",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 钉钉个人消息
	 */
	DINGTALK_PERSONAL("dingtalk-personal", "钉钉个人消息",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 钉钉群组消息
	 */
	DINGTALK_GROUP("dingtalk-group", "钉钉群组消息",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 钉钉机器人消息
	 */
	DINGTALK_ROBOT("dingtalk-robot",  "钉钉机器人消息",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 腾讯QQ
	 */
	QQ("qq", "腾讯QQ", (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * GoEasy推送
	 */
	GOEASY("goeasy", "GoEasy推送", (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 极光推送
	 */
	JPUSH("jpush", "极光推送",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 新浪微博
	 */
	WEIBO("weobo", "新浪微博",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 微信小程序服务消息
	 */
	WXMA_NOTICE("wxma-notice", "微信小程序:服务消息",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 微信小程序订阅消息
	 */
	WXMA_SUBSCRIBE("wxma-subscribe", "微信小程序:订阅消息",  (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	/**
	 * 微信公众号订阅消息
	 */
	WXMP_SUBSCRIBE("wxpm-subscribe", "微信公众号:订阅消息", (type, userId)->{
		return BizRedisKey.INFORM_SEND_LIMIT.getKey(type, userId);
	}),
	;
	private String key;
	private String desc;
	private BiFunction<Object, Object, String> function;

	InformSendChannel(String key, String desc, BiFunction<Object, Object, String> function) {
		this.key = key;
		this.desc = desc;
		this.function = function;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
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

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.name());
		pair.setValue(this.getDesc());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformSendChannel sendChannel : InformSendChannel.values()) {
			pairList.add(sendChannel.toPair());
		}
		return pairList;
	}

}

