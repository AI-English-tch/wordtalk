package com.mmr.wordtalk.ai.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mmr.wordtalk.ai.bo.ChatGptModelParams;
import com.mmr.wordtalk.ai.dto.Context;
import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.ai.entity.AiModel;
import com.mmr.wordtalk.ai.sse.ChatGptSseEmitterListener;
import com.mmr.wordtalk.ai.sse.SseEmitterUtil;
import com.mmr.wordtalk.common.security.service.WordtalkUser;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.api.Api;
import com.plexpt.chatgpt.entity.chat.ChatChoice;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * ChatGpt的发送策略
 *
 * @author 张恩睿
 * @date 2023-06-30 00:49:22
 */
@Data
public class ChatGptSendStrategy implements SendStrategy {

    private final ChatCompletion chatCompletion;

    private final ChatGPT chatGPT;

    private final ChatGPTStream chatGPTStream;

    public ChatGptSendStrategy(AiModel model, JSONObject params) {
        Proxy globalProxy = null;
        try {
            globalProxy = SpringUtil.getBean("globalProxy", Proxy.class);
        } catch (Exception e) {
            // 未开启全局代理
            globalProxy = Proxy.NO_PROXY;
        }

        // 构建模型默认参数 ---- 先采用全默认参数，若模型内有自定义参数，则将自定义覆盖之，若本次调用有自定义参数，则再覆盖之
        ChatGptModelParams defaultParams = new ChatGptModelParams();
        if (!JSONUtil.isNull(model.getParam())) {
            ChatGptModelParams source = model.getParam().toBean(ChatGptModelParams.class);
            BeanUtil.copyProperties(source, defaultParams);
        }

        if (!JSONUtil.isNull(params)) {
            ChatGptModelParams source = params.toBean(ChatGptModelParams.class);
            BeanUtil.copyProperties(source, defaultParams);
        }

        Proxy proxy = model.getEnable_proxy() ? globalProxy : Proxy.NO_PROXY;

        // 根据模型构造策略
        this.chatCompletion = ChatCompletion.builder()
                .model(model.getVersion())
                .temperature(defaultParams.getTemperature())
                .topP(defaultParams.getTopP())
                .frequencyPenalty(defaultParams.getFrequencyPenalty())
                .presencePenalty(defaultParams.getPresencePenalty())
                .maxTokens(defaultParams.getMaxTokens())
                .n(defaultParams.getN())
                .messages(new ArrayList<>())
                .build();

        this.chatGPT = ChatGPT.builder()
                .apiHost(StrUtil.isNotBlank(model.getHost()) ? model.getHost() : Api.DEFAULT_API_HOST)
                .timeout(300)
                .proxy(proxy)
                .apiKeyList(model.getKeyList())
                .build();

        this.chatGPTStream = ChatGPTStream.builder()
                .apiHost(StrUtil.isNotBlank(model.getHost()) ? model.getHost() : Api.DEFAULT_API_HOST)
                .timeout(300)
                .proxy(proxy)
                .apiKeyList(model.getKeyList())
                .build();
    }

    public static List<Message> cover(List<Context> contextList) {
        return contextList.stream().map(item -> Message.builder().role(item.getRole()).content(item.getContent()).build()).collect(Collectors.toList());
    }

    public static String parse(List<ChatChoice> choices, boolean isStream) {
        return choices.stream().map(item -> {
            Message message = null;
            if (isStream) {
                message = item.getDelta();
            } else {
                message = item.getMessage();
            }
            return message.getContent() != null ? message.getContent() : "";
        }).collect(Collectors.joining("\n"));
    }


    @Override
    public String send(SendDto sendDto) {
        ChatGPT gpt = this.chatGPT.init();
        this.chatCompletion.setMessages(cover(sendDto.getContextList()));
        ChatCompletionResponse response = gpt.chatCompletion(this.chatCompletion);

        // 此处可获取token总量进行扣费
//        Usage usage = response.getUsage();

        return parse(response.getChoices(), false);
    }

    @SneakyThrows
    @Override
    public String streamSend(SendDto sendDto) {
        ChatGPTStream gpt = this.chatGPTStream.init();

        this.chatCompletion.setMessages(cover(sendDto.getContextList()));
        this.chatCompletion.setStream(true);

        //  获取当前用户的登陆信息
        WordtalkUser user = SecurityUtils.getUser();

        SseEmitter emitter = SseEmitterUtil.openEmitter(user.getUsername());

        ChatGptSseEmitterListener listener = new ChatGptSseEmitterListener(emitter, sendDto.getId(), sendDto.getEvent());

        gpt.streamChatCompletion(this.chatCompletion, listener);

        CompletableFuture<String> future = listener.getFuture();

        return future.get();
    }
}
