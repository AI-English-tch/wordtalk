package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptStore;
import com.mmr.wordtalk.bridge.entity.GptWords;
import lombok.Data;

import java.util.List;

/**
 * GptStoreDto
 *
 * @author 张恩睿
 * @date 2023-06-16 21:39:44
 */
@Data
public class GptStoreDto extends GptStore {
    private List<GptWords> wordsList;
}
