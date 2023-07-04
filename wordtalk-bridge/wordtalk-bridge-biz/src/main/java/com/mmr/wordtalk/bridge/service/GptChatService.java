package com.mmr.wordtalk.bridge.service;

import com.mmr.wordtalk.bridge.dto.GptChatDto;

/**
 * GptChatService
 *
 * @author 张恩睿
 * @date 2023-07-04 01:17:07
 */
public interface GptChatService {
    String send(Long robotId, GptChatDto chatDto);

    String sendOnStream(Long robotId, GptChatDto chatDto);
}
