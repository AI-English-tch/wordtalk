package com.mmr.wordtalk.ai.config;

import cn.hutool.core.util.StrUtil;
import mybatis.mate.databind.ISensitiveStrategy;
import mybatis.mate.strategy.SensitiveStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class SensitiveStrategyConfig {

	/**
	 * 注入脱敏策略
	 */
	@Bean
	public ISensitiveStrategy sensitiveStrategy() {
		// 自定义 testStrategy 类型脱敏处理
		return new ListSensitiveStrategy().addStrategy("keyList", list -> list.stream().map(t -> StrUtil.replace(t, 3, t.length() - 3, "*")).collect(Collectors.toList()));
	}
}