package com.mmr.wordtalk.bridge.service;

/**
 * GptChatService
 *
 * @author 张恩睿
 * @date 2023-07-04 01:17:07
 */
public interface GptChatService {
    String send(Long robotId, Long bookId, String message, String inject);

    String sendOnStream(Long robotId, Long bookId, String message, String inject);
}
