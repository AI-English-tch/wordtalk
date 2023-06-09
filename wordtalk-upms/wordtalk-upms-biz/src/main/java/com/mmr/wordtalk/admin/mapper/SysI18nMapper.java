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

package com.mmr.wordtalk.admin.mapper;

import com.mmr.wordtalk.admin.api.entity.SysI18nEntity;
import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统表-国际化
 *
 * @author PIG
 * @date 2023-02-14 09:07:01
 */
@Mapper
public interface SysI18nMapper extends WordtalkBaseMapper<SysI18nEntity> {

}
