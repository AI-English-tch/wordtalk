package com.mmr.wordtalk.bridge.gpt;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Component;

/**
 * GptFactory
 *
 * @author 张恩睿
 * @date 2023-06-06 23:42:59
 */
@Component
public class GptFactory {

    private final ChatGptPrototype chatGptPrototype = new ChatGptPrototype();

    public GptPrototype createGpt(String type) {
        if ("chat".equals(type)) {
            return ObjectUtil.clone(chatGptPrototype);
        } else {
            return null;
        }
    }

}
