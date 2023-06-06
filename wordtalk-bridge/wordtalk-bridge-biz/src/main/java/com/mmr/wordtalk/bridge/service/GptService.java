package com.mmr.wordtalk.bridge.service;

/**
 * GptService
 *
 * @author 张恩睿
 * @date 2023-06-06 23:00:14
 */
public interface GptService {

    // 与gpt实现聊天的方法
    Boolean chat(String msg);

    // 获取聊天上下文的方法
    Object getContext();
}
