package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.annotation.Inner;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.net.Proxy;
import java.util.Arrays;

/**
 * 测试接口
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

	@GetMapping("/hello")
	@Inner(false)
	public R hello() {
		return R.ok("Hello Lin~");
	}

	private SseEmitter sseEmitter = null;

	@SneakyThrows
	@GetMapping(value = "/sse/subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	@Inner(false)
	@CrossOrigin("*")
	public SseEmitter subscribe() {
		// 超时时间设置为3s，用于演示客户端自动重连
//		SseEmitter sseEmitter = new SseEmitter(30000L);
		sseEmitter = new SseEmitter(30000L);
		// 设置前端的重试时间为1s
		sseEmitter.send(SseEmitter.event().reconnectTime(1000).data("连接成功"));
		sseEmitter.onTimeout(() -> {
			System.out.println("超时了");
		});
		sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
		return sseEmitter;
	}

	@GetMapping("/sse/push")
	@Inner(false)
	public R push(@RequestParam String msg) throws IOException {
		sseEmitter.send(msg);
		return R.ok();
	}

	@GetMapping("/chat/sse")
	@Inner(false)
	public SseEmitter streamEvents(@RequestParam String prompt) {
		// 不需要代理的话，注销此行
		Proxy proxy = Proxys.http("127.0.0.1", 7890);
		ChatGPTStream chatGPTStream = ChatGPTStream.builder()
				.timeout(900)
				.apiKey("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")
				.proxy(proxy)
				.apiHost("https://api.openai.com/")
				.build()
				.init();
		SseEmitter sseEmitter = new SseEmitter(-1L);
		SseStreamListener listener = new SseStreamListener(sseEmitter);
		Message message = Message.of(prompt);
		listener.setOnComplate(msg -> {
			//回答完成，可以做一些事情
			System.out.println("------回答完成------");
			System.out.println(msg);
		});
		chatGPTStream.streamChatCompletion(Arrays.asList(message), listener);
		return sseEmitter;
	}


}
