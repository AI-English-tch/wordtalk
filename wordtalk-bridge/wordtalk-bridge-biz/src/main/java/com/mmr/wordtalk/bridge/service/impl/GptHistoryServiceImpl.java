/*
 *    Copyright (c) 2018-2025, zero All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: zero
 */
package com.mmr.wordtalk.bridge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.ai.dto.Context;
import com.mmr.wordtalk.bridge.entity.GptHistory;
import com.mmr.wordtalk.bridge.mapper.GptHistoryMapper;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 对话历史
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
@Service
@RequiredArgsConstructor
public class GptHistoryServiceImpl extends ServiceImpl<GptHistoryMapper, GptHistory> implements GptHistoryService {

    private final RedisTemplate redisTemplate;

    private final String redisKey = "context::{system}::{bookId}";


    @Override
    public List<Context> getHistoryContext(GptHistory history, Integer size) {
        // 从redis中尝试获取历史上下文数据
        String key = createKey(history.getBookId(), history.getSystem());
        ListOperations<String, GptHistory> opsForList = redisTemplate.opsForList();
        List<GptHistory> historyList = new ArrayList<>(0);
        if (redisTemplate.hasKey(key)) {
            // key存在则取redis中的数据
            historyList = opsForList.range(key, 0, -1);
            if (historyList.size() > size) {
                opsForList.trim(key, historyList.size() - size, -1);
                historyList = historyList.subList(historyList.size() - size, historyList.size());
            }
        } else {
            Page<GptHistory> page = Page.of(1, size, false);
            LambdaQueryWrapper<GptHistory> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(GptHistory::getSystem, history.getSystem());
            wrapper.eq(GptHistory::getBookId, history.getBookId());
            wrapper.eq(GptHistory::getWord, history.getWord());
            wrapper.orderByDesc(GptHistory::getCreateTime);
            historyList = this.page(page, wrapper).getRecords();
            if (!historyList.isEmpty()) {
                // 存入redis一份
                opsForList.rightPushAll(key, historyList);
                // 设置1天的过期时间
                redisTemplate.expire(key, 1L, TimeUnit.DAYS);
            }
        }
        // 将GptHistory集合转化为上下文返回
        return cover(historyList);
    }

    @Override
    public void saveContext(GptHistory history, List<Context> needSave) {
        List<GptHistory> historyList = revers(history, needSave);
        // 保存到mysql中
        this.saveBatch(historyList);
        String key = createKey(history.getBookId(), history.getSystem());
        ListOperations<String, GptHistory> opsForList = redisTemplate.opsForList();
        // redis中加入数据
        opsForList.rightPushAll(key, historyList);
        // 续费一天的过期时间
        redisTemplate.expire(key, 1L, TimeUnit.DAYS);
    }

    private List<Context> cover(List<GptHistory> historyList) {
        return historyList.stream().map(item ->
                        Context.builder()
                                .role(item.getRole())
                                .content(item.getContent())
                                .build())
                .collect(Collectors.toList());
    }

    private List<GptHistory> revers(GptHistory history, List<Context> contextList) {
        return contextList.stream().map(item -> {
            GptHistory temp = BeanUtil.copyProperties(history, GptHistory.class);
            temp.setRole(item.getRole());
            temp.setContent(item.getContent());
            return temp;
        }).collect(Collectors.toList());
    }

    private String createKey(Long bookId, String system) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("system", system);
        String key = StrUtil.format(redisKey, params);
        return key;
    }


}