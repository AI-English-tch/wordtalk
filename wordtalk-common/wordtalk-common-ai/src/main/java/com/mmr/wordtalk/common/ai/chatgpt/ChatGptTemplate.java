package com.mmr.wordtalk.common.ai.chatgpt;

import com.mmr.wordtalk.common.ai.context.Context;
import com.mmr.wordtalk.common.ai.core.AiChatTemplate;
import com.mmr.wordtalk.common.ai.core.AiProperties;
import com.mmr.wordtalk.common.ai.core.Content;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.api.Api;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张恩睿
 * @date 2023-06-07 14:35:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptTemplate implements AiChatTemplate {

	private AiProperties properties;

	private Context context;

	private ChatGPT buildGpt() {
		ChatGPT.ChatGPTBuilder builder = ChatGPT.builder()
				.timeout(3000l)
				.apiHost(Api.DEFAULT_API_HOST)
				.apiKeyList(properties.getChatGpt().getApiKeys());
		if (properties.getProxy().getEnable()) {
			Proxy proxy = Proxys.http(properties.getProxy().getHost(), properties.getProxy().getPort());
			builder.proxy(proxy);
		}
		return builder.build().init();
	}

	private ChatGPTStream buildGptStream() {
		ChatGPTStream.ChatGPTStreamBuilder builder = ChatGPTStream.builder()
				.timeout(3000l)
				.apiHost(Api.DEFAULT_API_HOST)
				.apiKeyList(properties.getChatGpt().getApiKeys());
		if (properties.getProxy().getEnable()) {
			Proxy proxy = Proxys.http(properties.getProxy().getHost(), properties.getProxy().getPort());
			builder.proxy(proxy);
		}
		return builder.build().init();
	}

	private List<Message> createMessageList(List<Content> contentList) {
		return contentList.stream().map(item ->
				Message.builder()
						.role(item.getRole())
						.content(item.getText())
						.build()).collect(Collectors.toList());
	}

	@Override
	public String chat(String ask) {
		ChatGPT chatGPT = buildGpt();
		return chatGPT.chat(ask);
	}

	@Override
	public String chatWithContext(String key, String ask) {
		// 推送到上下文
		context.push(key, new Content("user", ask));
		// 获取当前上下文数组
		List<Content> contentList = context.getContext(key);

		List<Message> messageList = createMessageList(contentList);

		ChatGPT chatGPT = buildGpt();
		ChatCompletionResponse response = chatGPT.chatCompletion(messageList);
		Message message = response.getChoices().get(0).getMessage();
		// 将chat的回答也放置于上下文
		context.push(key, new Content("assistant", message.getContent()));
		return message.toString();
	}

	@Override
	public void chatOnStream(String ask, SseEmitter sseEmitter) {
		ChatGPTStream chatStream = buildGptStream();
		SseStreamListener listener = new SseStreamListener(sseEmitter);
		Message message = Message.of(ask);
		chatStream.streamChatCompletion(Arrays.asList(message), listener);
	}

	@Override
	public void chatWithContextOnStream(String key, String ask, SseEmitter sseEmitter) {
		// 推送到上下文
		context.push(key, new Content("user", ask));
		// 获取当前上下文数组
		List<Content> contentList = context.getContext(key);

		ChatGPTStream chatStream = buildGptStream();
		SseStreamListener listener = new SseStreamListener(sseEmitter);
		listener.setOnComplate(result -> {
			// 将chat的回答存入上下文
			context.push(key, new Content("assistant", result));
		});
		List<Message> messageList = createMessageList(contentList);

		chatStream.streamChatCompletion(messageList, listener);

	}


}
