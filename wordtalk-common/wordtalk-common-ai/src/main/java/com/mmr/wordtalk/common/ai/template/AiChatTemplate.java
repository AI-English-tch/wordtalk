package com.mmr.wordtalk.common.ai.template;

import com.mmr.wordtalk.common.ai.core.Context;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.function.Consumer;

/**
 * 对话式AI的操作模板
 *
 * @author 张恩睿
 * @date 2023-06-07 14:16:00
 */

public interface AiChatTemplate {

    String send(List<Context> contexts);

    void sendOnStream(List<Context> contexts, SseEmitter emitter, Consumer<String> complete);
}
