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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.entity.GptBookWordsEntity;
import com.mmr.wordtalk.bridge.mapper.GptBookWordsMapper;
import com.mmr.wordtalk.bridge.service.GptBookWordsService;
import org.springframework.stereotype.Service;

/**
 * 词书--单词关联表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:06:27
 */
@Service
public class GptBookWordsServiceImpl extends ServiceImpl<GptBookWordsMapper, GptBookWordsEntity> implements GptBookWordsService {

}