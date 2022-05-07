package io.hiwepy.admin.extras.inform.strategy;

import java.math.BigDecimal;

import io.hiwepy.admin.extras.inform.bo.InformSendBO;
import io.hiwepy.admin.extras.inform.bo.InformSendResult;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

/**
 * 推送消息
 */
public interface InformSendStrategy {

	public static final String PAYMENT_SUCCESS = "success";
	public static final String PAYMENT_FAIL = "fail";
	public static final BigDecimal HUNDRED = new BigDecimal(100);

	/**
	 * 获取支付渠道
	 * @return
	 */
	InformSendChannel getChannel();

	<R extends InformSendResult, O extends InformSendBO> R send(O sendBo, Class<R> rtType) throws Exception;

}
