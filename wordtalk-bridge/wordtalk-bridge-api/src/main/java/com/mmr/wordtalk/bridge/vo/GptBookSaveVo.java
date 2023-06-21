package com.mmr.wordtalk.bridge.vo;

import com.mmr.wordtalk.bridge.entity.GptBook;
import lombok.Data;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-06-19 13:55:00
 */
@Data
public class GptBookSaveVo extends GptBook {
	private Long storeId;
	private List<Long> wordsIdList;
}
