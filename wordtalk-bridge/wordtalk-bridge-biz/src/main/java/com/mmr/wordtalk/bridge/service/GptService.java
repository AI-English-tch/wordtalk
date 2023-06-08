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
    String chat(String msg);

    String chatWithContext(String username, String msg);

	R chatOnStream(String username, String msg);

	R chatWithContextOnStream(String username, String msg);
}
