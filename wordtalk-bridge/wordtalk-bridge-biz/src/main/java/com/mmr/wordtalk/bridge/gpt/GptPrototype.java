package com.mmr.wordtalk.bridge.gpt;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;

/**
 * GptPrototype
 *
 * @author 张恩睿
 * @date 2023-06-06 23:48:27
 */
public interface GptPrototype extends Cloneable {

    ChatGPTStream getGPTStream();

    ChatGPT getGPT();
}
