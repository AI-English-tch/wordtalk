/*
 *
 *      Copyright (c) 2018-2025, wordtalk All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wordtalk
 *
 */

package com.mmr.wordtalk.admin.mapper;

import com.mmr.wordtalk.admin.api.entity.SysLog;
import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author wordtalk
 * @since 2017-11-20
 */
@Mapper
public interface SysLogMapper extends WordtalkBaseMapper<SysLog> {

}
