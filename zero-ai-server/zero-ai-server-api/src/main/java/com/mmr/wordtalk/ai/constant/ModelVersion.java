package com.mmr.wordtalk.ai.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 张恩睿
 * @date 2023-06-30 18:07:00
 */
@Getter
@AllArgsConstructor
public enum ModelVersion {
	/**
	 * gpt-3.5-turbo
	 */
	GPT_3_5_TURBO("gpt-3.5-turbo"),
	GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
	GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
	/**
	 * 临时模型，不建议使用
	 */
	GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
	/**
	 * GPT4.0
	 */
	GPT_4("gpt-4"),
	/**
	 * 临时模型，不建议使用
	 */
	GPT_4_0314("gpt-4-0314"),
	/**
	 * 支持函数
	 */
	GPT_4_0613("gpt-4-0613"),
	/**
	 * GPT4.0 超长上下文
	 */
	GPT_4_32K("gpt-4-32k"),
	/**
	 * GPT4.0 超长上下文
	 */
	GPT_4_32K_0613("gpt-4-32k-0613"),
	/**
	 * 临时模型，不建议使用
	 */
	GPT_4_32K_0314("gpt-4-32k-0314"),
	;
	private String name;
}
