package com.mmr.wordtalk.bridge.vo;

import com.mmr.wordtalk.bridge.entity.GptBook;
import lombok.Data;

/**
 * GptBookVo
 *
 * @author 张恩睿
 * @date 2023-06-16 21:42:59
 */
@Data
public class GptBookVo extends GptBook {

    private Boolean queryWordsList = false;
}
