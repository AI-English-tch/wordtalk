package com.mmr.wordtalk.bridge.service.impl;

import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.bridge.utils.SseEmitterUtil;
import com.mmr.wordtalk.common.ai.core.AiChatTemplate;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;

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

	@Override
	public String chat(String msg) {
		return chatTemplate.chat(msg);
	}

	@Override
	public String chatWithContext(String username, String msg) {
		return chatTemplate.chatWithContext(username, msg);
	}

	@Override
	public R chatOnStream(String username, String msg) {
		// 获取用户的SSE链接
		SseEmitter emitter = emitterUtil.getEmitter(username);
		if (Objects.nonNull(emitter)) {
			String chat = chatTemplate.chatOnStream(msg, emitter);
			System.out.println("chart返回值：" + chat);
			return R.ok();
		}
		return R.failed("用户还未建立SSE连接");
	}

	@Override
	public R chatWithContextOnStream(String username, String msg) {
		// 获取用户的SSE链接
		SseEmitter emitter = emitterUtil.getEmitter(username);
		if (Objects.nonNull(emitter)) {
			String chat = chatTemplate.chatWithContextOnStream(username, msg, emitter);
			System.out.println("chart返回值：" + chat);
			return R.ok();
		}
		return R.failed("用户还未建立SSE连接");
	}


}
