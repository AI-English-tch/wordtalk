/*
 *    Copyright (c) 2018-2025, wordtalk All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: wordtalk
 */
package com.mmr.wordtalk.pay.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.pay.entity.PayRefundOrder;
import com.mmr.wordtalk.pay.handler.PayOrderRefundHandler;
import com.mmr.wordtalk.pay.mapper.PayRefundOrderMapper;
import com.mmr.wordtalk.pay.service.PayRefundOrderService;
import com.mmr.wordtalk.pay.utils.RefundNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 退款
 *
 * @author 张恩睿
 * @date 2019-05-28 23:58:11
 */
@Service
@RequiredArgsConstructor
public class PayRefundOrderServiceImpl extends ServiceImpl<PayRefundOrderMapper, PayRefundOrder>
		implements PayRefundOrderService {

	private final Map<String, PayOrderRefundHandler> refundHandlerMap;

	/**
	 * 退款操作
	 * @param refundOrder refundOrder
	 * @return true/false
	 */
	@Override
	public Boolean refund(PayRefundOrder refundOrder) {
		String channelId = refundOrder.getChannelId();
		// 判断用哪个通道
		if (StrUtil.containsAnyIgnoreCase(channelId, "ali")) {
			refundHandlerMap.get(RefundNameEnum.ALIPAY.getName()).handle(refundOrder);
		}
		else {
			throw new UnsupportedOperationException();
		}
		return null;
	}

}
