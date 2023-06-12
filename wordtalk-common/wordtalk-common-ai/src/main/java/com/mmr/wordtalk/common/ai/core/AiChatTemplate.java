package com.mmr.wordtalk.common.ai.core;

import com.plexpt.chatgpt.entity.chat.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 对话式AI的操作模板
 *
 * @author 张恩睿
 * @date 2023-06-07 14:16:00
 */

public interface AiChatTemplate {

	/**
	 * 初始化上下文
	 *
	 * @param key
	 * @param contentList
	 */
	void initContext(String key, List<Content> contentList);

	/**
	 * 获取上下文
	 *
	 * @param key
	 * @return
	 */
	List<Content> getContext(String key);

	/**
	 * 单次对话，整串返回
	 *
	 * @param ask
	 * @return
	 */
	String chat(String ask);

	/**
	 * 单次对话带system，整串返回
	 *
	 * @param prompt
	 * @param ask
	 * @return
	 */
	String chat(String ask, String prompt);


	/**
	 * 上下文对话，整串返回
	 *
	 * @param ask
	 * @return
	 */
	String chatWithContext(String key, String ask);

	/**
	 * 上下文对话，整串返回
	 *
	 * @param ask
	 * @return
	 */
	String chatWithContext(String key, String ask, String prompt);

	/**
	 * 单词对话，流式返回
	 *
	 * @param ask
	 * @param sseEmitter
	 * @return
	 */
	CompletableFuture<String> chatOnStream(String ask, SseEmitter sseEmitter);

	CompletableFuture<String> chatOnStream(String ask, SseEmitter sseEmitter, String prompt);

	/**
	 * 上下文对话，流式返回
	 *
	 * @param key
	 * @param ask
	 * @param sseEmitter
	 * @return
	 */
	CompletableFuture<String> chatWithContextOnStream(String key, String ask, SseEmitter sseEmitter);

	CompletableFuture<String> chatWithContextOnStream(String key, String ask, SseEmitter sseEmitter, String prompt);

}
