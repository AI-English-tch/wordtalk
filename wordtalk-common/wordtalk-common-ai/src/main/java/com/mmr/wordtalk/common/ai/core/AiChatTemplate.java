package com.mmr.wordtalk.common.ai.core;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

/**
 * 对话式AI的操作模板
 *
 * @author 张恩睿
 * @date 2023-06-07 14:16:00
 */

public interface AiChatTemplate {

	/**
	 * 单次对话无上下文功能
	 *
	 * @param ask
	 * @return
	 */
	String chat(String ask);

	/**
	 * 传入上下文对象，返回ai的值
	 *
	 * @param ask
	 * @return
	 */
	String chatWithContext(String key, String ask);

	CompletableFuture<String> chatOnStream(String ask, SseEmitter sseEmitter);

	CompletableFuture<String> chatWithContextOnStream(String key, String ask, SseEmitter sseEmitter);

}
