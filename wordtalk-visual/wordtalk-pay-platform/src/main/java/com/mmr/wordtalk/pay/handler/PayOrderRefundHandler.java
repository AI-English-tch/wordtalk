package com.mmr.wordtalk.pay.handler;

import com.mmr.wordtalk.pay.entity.PayChannel;
import com.mmr.wordtalk.pay.entity.PayRefundOrder;
import com.mmr.wordtalk.pay.entity.PayTradeOrder;

/**
 * @author 张恩睿
 * @date 2023-03-01
 * <p>
 * 退款业务
 */
public interface PayOrderRefundHandler {

	/**
	 * 准备支付参数
	 */
	default PayChannel preparePayParams() {
		return null;
	}

	/**
	 * 创建商品退款订单
	 * @param tradeOrder 交易订单
	 * @return
	 */
	PayRefundOrder createPayRefundOrder(PayRefundOrder refundOrder, PayTradeOrder tradeOrder);

	/**
	 * 调起渠道退款
	 * @param refundOrder 商品退款订单
	 * @param tradeOrder 交易订单
	 * @return obj
	 */
	Object refund(PayRefundOrder refundOrder, PayTradeOrder tradeOrder);

	/**
	 * 更新订单信息
	 * @param goodsOrder 商品订单
	 * @param tradeOrder 交易订单
	 */
	void updateOrder(Object obj, PayRefundOrder refundOrder, PayTradeOrder tradeOrder);

	/**
	 * 调用入口
	 * @param goodsOrde 商品订单
	 * @return
	 */
	Object handle(PayRefundOrder refundOrder);

}
