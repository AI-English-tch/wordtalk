package com.mmr.wordtalk.common.ai.template;

import com.mmr.wordtalk.common.ai.core.Context;
import com.mmr.wordtalk.common.ai.properties.AiProperties;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.api.Api;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 张恩睿
 * @date 2023-06-07 14:35:00
 */
@Data
@AllArgsConstructor
public class ChatGptTemplate implements AiChatTemplate {

    private AiProperties properties;

    private ChatGPT build() {
        ChatGPT.ChatGPTBuilder builder = ChatGPT.builder()
                .timeout(3000l)
                .apiHost(Api.DEFAULT_API_HOST)
                .apiKeyList(properties.getChatGpt().getApiKeys());
        if (properties.getProxy().getEnable()) {
            Proxy proxy = Proxys.http(properties.getProxy().getHost(), properties.getProxy().getPort());
            builder.proxy(proxy);
        }
        return builder.build().init();
    }

    private ChatGPTStream buildOfStream() {
        ChatGPTStream.ChatGPTStreamBuilder builder = ChatGPTStream.builder()
                .timeout(3000l)
                .apiHost(Api.DEFAULT_API_HOST)
                .apiKeyList(properties.getChatGpt().getApiKeys());
        if (properties.getProxy().getEnable()) {
            Proxy proxy = Proxys.http(properties.getProxy().getHost(), properties.getProxy().getPort());
            builder.proxy(proxy);
        }
        return builder.build().init();
    }

    private Function<Context, Message> convert() {
        return item -> Message.builder()
                .role(item.getRole())
                .content(item.getText())
                .build();
    }

    private String parse(ChatCompletionResponse response) {
        return response.getChoices().get(0).getMessage().getContent();
    }

    private String parseOfStream(ChatCompletionResponse response) {
        return response.getChoices().get(0).getDelta().getContent();
    }


    @Override
    public String send(List<Context> contexts) {
        ChatGPT gpt = this.build();
        List<Message> messageList = contexts.stream().map(convert()).collect(Collectors.toList());
        ChatCompletionResponse response = gpt.chatCompletion(messageList);
        return parse(response);
    }

    @Override
    public void sendOnStream(List<Context> contexts, SseEmitter emitter, Consumer<String> complete) {
        ChatGPTStream gpt = this.buildOfStream();
        SseStreamListener listener = new SseStreamListener(emitter);
        listener.setOnComplate(complete);
        List<Message> messageList = contexts.stream().map(convert()).collect(Collectors.toList());
        gpt.streamChatCompletion(messageList, listener);
    }
}
