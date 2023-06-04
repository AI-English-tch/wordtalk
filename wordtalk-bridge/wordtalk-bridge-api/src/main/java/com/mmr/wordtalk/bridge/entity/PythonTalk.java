package com.mmr.wordtalk.bridge.entity;

import lombok.Data;

/**
 * python后台返回的对话内容
 */
@Data
public class PythonTalk {

    private String chat;

    private String check;
}
