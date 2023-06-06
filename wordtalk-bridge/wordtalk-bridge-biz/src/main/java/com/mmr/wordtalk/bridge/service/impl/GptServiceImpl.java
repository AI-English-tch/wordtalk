package com.mmr.wordtalk.bridge.service.impl;

import com.mmr.wordtalk.bridge.gpt.GptFactory;
import com.mmr.wordtalk.bridge.gpt.GptPrototype;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.bridge.utils.SseEmitterUtils;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;
import java.util.List;

/**
 * GptServiceImpl
 *
 * @author 张恩睿
 * @date 2023-06-06 23:01:22
 */
@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

    private final RedisTemplate redisTemplate;

    private final GptFactory gptFactory;

    @Override
    public Boolean chat(String msg) {
        // 获取用户的sse连接
        String username = SecurityUtils.getUser().getUsername();
        SseEmitter emitter = SseEmitterUtils.getEmitter(username);

        GptPrototype prototype = gptFactory.createGpt("chat");
        ChatGPTStream chatGPTStream = prototype.getGPTStream();

        // 构建流式监听器
        SseStreamListener listener = new SseStreamListener(emitter);
        // 获取用户的上下文聊天对象
        Message message = Message.of(msg);
        chatGPTStream.streamChatCompletion(Arrays.asList(message), listener);

        listener.setOnComplate(result -> {
            // chat的返回结果，作为上下文和聊天记录储存起来

        });
        return Boolean.TRUE;
    }

    @Override
    public Object getContext() {
        String username = SecurityUtils.getUser().getUsername();
        List<String> range = redisTemplate.opsForList().range(username, 0, -1);
        // 上下文工厂，根据使用的机器人不同，构建出不同的上下文

        return null;
    }
}
