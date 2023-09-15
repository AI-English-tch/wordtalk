/*
 *    Copyright (c) 2018-2025, mall All rights reserved.
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
 * Author: mall
 */
package com.mmr.wordtalk.ai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.ai.constant.ModelType;
import com.mmr.wordtalk.ai.core.CPMSendStrategy;
import com.mmr.wordtalk.ai.core.ChatGptSendStrategy;
import com.mmr.wordtalk.ai.core.SendStrategy;
import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.ai.entity.AiModel;
import com.mmr.wordtalk.ai.mapper.AiModelMapper;
import com.mmr.wordtalk.ai.service.AiModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AI模型表
 *
 * @author 张恩睿
 * @date 2023-06-29 11:56:29
 */
@Service
@AllArgsConstructor
public class AiModelServiceImpl extends ServiceImpl<AiModelMapper, AiModel> implements AiModelService {

    @Override
    public String sendOnOnce(Long id, SendDto sendDto) {
        SendStrategy strategy = null;
        // 查询模型信息
        AiModel aiModel = this.getById(id);
        // 根据AI模型的类型调用不同的实现体
        if (StrUtil.equals(aiModel.getType(), ModelType.CHATGPT.getValue())) {
            strategy = new ChatGptSendStrategy(aiModel, sendDto.getParams());
        } else if (StrUtil.equals(aiModel.getType(), ModelType.CPMGPT.getValue())) {
            strategy = new CPMSendStrategy();
        }
        return strategy.send(sendDto);
    }

    @Override
    public String sendOnStream(Long id, SendDto sendDto) {
        SendStrategy strategy = null;
        // 查询模型信息
        AiModel aiModel = this.getById(id);
        // 根据AI模型的类型调用不同的实现体
        if (StrUtil.equals(aiModel.getType(), ModelType.CHATGPT.getValue())) {
            strategy = new ChatGptSendStrategy(aiModel, sendDto.getParams());
        } else if (StrUtil.equals(aiModel.getType(), ModelType.CPMGPT.getValue())) {
            strategy = new CPMSendStrategy();
        }
        return strategy.streamSend(sendDto);
    }


}
