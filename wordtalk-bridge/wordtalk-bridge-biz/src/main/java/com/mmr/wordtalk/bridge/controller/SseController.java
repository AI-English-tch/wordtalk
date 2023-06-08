package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.bridge.utils.SseEmitterUtil;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.service.WordtalkUser;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * sse连接管理
 *
 * @author 张恩睿
 * @date 2023-06-05 23:12:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
@Tag(description = "eventSource", name = "sse连接管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SseController {

	private final SseEmitterUtil emitterUtil;
    /**
     * 打开连接
     *
     * @return
     */
    @GetMapping(value = "/open", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter open() {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
		SseEmitter emitter = emitterUtil.createEmitter(username);
        return emitter;
    }

    /**
     * 关闭连接
     *
     * @return
     */
    @GetMapping("/close")
    public R close() {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
        return R.ok(emitterUtil.release(username));
    }

    /**
     * 推送文字
     *
     * @param msg
     * @return
     */
    @SneakyThrows
    @GetMapping("/push")
    public R push(@RequestParam String msg) {
		WordtalkUser user = SecurityUtils.getUser();
		String username = user.getUsername();
        SseEmitter emitter = emitterUtil.getEmitter(username);
        emitter.send(msg);
        return R.ok();
    }
}
