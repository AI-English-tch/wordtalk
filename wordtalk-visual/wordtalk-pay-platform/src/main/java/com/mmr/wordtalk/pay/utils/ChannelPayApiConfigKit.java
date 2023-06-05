package com.mmr.wordtalk.pay.utils;

import com.mmr.wordtalk.pay.entity.PayChannel;
import lombok.experimental.UtilityClass;

/**
 * @author 张恩睿
 * @date 2021/2/2
 *
 * 聚合支付配置管理
 */
@UtilityClass
public class ChannelPayApiConfigKit {

	private static final ThreadLocal<PayChannel> TL = new ThreadLocal();

	public PayChannel get() {
		return TL.get();
	}

	public void put(PayChannel channel) {
		TL.set(channel);
	}

	public void remove() {
		TL.remove();
	}

}
