package com.mmr.wordtalk.bridge.utils;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SseEmitter的链接池
 *
 * @author 张恩睿
 * @date 2023-06-05 23:29:29
 */

public class SseEmitterPool {
    private static final int MAX_POOL_SIZE = 10;

    private List<SseEmitter> sseEmitterList;
    private static SseEmitterPool instance;

    private SseEmitterPool() {
        sseEmitterList = Collections.synchronizedList(new ArrayList<>(MAX_POOL_SIZE));
        initializePool();
    }

    public static SseEmitterPool getInstance() {
        if (instance == null) {
            synchronized (ConnectPool.class) {
                if (instance == null) {
                    instance = new SseEmitterPool();
                }
            }
        }
        return instance;
    }

    private void initializePool() {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            sseEmitterList.add(createSseEmitter());
        }
    }

    private SseEmitter createSseEmitter() {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(() -> {
            System.out.println("连接完成了");
        });
        return sseEmitter;
    }

    public SseEmitter getSseEmitter() {
        if (sseEmitterList.isEmpty()) {
            System.out.println("No available connections. Creating a new one.");
            return createSseEmitter();
        }
        SseEmitter sseEmitter = sseEmitterList.remove(0);
        System.out.println("Using an existing connection from the pool.");
        return sseEmitter;
    }

    public void releaseSseEmitter(SseEmitter sseEmitter) {
        if (sseEmitterList.size() < MAX_POOL_SIZE) {
            sseEmitterList.add(sseEmitter);
            System.out.println("Connection released and added back to the pool.");
        } else {
            System.out.println("Connection pool is full. Connection not added back to the pool.");
            sseEmitter.complete();
        }
    }
}
