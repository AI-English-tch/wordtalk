package com.mmr.wordtalk.common.ai.core;

import com.mmr.wordtalk.common.ai.chatgpt.ChatGptProperties;
import com.mmr.wordtalk.common.ai.context.ContextProperties;
import com.mmr.wordtalk.common.ai.proxy.ProxyProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 张恩睿
 * @date 2023-06-07 13:30:00
 */
@Data
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

	/**
	 * 设置代理信息
	 */
	private ProxyProperties proxy;

	/**
	 * 上下文配置
	 */
	private ContextProperties context;

	/**
	 * 设置chatgpt的配置
	 */
	private ChatGptProperties chatGpt;

}
