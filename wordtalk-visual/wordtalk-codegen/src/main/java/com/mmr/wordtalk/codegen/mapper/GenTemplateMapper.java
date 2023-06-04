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

package com.mmr.wordtalk.codegen.mapper;

import com.mmr.wordtalk.codegen.entity.GenTemplateEntity;
import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 模板
 *
 * @author PIG
 * @date 2023-02-21 17:15:44
 */
@Mapper
public interface GenTemplateMapper extends WordtalkBaseMapper<GenTemplateEntity> {

	/**
	 * 根据groupId查询 模板
	 * @param groupId
	 * @return
	 */
	List<GenTemplateEntity> listTemplateById(Long groupId);

}
