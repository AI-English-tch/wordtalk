package com.mmr.wordtalk.bridge.controller;

import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.bridge.service.GptService;
import com.mmr.wordtalk.common.core.util.R;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/chat")
    public R chat(String msg) {
        if (StrUtil.isBlank(msg)) {
            return R.failed("输入内容非法");
        }
        return R.ok(gptService.chat(msg));
    }


}
