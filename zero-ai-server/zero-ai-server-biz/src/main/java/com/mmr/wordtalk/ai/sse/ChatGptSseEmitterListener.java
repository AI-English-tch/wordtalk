package com.mmr.wordtalk.ai.sse;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mmr.wordtalk.ai.core.ChatGptSendStrategy;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author 张恩睿
 * @date 2023-06-30 10:24:00
 */
@Slf4j
@RequiredArgsConstructor
public class ChatGptSseEmitterListener extends EventSourceListener {

    /**
     * 回复流式消息的组件，此处是SSE
     */
    private final SseEmitter sseEmitter;

    /**
     * SSE的触发ID
     */
    private final String id;

    /**
     * SSE的触发事件
     */
    private final String event;

    @Getter
    protected CompletableFuture<String> future = new CompletableFuture<>();
    @Getter
    protected String lastMessage = "";


    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        // do nothing
    }

    @SneakyThrows
    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        // do nothing
        if (data.equals("[DONE]")) {
            future.complete(lastMessage);
            return;
        }

        ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
        // 读取Json
        String text = ChatGptSendStrategy.parse(response.getChoices(), true);
        log.info(Marker.ANY_MARKER, text);
        if (text != null) {
            lastMessage += text;
            SseEmitter.SseEventBuilder sb = SseEmitter.event();
            if (StrUtil.isNotBlank(id)) sb.id(id);
            if (StrUtil.isNotBlank(event)) sb.name(event);
            sb.data(text);
            sseEmitter.send(sb);
        }
    }

    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable throwable, Response response) {

        try {
            log.error("Stream connection error: {}", throwable);

            String responseText = "";

            if (Objects.nonNull(response)) {
                responseText = response.body().string();
            }

            log.error("response：{}", responseText);

            String forbiddenText = "Your access was terminated due to violation of our policies";

            if (StrUtil.contains(responseText, forbiddenText)) {
                log.error("Chat session has been terminated due to policy violation");
                log.error("检测到号被封了");
            }

            String overloadedText = "That model is currently overloaded with other requests.";

            if (StrUtil.contains(responseText, overloadedText)) {
                log.error("检测到官方超载了，赶紧优化你的代码，做重试吧");
            }
        } catch (Exception e) {
            log.warn("onFailure error:{}", e);
            // do nothing

        } finally {
            eventSource.cancel();
        }
    }


    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        // do nothing
    }
}
