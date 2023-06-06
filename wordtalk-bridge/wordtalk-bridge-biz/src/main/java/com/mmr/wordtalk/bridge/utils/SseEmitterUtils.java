package com.mmr.wordtalk.bridge.utils;

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
public class SseEmitterUtils {

    private static final Map<String, SseEmitter> sseTable = Collections.synchronizedMap(new HashMap<>(10));

    public static SseEmitter getEmitter(String username) {
        // 判断该用户是否有emitter
        if (sseTable.containsKey(username)) {
            // 已经存在
            return sseTable.get(username);
        }
        SseEmitter emitter = new SseEmitter(0L);
//        SseEmitterDecorator decorator = new SseEmitterDecorator(emitter);
        sseTable.put(username, emitter);
        return emitter;
    }

    public static void release(String username) {
        if (sseTable.containsKey(username)) {
            // 存在才需要释放
            SseEmitter sseEmitter = sseTable.remove(username);
            sseEmitter.complete();
        }
    }


}
