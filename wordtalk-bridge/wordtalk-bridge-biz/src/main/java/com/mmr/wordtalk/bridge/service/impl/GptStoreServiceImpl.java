/*
 *    Copyright (c) 2018-2025, wordtalk All rights reserved.
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
 * Author: wordtalk
 */
package com.mmr.wordtalk.bridge.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.entity.GptStoreEntity;
import com.mmr.wordtalk.bridge.entity.GptStoreWordsEntity;
import com.mmr.wordtalk.bridge.entity.GptWordsEntity;
import com.mmr.wordtalk.bridge.mapper.GptStoreMapper;
import com.mmr.wordtalk.bridge.service.GptStoreService;
import com.mmr.wordtalk.bridge.service.GptStoreWordsService;
import com.mmr.wordtalk.bridge.service.GptWordsService;
import com.mmr.wordtalk.common.core.util.MsgUtils;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
@Service
@RequiredArgsConstructor
public class GptStoreServiceImpl extends ServiceImpl<GptStoreMapper, GptStoreEntity> implements GptStoreService {

	private final GptWordsService wordsService;

	private final GptStoreWordsService storeWordsService;

	@Override
	public R importWords(Long id, List<String> wordsList) {
		//1. 查询词库是否存在
		GptStoreEntity store = this.getById(id);
		if (Objects.isNull(store)) {
			// return MsgUtils.getMessage();
			return R.failed("词库不存在");
		}
		//2. 构造完整的单词列表
		List<GptWordsEntity> wordsEntityList = createWordsEntity(wordsList);
		//3. 差量填充到词库中
		List<GptWordsEntity> newWordsEntityList = wordsEntityList.stream().filter(item -> {
			//3.1. 返回不在该词库中的新单词
			GptStoreWordsEntity storeWords = storeWordsService.getOne(Wrappers.<GptStoreWordsEntity>lambdaQuery()
					.eq(GptStoreWordsEntity::getStoreId, store.getId())
					.eq(GptStoreWordsEntity::getWordId, item.getId()));
			return Objects.isNull(storeWords);
		}).collect(Collectors.toList());
		//4. 构造要填充的关联实体
		List<GptStoreWordsEntity> collect = newWordsEntityList.stream().map(item -> {
			GptStoreWordsEntity storeWordsEntity = new GptStoreWordsEntity();
			storeWordsEntity.setStoreId(store.getId());
			storeWordsEntity.setWordId(item.getId());
			return storeWordsEntity;
		}).collect(Collectors.toList());
		//5. 异步执行导入操作
		ThreadUtil.execute(() -> {
			storeWordsService.saveBatch(collect);
		});
		return R.ok("导入成功~请等待填充");
	}

	private List<GptWordsEntity> createWordsEntity(List<String> wordsList) {
		return wordsList.stream().map(item -> {
			GptWordsEntity one = wordsService.getOne(Wrappers.<GptWordsEntity>lambdaQuery().eq(GptWordsEntity::getWord, item));
			if (Objects.isNull(one)) {
				GptWordsEntity entity = new GptWordsEntity();
				entity.setWord(item);
				wordsService.save(entity);
				return entity;
			} else {
				return one;
			}
		}).distinct().collect(Collectors.toList());
	}
}