package com.mmr.wordtalk.ai.core;

import com.mmr.wordtalk.ai.dto.Context;

import java.util.List;

/**
 * AI的消息发送策略
 *
 * @author 张恩睿
 * @date 2023-06-30 00:42:56
 */
public interface SendStrategy {

    default String send(List<Context> contextList) {
        return "模型策略不存在!";
    }

    default String streamSend(List<Context> contextList) {return "模型策略不存在!";}

}
