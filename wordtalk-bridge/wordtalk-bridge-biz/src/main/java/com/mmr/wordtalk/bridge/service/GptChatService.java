package com.mmr.wordtalk.bridge.service;


import com.mmr.wordtalk.bridge.entity.GptTalkEntity;

/**
 * @author 张恩睿
 * @date 2023-06-14 11:38:00
 */

public interface GptChatService {
	// 单次对话的接口
	GptTalkEntity talk(GptTalkEntity gptTalkEntity);

	GptTalkEntity talkOnStream(GptTalkEntity gptTalkEntity);
}
