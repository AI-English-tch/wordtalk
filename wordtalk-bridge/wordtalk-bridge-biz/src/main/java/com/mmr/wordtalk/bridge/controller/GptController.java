package com.mmr.wordtalk.bridge.controller;

import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.common.core.exception.ErrorCodes;
import com.mmr.wordtalk.common.core.util.MsgUtils;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.service.WordtalkUser;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 大语言模型管理
 *
 * @author 张恩睿
 * @date 2023-06-06 22:55:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
@Tag(description = "大语言模型管理", name = "大语言模型管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptController {

	private final GptService gptService;

	/**
	 * 单次聊天
	 *
	 * @param id
	 * @param msg
	 * @return
	 */
	@Operation(summary = "单次聊天", description = "单次聊天")
	@GetMapping("/chat/{id}")
	public R chat(@PathVariable Long id, @RequestParam String msg) {
		return R.ok(gptService.chat(id, msg));
	}

	/**
	 * 上下文聊天
	 *
	 * @param id
	 * @param msg
	 * @return
	 */
	@Operation(summary = "上下文聊天", description = "上下文聊天")
	@GetMapping("/chatWithContext/{id}")
	public R chatWithContext(@PathVariable Long id, @RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		if (Objects.isNull(user)) {
			String message = "用户为空，不应该出现";
			return R.failed(message);
		}
		String username = user.getUsername();
		return R.ok(gptService.chatWithContext(id, username, msg));
	}

	/**
	 * 单次流式聊天
	 *
	 * @param id
	 * @param msg
	 * @return
	 */
	@Operation(summary = "单次流式聊天", description = "单次流式聊天")
	@GetMapping("/chatOnStream/{id}")
	public R chatOnStream(@PathVariable Long id, @RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
		return gptService.chatOnStream(id, username, msg);
	}

	/**
	 * 上下文流式聊天
	 *
	 * @param id
	 * @param msg
	 * @return
	 */
	@Operation(summary = "上下文流式聊天", description = "上下文流式聊天")
	@GetMapping("/chatWithContextOnStream/{id}")
	public R chatWithContextOnStream(@PathVariable Long id, @RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
		return gptService.chatWithContextOnStream(id, username, msg);
	}

	@Operation(summary = "初始化上下文",description = "初始化上下文")
	@GetMapping("/initContext")
	public

}
