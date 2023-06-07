package com.mmr.wordtalk.common.ai.chatgpt;

import com.mmr.wordtalk.common.ai.context.Context;
import com.mmr.wordtalk.common.ai.core.AiChatTemplate;
import com.mmr.wordtalk.common.ai.core.AiProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * chatGpt的自动装配类
 * @author 张恩睿
 * @date 2023-06-07 13:29:00
 */
@AllArgsConstructor
public class ChatGptAutoConfiguration {

	private final AiProperties properties;

	private final Context context;

	@Bean
	@ConditionalOnMissingBean(ChatGptTemplate.class)
	@ConditionalOnProperty(name = "ai.chatgpt.enable", havingValue = "true", matchIfMissing = true)
	public AiChatTemplate chatGptTemplate() {
		return new ChatGptTemplate(properties, context);
		// return new ChatGptTemplate();
	}
}
