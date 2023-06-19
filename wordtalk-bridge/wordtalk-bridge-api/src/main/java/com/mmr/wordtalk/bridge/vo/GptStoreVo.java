package com.mmr.wordtalk.bridge.vo;

import com.mmr.wordtalk.bridge.entity.GptStore;
import lombok.Data;

/**
 * GptStoreVo
 *
 * @author 张恩睿
 * @date 2023-06-16 22:09:30
 */
@Data
public class GptStoreVo extends GptStore {
    private Boolean queryWordsList = false;
}
