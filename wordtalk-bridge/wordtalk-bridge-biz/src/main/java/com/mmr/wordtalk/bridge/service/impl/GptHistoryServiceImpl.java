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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.entity.GptHistory;
import com.mmr.wordtalk.bridge.mapper.GptHistoryMapper;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import com.mmr.wordtalk.common.ai.properties.AiProperties;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 对话历史
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
@Service
@RequiredArgsConstructor
public class GptHistoryServiceImpl extends ServiceImpl<GptHistoryMapper, GptHistory> implements GptHistoryService {

	private final AiProperties properties;

	private final RedisTemplate redisTemplate;

	private final String redisKey = "context::{username}::{topicId}";


	@Override
	public void saveTalk(Long topicId, GptHistory userEntity, GptHistory assistantEntity) {
		String username = userEntity.getCreateBy();
		ListOperations<String, GptHistory> opsForList = redisTemplate.opsForList();
		String key = createKey(topicId, username);
		// redis中加入数据
		opsForList.rightPushAll(key, userEntity, assistantEntity);
		// 续费一天的过期时间
		redisTemplate.expire(key, 1L, TimeUnit.DAYS);
		this.saveBatch(Arrays.asList(userEntity, assistantEntity));
	}

	@Override
	public List<GptHistory> queryHistoryByTopicId(Long topicId) {
		String username = SecurityUtils.getUser().getUsername();
		// 从redis中尝试获取历史上下文数据
		String key = createKey(topicId, username);
		ListOperations<String, GptHistory> opsForList = redisTemplate.opsForList();
		if (redisTemplate.hasKey(key)) {
			// key存在则取redis中的数据
			List<GptHistory> historyList = opsForList.range(key, 0, -1);
			if (historyList.size() > properties.getContextSize()) {
				opsForList.trim(key, historyList.size() - properties.getContextSize(), -1);
				historyList = historyList.subList(historyList.size() - properties.getContextSize(), historyList.size());
			}
			return historyList;
		} else {
			// key不存在则取mysql中的数据
			Page<GptHistory> page = Page.of(1, properties.getContextSize(), false);
			LambdaQueryWrapper<GptHistory> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(GptHistory::getTopicId, topicId);
			wrapper.eq(GptHistory::getCreateBy, username);
			wrapper.orderByDesc(GptHistory::getCreateTime);
			List<GptHistory> historyList = this.page(page, wrapper).getRecords();
			if (!historyList.isEmpty()) {
				// 存入redis一份
				opsForList.rightPushAll(key, historyList);
				// 设置1天的过期时间
				redisTemplate.expire(key, 1L, TimeUnit.DAYS);
			}
			return historyList;
		}
	}

	private String createKey(Long topicId, String username) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("topicId", topicId);
		String key = StrUtil.format(redisKey, params);
		return key;
	}


}