package com.mmr.wordtalk.common.ai.context;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 张恩睿
 * @date 2023-06-07 19:20:00
 */
@Data
@ConfigurationProperties(prefix = "context")
public class ContextProperties {
	/**
	 * 上下文大小
	 */
	private Integer size;
}
