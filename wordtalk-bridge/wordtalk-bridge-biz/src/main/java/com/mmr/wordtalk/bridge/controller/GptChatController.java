package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.bridge.service.GptChatService;
import com.mmr.wordtalk.common.core.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * GPT交互接口
 *
 * @author 张恩睿
 * @date 2023-07-04 00:58:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
@Tag(description = "gpt", name = "GPT交互接口")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptChatController {

    private final GptChatService gptChatService;

    /**
     * 单次对话
     *
     * @param message 输入给AI的问题
     * @param inject  插入到prompt中的值
     * @return
     */
    @Operation(summary = "单次对话", description = "单次对话")
    @GetMapping("/send/{robotId}")
    public R send(@PathVariable Long robotId, Long bookId, String message, String inject) {
        String result = gptChatService.send(robotId, bookId, message, inject);
        return R.ok(result);
    }

    @Operation(summary = "流式对话", description = "流式对话")
    @GetMapping("/sendOnStream/{robotId}")
    public R sendOnStream(@PathVariable Long robotId, Long bookId, String message, String inject) {
        String result = gptChatService.sendOnStream(robotId, bookId, message, inject);
        return R.ok(result);
    }


}
