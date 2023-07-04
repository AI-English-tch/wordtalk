package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptStore;
import com.mmr.wordtalk.bridge.entity.GptWords;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 词库的Dto对象
 *
 * @author 张恩睿
 * @date 2023-06-16 21:39:44
 */
@Data
@Schema(description = "词库的Dto对象")
public class GptStoreDto extends GptStore {
	@Schema(description = "单词列表")
	private List<GptWords> wordsList;
}
