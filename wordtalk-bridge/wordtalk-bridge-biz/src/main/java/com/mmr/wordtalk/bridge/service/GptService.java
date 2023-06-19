package com.mmr.wordtalk.bridge.service;

import com.mmr.wordtalk.common.core.util.R;

/**
 * GptService
 *
 * @author 张恩睿
 * @date 2023-06-06 23:00:14
 */
public interface GptService {

    // 与gpt实现聊天的方法
    String chat(Long id, String msg, Long topicId);

    String chatWithContext(Long id, String username, String msg, Long topicId);

	R chatOnStream(Long id, String username, String msg, Long topicId);

	R chatWithContextOnStream(Long id, String username, String msg, Long topicId);

	R initContext(Long topicId);
}
