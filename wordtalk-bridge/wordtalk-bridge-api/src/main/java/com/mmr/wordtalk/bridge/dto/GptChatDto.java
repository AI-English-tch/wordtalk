package com.mmr.wordtalk.bridge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * gpt的交互对象
 *
 * @author 张恩睿
 * @date 2023-07-04 09:24:00
 */
@Data
@Schema(description = "gpt的交互对象")
public class GptChatDto {
	@Schema(description = "词书的ID")
	private Long bookId;
	@Schema(description = "发送的消息")
	private String message;
	@Schema(description = "注入提词的内容")
	private String inject;
}
