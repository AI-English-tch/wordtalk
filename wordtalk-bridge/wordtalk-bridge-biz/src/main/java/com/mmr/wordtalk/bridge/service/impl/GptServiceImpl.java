package com.mmr.wordtalk.bridge.service.impl;

import com.mmr.wordtalk.bridge.entity.GptPromptEntity;
import com.mmr.wordtalk.bridge.service.GptPromptService;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.bridge.utils.SseEmitterUtil;
import com.mmr.wordtalk.common.ai.core.AiChatTemplate;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * GptServiceImpl
 *
 * @author 张恩睿
 * @date 2023-06-06 23:01:22
 */
@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {
	private final AiChatTemplate chatTemplate;

	private final SseEmitterUtil emitterUtil;

	private final GptPromptService gptPromptService;

	@Override
	public String chat(Long id, String msg) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		if (prompt == null) {
			return chatTemplate.chat(msg);
		}
		return chatTemplate.chat(msg, prompt.getContent());
	}

	@Override
	public String chatWithContext(Long id, String username, String msg) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		if (prompt == null) {
			return chatTemplate.chatWithContext(username, msg);
		}
		return chatTemplate.chatWithContext(username, msg, prompt.getContent());
	}

	@Override
	public R chatOnStream(Long id, String username, String msg) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		// 获取用户的SSE链接
		SseEmitter emitter = emitterUtil.getEmitter(username);
		if (Objects.nonNull(emitter)) {
			CompletableFuture<String> future = null;
			if (prompt == null) {
				future = chatTemplate.chatOnStream(msg, emitter);
			} else {
				future = chatTemplate.chatOnStream(msg, emitter, prompt.getContent());
			}
			future.thenAccept(result -> {
				System.out.println("异步等待chat的返回：" + result);
			});
			return R.ok(Boolean.TRUE);
		}
		return R.failed("用户还未建立SSE连接");
	}

	@Override
	public R chatWithContextOnStream(Long id, String username, String msg) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		// 获取用户的SSE链接
		SseEmitter emitter = emitterUtil.getEmitter(username);
		if (Objects.nonNull(emitter)) {
			CompletableFuture<String> future = null;
			if (prompt == null) {
				future = chatTemplate.chatWithContextOnStream(username, msg, emitter);
			} else {
				future = chatTemplate.chatWithContextOnStream(username, msg, emitter, prompt.getContent());
			}
			future.thenAccept(result -> {
				System.out.println("异步等待chat的返回：" + result);
			});
			return R.ok(Boolean.TRUE);
		}
		return R.failed("用户还未建立SSE连接");
	}

}
