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

package com.mmr.wordtalk.bridge.mapper;

import com.mmr.wordtalk.bridge.entity.GptTopicEntity;
import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 话题
 *
 * @author 张恩睿
 * @date 2023-06-14 11:11:15
 */
@Mapper
public interface GptTopicMapper extends WordtalkBaseMapper<GptTopicEntity> {

}