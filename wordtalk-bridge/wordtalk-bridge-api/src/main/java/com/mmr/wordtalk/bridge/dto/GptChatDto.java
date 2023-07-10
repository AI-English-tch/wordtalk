package com.mmr.wordtalk.bridge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * gpt的交互对象
 *
 * @author 张恩睿
 * @date 2023-07-04 09:24:00
 */
@Data
@Schema(description = "gpt的交互对象")
public class GptChatDto {
    /**
     * 词书的ID
     */
    @Schema(description = "词书的ID")
    private Long bookId;

    /**
     * 关联的单词
     */
    @Schema(description = "关联的单词")
    private String word;

    /**
     * 发送的消息
     */
    @Schema(description = "发送的消息")
    private String message;

    /**
     * 注入提词的内容
     */
    @Schema(description = "注入提词的内容")
    private Map<String, Object> inject;
}
