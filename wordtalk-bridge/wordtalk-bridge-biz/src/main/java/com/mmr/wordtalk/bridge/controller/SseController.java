package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

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

    private final List<SseEmitter> sseEmitterList = new ArrayList<>(5);

    /**
     * 打开连接
     *
     * @return
     */
    @GetMapping(value = "/open", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    @Inner(value = false)
    public SseEmitter open() {


        return null;
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
        SseEmitter emitter = sseEmitterList.get(0);
        emitter.send(msg);
        return R.ok();
    }


}
