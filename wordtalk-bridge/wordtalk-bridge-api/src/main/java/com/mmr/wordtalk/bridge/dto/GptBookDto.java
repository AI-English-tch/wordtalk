package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptBook;
import com.mmr.wordtalk.bridge.entity.GptWords;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 词书Dto对象
 *
 * @author 张恩睿
 * @date 2023-06-16 21:37:01
 */
@Data
@Schema(description = "词书Dto对象")
public class GptBookDto extends GptBook {
	@Schema(description = "单词列表")
	private List<GptWords> wordsList;
}
