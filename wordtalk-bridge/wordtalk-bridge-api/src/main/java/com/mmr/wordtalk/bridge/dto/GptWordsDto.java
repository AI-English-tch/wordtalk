package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptWords;
import lombok.Data;

import java.math.BigDecimal;

/**
 * GptWordsDto
 *
 * @author 张恩睿
 * @date 2023-06-16 21:40:12
 */
@Data
public class GptWordsDto extends GptWords {

	private BigDecimal score;
}
