package com.mmr.wordtalk.ai.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

/**
 * SseEmitterUtils
 * 负责创建、管理SseEmitter
 *
 * @author 张恩睿
 * @date 2023-06-06 20:50:38
 */
public class SseEmitterUtil {
    private final static Map<String, SseEmitter> sseTable = Collections.synchronizedMap(new HashMap<>());

    /**
     * 获取当前用户的Listener对象
     *
     * @param system
     * @param token
     * @return
     */
    public static SseEmitter openEmitter(String system, String token) {
        String key = system + "::" + token;
        if (sseTable.containsKey(key)) {
            return sseTable.get(key);
        }
        SseEmitter emitter = new SseEmitter(0L);
        sseTable.put(key, emitter);
        return emitter;
    }

    /**
     * 关闭当前用户的Listener对象
     *
     * @param token
     */
    public static void closeEmitter(String system, String token) {
        String key = system + "::" + token;
        if (sseTable.containsKey(key)) {
            SseEmitter emitter = sseTable.remove(key);
            emitter.complete();
        }
    }
}
