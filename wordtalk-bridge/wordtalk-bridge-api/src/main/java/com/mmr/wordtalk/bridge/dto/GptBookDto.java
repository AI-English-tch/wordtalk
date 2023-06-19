package com.mmr.wordtalk.bridge.dto;

import com.mmr.wordtalk.bridge.entity.GptBook;
import com.mmr.wordtalk.bridge.entity.GptWords;
import lombok.Data;

import java.util.List;

/**
 * GptBookDto
 *
 * @author 张恩睿
 * @date 2023-06-16 21:37:01
 */
@Data
public class GptBookDto extends GptBook {
    private List<GptWords> wordsList;
}
