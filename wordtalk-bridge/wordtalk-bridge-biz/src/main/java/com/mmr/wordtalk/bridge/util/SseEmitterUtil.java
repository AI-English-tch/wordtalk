package com.mmr.wordtalk.bridge.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * SseEmitterUtils
 * 负责创建、管理SseEmitter
 *
 * @author 张恩睿
 * @date 2023-06-06 20:50:38
 */
@Component
public class SseEmitterUtil {

	private final Map<String, SseEmitter> sseTable = Collections.synchronizedMap(new HashMap<>(10));

	public SseEmitter createEmitter(String token) {
		SseEmitter sseEmitter = sseTable.getOrDefault(token, new SseEmitter(0L));
		sseTable.put(token, sseEmitter);
		return sseEmitter;
	}

	public SseEmitter getEmitter(String token) {
		return sseTable.get(token);
	}

	public Boolean release(String token) {
		if (sseTable.containsKey(token)) {
			// 存在才需要释放
			SseEmitter sseEmitter = sseTable.remove(token);
			sseEmitter.complete();
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}


}
