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

package com.mmr.wordtalk.bridge.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mmr.wordtalk.ai.dto.Context;
import com.mmr.wordtalk.bridge.entity.GptHistory;

import java.util.List;

/**
 * 对话历史
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
public interface GptHistoryService extends IService<GptHistory> {
    /**
     * 获取历史上下文的方法
     *
     * @param robotId 助手的id
     * @param bookId  词书的id
     * @param size    上下文的大小
     * @return
     */
    List<Context> getHistoryContext(Long robotId, Long bookId, Integer size);

    /**
     * 保存上下文到历史的方法
     *
     * @param robotId  助手的id
     * @param bookId   词书的id
     * @param needSave 需要保存的上下文
     */
    void saveContext(Long robotId, Long bookId, List<Context> needSave);
}