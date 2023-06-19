package com.mmr.wordtalk.common.ai.core;

import com.mmr.wordtalk.common.ai.properties.AiProperties;
import com.mmr.wordtalk.common.ai.template.AiChatTemplate;
import com.mmr.wordtalk.common.ai.template.ChatGptTemplate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 张恩睿
 * @date 2023-06-07 13:29:00
 */
@AllArgsConstructor
public class ChatGptAutoConfiguration {

    private final AiProperties properties;

    @Bean
    @ConditionalOnMissingBean(ChatGptTemplate.class)
    @ConditionalOnProperty(name = "ai.chatgpt.enable", havingValue = "true", matchIfMissing = true)
    public AiChatTemplate chatGptTemplate() {
        return new ChatGptTemplate(properties);
    }
}
