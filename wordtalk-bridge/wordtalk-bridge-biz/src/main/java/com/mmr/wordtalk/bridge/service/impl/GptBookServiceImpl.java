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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.dto.GptBookDto;
import com.mmr.wordtalk.bridge.entity.GptBook;
import com.mmr.wordtalk.bridge.entity.GptBookWords;
import com.mmr.wordtalk.bridge.entity.GptWords;
import com.mmr.wordtalk.bridge.mapper.GptBookMapper;
import com.mmr.wordtalk.bridge.service.GptBookService;
import com.mmr.wordtalk.bridge.service.GptBookWordsService;
import com.mmr.wordtalk.bridge.vo.GptBookVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户自己的词书
 *
 * @author 张恩睿
 * @date 2023-06-11 20:00:03
 */
@Service
@RequiredArgsConstructor
public class GptBookServiceImpl extends ServiceImpl<GptBookMapper, GptBook> implements GptBookService {

    private final GptBookWordsService bookWordsService;

    private Function<GptBook, GptBookDto> fillFunction(GptBookVo vo) {
        return item -> {
            GptBookDto dto = BeanUtil.copyProperties(item, GptBookDto.class);
            fill(dto, vo);
            return dto;
        };
    }

    private void fill(GptBookDto dto, GptBookVo vo) {
        if (vo.getQueryWordsList()) {
            LambdaQueryWrapper<GptBookWords> wrapper = Wrappers.<GptBookWords>lambdaQuery().eq(GptBookWords::getBookId, dto.getId());
            List<GptWords> wordsList = bookWordsService.queryWordsList(wrapper);
            dto.setWordsList(wordsList);
        }
    }

    @Override
    public IPage queryPage(Page page, GptBookVo vo) {
        GptBook query = BeanUtil.copyProperties(vo, GptBook.class);
        LambdaQueryWrapper<GptBook> wrapper = new LambdaQueryWrapper<>(query);

        Page<GptBook> selectPage = this.page(page, wrapper);
        List<GptBookDto> collect = selectPage.getRecords().stream().map(fillFunction(vo)).collect(Collectors.toList());

        Page result = BeanUtil.copyProperties(selectPage, Page.class, "records");
        result.setRecords(collect);
        return result;
    }

    @Override
    public List queryList(GptBookVo vo) {
        GptBook query = BeanUtil.copyProperties(vo, GptBook.class);
        LambdaQueryWrapper<GptBook> wrapper = new LambdaQueryWrapper<>(query);
        List<GptBook> list = this.list(wrapper);
        return list.stream().map(fillFunction(vo)).collect(Collectors.toList());
    }

    @Override
    public GptBook detail(Long id, GptBookVo vo) {
        GptBook source = this.getById(id);
        GptBookDto dto = BeanUtil.copyProperties(source, GptBookDto.class);
        fill(dto,vo);
        return dto;
    }
}