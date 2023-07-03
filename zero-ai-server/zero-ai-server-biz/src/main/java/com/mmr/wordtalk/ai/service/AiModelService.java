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

package com.mmr.wordtalk.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.ai.entity.AiModel;

/**
 * AI模型表
 *
 * @author 张恩睿
 * @date 2023-06-29 11:56:29
 */
public interface AiModelService extends IService<AiModel> {

    String sendOnOnce(Long id, SendDto sendDto);

    String sendOnStream(Long id, SendDto sendDto);
}
