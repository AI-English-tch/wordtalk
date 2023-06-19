package com.mmr.wordtalk.common.ai.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 张恩睿
 * @date 2023-06-07 13:30:00
 */
@Data
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

	private Integer contextSize;

	/**
	 * 设置代理信息
	 */
	private ProxyProperties proxy;

	/**
	 * 设置chatgpt的配置
	 */
	private ChatGptProperties chatGpt;

}
