package com.mmr.wordtalk.bridge.controller;

import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.common.core.exception.ErrorCodes;
import com.mmr.wordtalk.common.core.util.MsgUtils;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.service.WordtalkUser;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
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

	@GetMapping("/chat")
	public R chat(@RequestParam String msg) {
		return R.ok(gptService.chat(msg));
	}

	@GetMapping("/chatWithContext")
	public R chatWithContext(@RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		if (Objects.isNull(user)) {
			String message = "用户为空，不应该出现";
			return R.failed(message);
		}
		String username = user.getUsername();
		return R.ok(gptService.chatWithContext(username, msg));
	}

	@GetMapping("/chatOnStream")
	public R chatOnStream(@RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
		return gptService.chatOnStream(username, msg);
	}

	@GetMapping("/chatWithContextOnStream")
	public R chatWithContextOnStream(@RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
		gptService.chatWithContextOnStream(username,msg);
	}

}
