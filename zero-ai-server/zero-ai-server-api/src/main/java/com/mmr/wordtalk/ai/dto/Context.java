package com.mmr.wordtalk.ai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Context
 *
 * @author 张恩睿
 * @date 2023-06-29 23:00:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ai 消息对象")
public class Context {

	@Builder.Default
	@Schema(description = "消息角色")
	private String role = Role.USER.value;
	@Schema(description = "消息内容")
	private String content;

	@Getter
	@AllArgsConstructor
	public enum Role {

		SYSTEM("system"),
		USER("user"),
		ASSISTANT("assistant"),

		FUNCTION("function"),
		;
		private String value;
	}

}
