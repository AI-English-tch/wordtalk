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

package com.mmr.wordtalk.admin.api.feign;

import com.mmr.wordtalk.admin.api.entity.SysAuditLog;
import com.mmr.wordtalk.common.core.constant.SecurityConstants;
import com.mmr.wordtalk.common.core.constant.ServiceNameConstants;
import com.mmr.wordtalk.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author wordtalk
 * @date 2023-02-27
 */
@FeignClient(contextId = "remoteAuditLogService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteAuditLogService {

	/**
	 * 保存日志
	 * @param auditLogList 日志实体 列表
	 * @param from 是否内部调用
	 * @return succes、false
	 */
	@PostMapping("/audit")
	R<Boolean> saveLog(@RequestBody List<SysAuditLog> auditLogList, @RequestHeader(SecurityConstants.FROM) String from);

}
