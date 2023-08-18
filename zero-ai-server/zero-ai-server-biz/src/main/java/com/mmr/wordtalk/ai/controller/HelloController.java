package com.mmr.wordtalk.ai.controller;

import cn.hutool.core.collection.CollUtil;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import com.mmr.wordtalk.common.websocket.distribute.MessageDO;
import com.mmr.wordtalk.common.websocket.distribute.RedisMessageDistributor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张恩睿
 * @date 2023-08-18 10:17:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
@Tag(description = "测试接口", name = "测试接口")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class HelloController {

	private final RedisMessageDistributor messageDistributor;

	/**
	 * ws测试接口
	 *
	 * @param msg
	 * @return
	 */
	@Operation(summary = "ws测试接口", description = "ws测试接口")
	@GetMapping("websocket")
	public R websocket(@RequestParam String msg) {
		Long userId = SecurityUtils.getUser().getId();
		// websocket 发送消息
		MessageDO messageDO = new MessageDO();
		messageDO.setNeedBroadcast(Boolean.FALSE);
		// 给目标用户ID
		messageDO.setSessionKeys(CollUtil.newArrayList(userId));
		messageDO.setMessageText(msg);
		messageDistributor.distribute(messageDO);
		return R.ok("发送成功");
	}

}
