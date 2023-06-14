package com.mmr.wordtalk.common.ai.core;

import com.mmr.wordtalk.common.ai.properties.AiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * AI模块的自动配置类
 *
 * @author 张恩睿
 * @date 2023-06-07 13:24:00
 */
@Import({ChatGptAutoConfiguration.class})
@EnableConfigurationProperties({AiProperties.class})
public class AiAutoConfiguration {
}
