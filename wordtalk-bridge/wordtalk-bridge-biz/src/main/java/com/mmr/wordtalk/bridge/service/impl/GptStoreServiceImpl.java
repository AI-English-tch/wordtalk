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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.dto.GptBookDto;
import com.mmr.wordtalk.bridge.dto.GptStoreDto;
import com.mmr.wordtalk.bridge.entity.*;
import com.mmr.wordtalk.bridge.mapper.GptStoreMapper;
import com.mmr.wordtalk.bridge.service.GptStoreService;
import com.mmr.wordtalk.bridge.service.GptStoreWordsService;
import com.mmr.wordtalk.bridge.service.GptWordsService;
import com.mmr.wordtalk.bridge.vo.GptBookVo;
import com.mmr.wordtalk.bridge.vo.GptStoreVo;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
@Service
@RequiredArgsConstructor
public class GptStoreServiceImpl extends ServiceImpl<GptStoreMapper, GptStore> implements GptStoreService {

	private final GptWordsService wordsService;

	private final GptStoreWordsService storeWordsService;

	private List<GptWords> createWordsEntity(List<String> wordsList) {
		// 查询出在单词列表中的单词
		return wordsList.stream().map(item -> {
			GptWords one = wordsService.getOne(Wrappers.<GptWords>lambdaQuery().eq(GptWords::getWord, item));
			if (Objects.isNull(one)) {
				GptWords entity = new GptWords();
				entity.setWord(item);
				wordsService.save(entity);
				return entity;
			} else {
				return one;
			}
		}).distinct().collect(Collectors.toList());
	}

	private Function<GptStore, GptStoreDto> fillFunction(GptStoreVo vo) {
		return item -> {
			GptStoreDto dto = BeanUtil.copyProperties(item, GptStoreDto.class);
			fill(dto, vo);
			return dto;
		};
	}

	private void fill(GptStoreDto dto, GptStoreVo vo) {
		if (vo.getQueryWordsList()) {
			LambdaQueryWrapper<GptStoreWords> wrapper = Wrappers.<GptStoreWords>lambdaQuery().eq(GptStoreWords::getStoreId, dto.getId());
			List<GptWords> wordsList = storeWordsService.queryWordsList(wrapper);
			dto.setWordsList(wordsList);
		}
	}

	@Override
	public R importWords(Long id, List<String> wordsList) {
		//1. 查询词库是否存在
		GptStore store = this.getById(id);
		if (Objects.isNull(store)) {
			// return MsgUtils.getMessage();
			return R.failed("词库不存在");
		}
		// 异步执行导入操作
		ThreadUtil.execute(() -> {
			//2. 构造完整的单词列表
			List<GptWords> wordsEntityList = createWordsEntity(wordsList);
			//3. 差量填充到词库中
			List<GptWords> newWordsEntityList = wordsEntityList.stream().filter(item -> {
				//3.1. 返回不在该词库中的新单词
				GptStoreWords storeWords = storeWordsService.getOne(Wrappers.<GptStoreWords>lambdaQuery()
						.eq(GptStoreWords::getStoreId, store.getId())
						.eq(GptStoreWords::getWordId, item.getId()));
				return Objects.isNull(storeWords);
			}).collect(Collectors.toList());
			//4. 构造要填充的关联实体
			List<GptStoreWords> collect = newWordsEntityList.stream().map(item -> {
				GptStoreWords storeWordsEntity = new GptStoreWords();
				storeWordsEntity.setStoreId(store.getId());
				storeWordsEntity.setWordId(item.getId());
				return storeWordsEntity;
			}).collect(Collectors.toList());

			storeWordsService.saveBatch(collect);
		});
		return R.ok("导入成功~请等待填充");
	}

	@Override
	public IPage queryPage(Page page, GptStoreVo vo) {
		GptStore query = BeanUtil.copyProperties(vo, GptStore.class);
		LambdaQueryWrapper<GptStore> wrapper = new LambdaQueryWrapper<>(query);

		Page<GptStore> selectPage = this.page(page, wrapper);
		List<GptStoreDto> collect = selectPage.getRecords().stream().map(fillFunction(vo)).collect(Collectors.toList());

		Page result = BeanUtil.copyProperties(selectPage, Page.class, "records");
		result.setRecords(collect);
		return result;
	}

	@Override
	public List queryList(Page page, GptStoreVo vo) {
		GptStore query = BeanUtil.copyProperties(vo, GptStore.class);
		LambdaQueryWrapper<GptStore> wrapper = new LambdaQueryWrapper<>(query);
		List<GptStore> list = this.list(wrapper);
		return list.stream().map(fillFunction(vo)).collect(Collectors.toList());
	}

	@Override
	public GptStore detail(Long id, GptStoreVo vo) {
		GptStore source = this.getById(id);
		GptStoreDto dto = BeanUtil.copyProperties(source, GptStoreDto.class);
		fill(dto, vo);
		return dto;
	}


}