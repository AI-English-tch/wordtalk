package com.mmr.wordtalk.common.ai.context;

import com.mmr.wordtalk.common.ai.core.AiProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 张恩睿
 * @date 2023-06-07 19:22:00
 */
@AllArgsConstructor
public class ContextAutoConfiguration {

	private final AiProperties properties;

	private final RedisTemplate redisTemplate;

	@Bean
	@ConditionalOnMissingBean(RedisContext.class)
	@ConditionalOnProperty(name = "ai.context.redis.enable",havingValue = "true",matchIfMissing = true)
	public Context redisContext() {
		return new RedisContext(properties.getContext().getSize(),redisTemplate);
	}
}
