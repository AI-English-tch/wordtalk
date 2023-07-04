package com.mmr.wordtalk.ai.dto;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-07-02 13:42:00
 */
@Data
@Schema(description = "AI交互对象")
public class SendDto {

	@Schema(description = "交互系统")
	private String system;

	@Schema(description = "上下文列表")
	private List<Context> contextList;

	@Schema(description = "Ai参数")
	private JSONObject params;

}
