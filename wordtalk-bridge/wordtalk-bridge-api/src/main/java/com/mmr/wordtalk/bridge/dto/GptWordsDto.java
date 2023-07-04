package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptWords;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单词的Dto对象
 *
 * @author 张恩睿
 * @date 2023-06-16 21:40:12
 */
@Data
@Schema(description = "单词的Dto对象")
public class GptWordsDto extends GptWords {

	@Schema(description = "单词的打分")
	private BigDecimal score;
}
