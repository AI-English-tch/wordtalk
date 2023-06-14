package com.mmr.wordtalk.bridge.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.mmr.wordtalk.bridge.entity.GptHistoryEntity;
import com.mmr.wordtalk.bridge.entity.GptPromptEntity;
import com.mmr.wordtalk.bridge.entity.GptTalkEntity;
import com.mmr.wordtalk.bridge.entity.GptTopicEntity;
import com.mmr.wordtalk.bridge.service.GptChatService;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import com.mmr.wordtalk.bridge.service.GptPromptService;
import com.mmr.wordtalk.bridge.service.GptTopicService;
import com.mmr.wordtalk.bridge.util.SseEmitterUtil;
import com.mmr.wordtalk.common.ai.core.Context;
import com.mmr.wordtalk.common.ai.role.ChatGptRole;
import com.mmr.wordtalk.common.ai.template.AiChatTemplate;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author 张恩睿
 * @date 2023-06-14 11:52:00
 */
@Service
@RequiredArgsConstructor
public class GptChatServiceImpl implements GptChatService {

	private final AiChatTemplate chatTemplate;

	private final GptPromptService promptService;

	private final GptTopicService topicService;

	private final GptHistoryService historyService;

	private final SseEmitterUtil sseEmitterUtil;
	@Override

	public GptTalkEntity talk(GptTalkEntity gptTalkEntity) {
		GptTalkEntity result = new GptTalkEntity();
		// 创建|读取话题
		GptTopicEntity topic = createTopic(gptTalkEntity);
		// 通过话题构建上下文
		List<Context> contextList = createContextByTopic(topic);
		// 用户本次对话添加到上下文中
		Context userContext = new Context(ChatGptRole.USER, gptTalkEntity.getContent());
		contextList.add(userContext);
		// 清理上下文中的null值
		contextList.removeIf(Objects::isNull);
		// 获取chat组件的回答
		String reply = chatTemplate.send(contextList);

		// 异步将回答加入到历史查询中
		Long topicId = topic.getId();
		String username = SecurityUtils.getUser().getUsername();
		ThreadUtil.execute(() -> {
			GptHistoryEntity userHistory = createUserHistory(topicId, username, userContext.getText());
			GptHistoryEntity assistantHistory = createAssistantHistory(topicId, username, reply);
			historyService.saveTalk(topicId, userHistory, assistantHistory);
		});

		// 填充返回值
		result.setTopicId(topic.getId());
		result.setPromptId(topic.getPromptId());
		result.setContent(reply);
		return result;
	}

	@Override
	public GptTalkEntity talkOnStream(GptTalkEntity gptTalkEntity) {
		GptTalkEntity result = new GptTalkEntity();
		// 创建|读取话题
		GptTopicEntity topic = createTopic(gptTalkEntity);
		// 通过话题构建上下文
		List<Context> contextList = createContextByTopic(topic);

		Context userContext = new Context(ChatGptRole.USER, gptTalkEntity.getContent());
		contextList.add(userContext);
		// 清理上下文中的null值
		contextList.removeIf(Objects::isNull);

		String username = SecurityUtils.getUser().getUsername();
		SseEmitter emitter = sseEmitterUtil.getEmitter(username);
		Long topicId = topic.getId();

		chatTemplate.sendOnStream(contextList, emitter, complete(topicId, username, userContext.getText()));

		result.setTopicId(topic.getId());
		result.setPromptId(topic.getPromptId());
		return result;
	}

	/**
	 * 流式对话异步处理chat返回结果函数
	 *
	 * @param topicId
	 * @param username
	 * @param message
	 * @return
	 */
	private Consumer<String> complete(Long topicId, String username, String message) {
		return reply -> {
			GptHistoryEntity userHistory = createUserHistory(topicId, username, message);
			GptHistoryEntity assistantHistory = createAssistantHistory(topicId, username, reply);
			historyService.saveTalk(topicId, userHistory, assistantHistory);
		};
	}

	/**
	 * 构建话题，有则读取，无则新建
	 *
	 * @param gptTalkEntity
	 * @return
	 */
	private GptTopicEntity createTopic(GptTalkEntity gptTalkEntity) {
		GptTopicEntity topic = null;
		if (Objects.nonNull(gptTalkEntity.getTopicId())) {
			// 话题不为空则继续上次的话题，通过话题的题词+历史消息构建上下文对象
			topic = topicService.getById(gptTalkEntity.getTopicId());
			topic.setUpdateTime(LocalDateTime.now());
		} else {
			// 话题为空是新启话题，通过gptTalkEntity的promptId构造system语句即可，没有历史消息
			// 创建一个新的话题
			topic = new GptTopicEntity();
			topic.setTitle("None Title");
			topic.setPromptId(gptTalkEntity.getPromptId());
		}
		// 保存或更新topic的值
		topicService.saveOrUpdate(topic);

		return topic;
	}

	/**
	 * 创建用户的历史交谈值
	 *
	 * @param topicId
	 * @param username
	 * @param message
	 * @return
	 */
	private GptHistoryEntity createUserHistory(Long topicId, String username, String message) {
		GptHistoryEntity userEntity = new GptHistoryEntity();
		userEntity.setRole(ChatGptRole.ASSISTANT);
		userEntity.setTopicId(topicId);
		userEntity.setCreateBy(username);
		userEntity.setContent(message);
		return userEntity;
	}

	/**
	 * 创建助手的历史交谈值
	 *
	 * @param topicId
	 * @param username
	 * @param reply
	 * @return
	 */
	private GptHistoryEntity createAssistantHistory(Long topicId, String username, String reply) {
		GptHistoryEntity assistantEntity = new GptHistoryEntity();
		assistantEntity.setRole(ChatGptRole.ASSISTANT);
		assistantEntity.setTopicId(topicId);
		assistantEntity.setCreateBy(username);
		assistantEntity.setContent(reply);
		return assistantEntity;
	}


	/**
	 * 通过topic创建上下文对象
	 *
	 * @param topic
	 * @return
	 */
	private List<Context> createContextByTopic(GptTopicEntity topic) {
		List<Context> contextList = new ArrayList<>();

		if (Objects.nonNull(topic.getPromptId())) {
			Context systemContext = createSystemContext(topic.getPromptId());
			contextList.add(systemContext);
		}

		List<Context> historyContext = createHistoryContext(topic.getId());
		contextList.addAll(historyContext);
		return contextList;
	}

	/**
	 * 抽取方法，构建历史记录的上下文
	 *
	 * @param topicId
	 * @return
	 */
	private List<Context> createHistoryContext(Long topicId) {
		List<GptHistoryEntity> historyEntityList = historyService.queryHistoryByTopicId(topicId);
		return historyEntityList.stream().map(item -> new Context(item.getRole(), item.getContent())).collect(Collectors.toList());
	}

	/**
	 * 抽取方法，构建系统设置的上下文
	 *
	 * @param promptId
	 * @return
	 */
	private Context createSystemContext(Long promptId) {
		// 查找prompt
		GptPromptEntity prompt = promptService.getById(promptId);
		if (Objects.nonNull(prompt)) {
			Context context = new Context(ChatGptRole.SYSTEM, prompt.getContent());
			return context;
		}
		return null;
	}
}
