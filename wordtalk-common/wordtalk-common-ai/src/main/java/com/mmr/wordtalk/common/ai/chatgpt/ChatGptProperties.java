package com.mmr.wordtalk.common.ai.chatgpt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-06-07 14:09:00
 */
@Data
@ConfigurationProperties(prefix = "chatgpt")
public class ChatGptProperties {

	/**
	 * 上下文数据大小
	 */
	private Integer contextSize;

	/**
	 * apiKey的列表
	 */
	private List<String> apiKeys;
}
