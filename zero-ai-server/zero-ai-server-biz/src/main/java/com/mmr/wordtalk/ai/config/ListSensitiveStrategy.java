package com.mmr.wordtalk.ai.config;

import mybatis.mate.databind.ISensitiveStrategy;
import mybatis.mate.strategy.SensitiveStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 张恩睿
 * @date 2023-07-04 18:04:00
 */

public class ListSensitiveStrategy implements ISensitiveStrategy {

	private static Map<String, Function<List<String>, List<String>>> STRATEGY_FUNCTION_MAP;

	public ListSensitiveStrategy() {
		STRATEGY_FUNCTION_MAP = new HashMap<>();
	}

	public ListSensitiveStrategy addStrategy(String var1, Function<List<String>, List<String>> var2) {
		STRATEGY_FUNCTION_MAP.put(var1, var2);
		return this;
	}

	@Override
	public Map<String, Function<String, String>> getStrategyFunctionMap() {
		return null;
	}
}
