package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.bridge.entity.GptTalk;
import com.mmr.wordtalk.bridge.service.GptChatService;
import com.mmr.wordtalk.bridge.util.SseEmitterUtil;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.annotation.Inner;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * chat对话管理
 *
 * @author 张恩睿
 * @date 2023-06-14 11:36:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Tag(description = "chat", name = "chat对话管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptChatController {

	private final SseEmitterUtil emitterUtil;

	private final GptChatService chatService;

	/**
	 * 打开EventSource连接
	 */
	@Operation(summary = "打开EventSource连接", description = "打开EventSource连接")
	@GetMapping(value = "/open", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public SseEmitter open() {
		String username = SecurityUtils.getUser().getUsername();
		return emitterUtil.createEmitter(username);
	}

	/**
	 * 关闭EventSource连接
	 *
	 * @return
	 */
	@Operation(summary = "关闭EventSource连接", description = "关闭EventSource连接")
	@GetMapping("/close")
	public R close() {
		String username = SecurityUtils.getUser().getUsername();
		return R.ok(emitterUtil.release(username));
	}

	/**
	 * 推送文字到EventSource
	 *
	 * @param msg
	 * @return
	 */
	@SneakyThrows
	@Operation(summary = "推送文字到EventSource", description = "推送文字到EventSource")
	@GetMapping("/push")
	@Inner
	public R push(@RequestParam String msg, @RequestParam String token) {
		SseEmitter emitter = emitterUtil.getEmitter(token);
		emitter.send(msg);
		return R.ok("发送成功");
	}

	/**
	 * 单次对话
	 *
	 * @param gptTalk
	 * @return
	 */
	@Operation(summary = "单次对话", description = "单次对话")
	@PostMapping("/talk")
	public R talk(@RequestBody GptTalk gptTalk) {
		return R.ok(chatService.talk(gptTalk));
	}


	/**
	 * 流式对话
	 *
	 * @param gptTalk
	 * @return
	 */
	@Operation(summary = "流式对话", description = "流式对话")
	@PostMapping("/talkOnStream")
	public R talkOnStream(@RequestBody GptTalk gptTalk) {
		return R.ok(chatService.talkOnStream(gptTalk));
	}


}
