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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.dto.GptWordsDto;
import com.mmr.wordtalk.bridge.entity.GptBookWords;
import com.mmr.wordtalk.bridge.entity.GptWords;
import com.mmr.wordtalk.bridge.mapper.GptBookWordsMapper;
import com.mmr.wordtalk.bridge.service.GptBookService;
import com.mmr.wordtalk.bridge.service.GptBookWordsService;
import com.mmr.wordtalk.bridge.service.GptWordsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 词书--单词关联表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:06:27
 */
@Service
public class GptBookWordsServiceImpl extends ServiceImpl<GptBookWordsMapper, GptBookWords> implements GptBookWordsService {

    @Resource
    @Lazy
    private GptWordsService wordsService;

    @Resource
    @Lazy
    private GptBookService bookService;

    @Override
    public List<GptWords> queryWordsList(Wrapper<GptBookWords> wrapper) {
        List<GptBookWords> list = this.list(wrapper);
        List<Long> collect = list.stream().map(GptBookWords::getWordId).distinct().collect(Collectors.toList());
		if (collect.isEmpty()) {
			return new ArrayList<>(0);
		}
		return wordsService.list(Wrappers.<GptWords>lambdaQuery().in(GptWords::getId, collect));
    }

	@Override
	public List<GptWordsDto> queryWordsDtoList(LambdaQueryWrapper<GptBookWords> wrapper) {
		List<GptWordsDto> wordsDtoList = baseMapper.dtoList(wrapper);
		return wordsDtoList;
	}
}