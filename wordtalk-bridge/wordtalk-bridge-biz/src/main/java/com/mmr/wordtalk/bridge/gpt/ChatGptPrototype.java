package com.mmr.wordtalk.bridge.gpt;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatGptPrototype
 * chatGpt的原型类
 *
 * @author 张恩睿
 * @date 2023-06-06 23:17:23
 */
public class ChatGptPrototype implements GptPrototype {

    private final Proxy proxy = Proxys.http("127.0.0.1", 7890);

    private final List<String> apiKeys = new ArrayList<String>() {{
        add("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");
    }};

    private final String host = "https://api.openai.com/";

    private final ChatGPTStream chatGPTStream;

    private final ChatGPT chatGPT;

    public ChatGptPrototype() {
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(900)
                .apiKeyList(apiKeys)
                .proxy(proxy)
                .apiHost(host)
                .build()
                .init();

        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(900)
                .apiKeyList(apiKeys)
                .proxy(proxy)
                .apiHost(host)
                .build()
                .init();

        this.chatGPTStream = chatGPTStream;
        this.chatGPT = chatGPT;
    }

    @Override
    public ChatGPTStream getGPTStream() {
        return this.chatGPTStream;
    }

    @Override
    public ChatGPT getGPT() {
        return this.chatGPT;
    }
}
