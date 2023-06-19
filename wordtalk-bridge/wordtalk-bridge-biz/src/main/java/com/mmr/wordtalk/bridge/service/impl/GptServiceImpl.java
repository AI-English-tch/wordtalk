package com.mmr.wordtalk.bridge.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.bridge.entity.GptHistoryEntity;
import com.mmr.wordtalk.bridge.entity.GptPromptEntity;
import com.mmr.wordtalk.bridge.entity.GptTopicEntity;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import com.mmr.wordtalk.bridge.service.GptPromptService;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.bridge.service.GptTopicService;
import com.mmr.wordtalk.bridge.utils.SseEmitterUtil;
import com.mmr.wordtalk.common.ai.context.ContextProperties;
import com.mmr.wordtalk.common.ai.core.AiChatTemplate;
import com.mmr.wordtalk.common.ai.core.Content;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

	private final ContextProperties contextProperties;

	private final GptHistoryService historyService;

	private final GptTopicService gptTopicService;

	@Override
	public String chat(Long id, String msg, Long topicId) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		if (prompt == null) {
			return chatTemplate.chat(msg);
		}
		return chatTemplate.chat(msg, prompt.getContent());
	}

	@Override
	public String chatWithContext(Long id, String username, String msg, Long topicId) {
		GptPromptEntity prompt = gptPromptService.getById(id);
		if (prompt == null) {
			return chatTemplate.chatWithContext(username, msg);
		}
		return chatTemplate.chatWithContext(username, msg, prompt.getContent());
	}

	@Override
	public R chatOnStream(Long id, String username, String msg, Long topicId) {
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
	public R chatWithContextOnStream(Long id, String username, String msg, Long topicId) {
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

	@Override
	public R initContext(Long topicId) {
		String username = SecurityUtils.getUser().getUsername();
		// 1.获取上下文的大小
		Integer contextSize = contextProperties.getSize();
		// 2.查找对应话题
		GptTopicEntity topic = gptTopicService.getById(topicId);
		if (Objects.isNull(topic)) {
			return R.failed("话题不存在");
		}
		// 3.查找话题下的聊天记录
		Page page = new Page<>(1, contextSize, false);
		Page<GptHistoryEntity> result = historyService.page(page, Wrappers.<GptHistoryEntity>lambdaQuery()
				.eq(GptHistoryEntity::getTopicId, topic.getId())
				.orderByDesc(GptHistoryEntity::getUpdateTime));
		// 4.获取聊天记录
		List<Content> collect = result.getRecords().stream().map(item -> new Content(item.getRole(), item.getContent())).collect(Collectors.toList());
		chatTemplate.initContext(username, collect);
		return R.ok("初始化成功");
	}

}
