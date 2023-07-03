package com.mmr.wordtalk.ai.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SystemEnum
 *
 * @author 张恩睿
 * @date 2023-07-03 22:18:51
 */
@Getter
@AllArgsConstructor
public enum SystemEnum {

    MASTER("master"),
    SERVANT("servant");

    private String system;
}
