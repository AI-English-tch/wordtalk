package com.mmr.wordtalk.ai.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ModelType
 *
 * @author 张恩睿
 * @date 2023-06-30 01:29:06
 */
@Getter
@AllArgsConstructor
public enum ModelType {

    CHATGPT("chatgpt"),
    ;

    private String value;
}
