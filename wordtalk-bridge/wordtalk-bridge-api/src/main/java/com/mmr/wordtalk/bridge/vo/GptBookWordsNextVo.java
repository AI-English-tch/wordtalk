package com.mmr.wordtalk.bridge.vo;

import com.mmr.wordtalk.bridge.entity.GptBookWords;
import lombok.Data;

/**
 * @author 张恩睿
 * @date 2023-06-21 13:43:00
 */
@Data
public class GptBookWordsNextVo extends GptBookWords {
	// 取值参考枚举类Evaluate的name
	private String evaluate;
}
