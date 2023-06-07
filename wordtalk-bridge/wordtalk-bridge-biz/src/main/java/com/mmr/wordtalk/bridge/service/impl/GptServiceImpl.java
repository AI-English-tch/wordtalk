package com.mmr.wordtalk.bridge.service.impl;

import com.mmr.wordtalk.bridge.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * GptServiceImpl
 *
 * @author 张恩睿
 * @date 2023-06-06 23:01:22
 */
@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

	@Override
	public Boolean chat(String msg) {
		return null;
	}

	@Override
	public Object getContext() {
		return null;
	}
}
