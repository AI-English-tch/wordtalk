package com.mmr.wordtalk.ai.core;

import com.mmr.wordtalk.ai.dto.SendDto;

/**
 * AI的消息发送策略
 *
 * @author 张恩睿
 * @date 2023-06-30 00:42:56
 */
public interface SendStrategy {

    default String send(SendDto sendDto) {
        return "模型策略不存在!";
    }

    default String streamSend(SendDto sendDto) {
        return "模型策略不存在!";
    }

}
