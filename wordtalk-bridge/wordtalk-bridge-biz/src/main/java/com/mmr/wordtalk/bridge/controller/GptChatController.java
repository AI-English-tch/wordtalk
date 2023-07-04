package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.bridge.dto.GptChatDto;
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
     * @param chatDto 对话交互对象
     * @return
     */
    @Operation(summary = "单次对话", description = "单次对话")
    @GetMapping("/send/{robotId}")
    public R send(@PathVariable Long robotId, GptChatDto chatDto) {
        String result = gptChatService.send(robotId, chatDto);
        return R.ok(result);
    }

    @Operation(summary = "流式对话", description = "流式对话")
    @GetMapping("/sendOnStream/{robotId}")
    public R sendOnStream(@PathVariable Long robotId, GptChatDto chatDto) {
        String result = gptChatService.sendOnStream(robotId, chatDto);
        return R.ok(result);
    }


}
