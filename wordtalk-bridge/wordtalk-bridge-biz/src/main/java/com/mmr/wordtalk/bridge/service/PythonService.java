package com.mmr.wordtalk.bridge.service;

import com.mmr.wordtalk.bridge.entity.PythonTalk;

import java.util.List;

public interface PythonService {
    // 设置词书的接口
    Boolean setWords(List<String> words);

    // 发送对话的接口
    PythonTalk sendTalk(String talk);
}
