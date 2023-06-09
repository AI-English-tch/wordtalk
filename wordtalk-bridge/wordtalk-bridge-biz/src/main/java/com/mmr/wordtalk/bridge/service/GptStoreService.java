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

package com.mmr.wordtalk.bridge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mmr.wordtalk.bridge.entity.GptStore;
import com.mmr.wordtalk.bridge.vo.GptStoreVo;
import com.mmr.wordtalk.common.core.util.R;

import java.util.List;

/**
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
public interface GptStoreService extends IService<GptStore> {

	/**
	 * 将单词列表导入到现有的词库中
	 *
	 * @param id
	 * @param wordsList
	 * @return
	 */
	R importWords(Long id, List<String> wordsList);

	IPage queryPage(Page page, GptStoreVo vo);

	List queryList(Page page, GptStoreVo vo);

	GptStore detail(Long id, GptStoreVo vo);
}