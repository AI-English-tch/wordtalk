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

package com.mmr.wordtalk.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.admin.api.dto.SysLogDTO;
import com.mmr.wordtalk.admin.api.entity.SysLog;
import com.mmr.wordtalk.admin.api.vo.PreLogVO;
import com.mmr.wordtalk.admin.mapper.SysLogMapper;
import com.mmr.wordtalk.admin.service.SysLogService;
import com.mmr.wordtalk.common.core.constant.CommonConstants;
import com.mmr.wordtalk.common.data.tenant.TenantBroker;
import com.mmr.wordtalk.common.data.tenant.TenantContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author 张恩睿
 * @since 2017-11-20
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	/**
	 * 批量插入前端错误日志
	 * @param preLogVoList 日志信息
	 * @return true/false
	 */
	@Override
	public Boolean saveBatchLogs(List<PreLogVO> preLogVoList) {
		List<SysLog> sysLogs = preLogVoList.stream().map(pre -> {
			SysLog log = new SysLog();
			log.setLogType(CommonConstants.STATUS_LOCK);
			log.setTitle(pre.getInfo());
			log.setException(pre.getStack());
			log.setParams(pre.getMessage());
			log.setCreateTime(LocalDateTime.now());
			log.setRequestUri(pre.getUrl());
			log.setCreateBy(pre.getUser());
			return log;
		}).collect(Collectors.toList());
		return this.saveBatch(sysLogs);
	}

	@Override
	public Page getLogByPage(Page page, SysLogDTO sysLog) {

		LambdaQueryWrapper<SysLog> wrapper = Wrappers.lambdaQuery();
		if (StrUtil.isNotBlank(sysLog.getLogType())) {
			wrapper.eq(SysLog::getLogType, sysLog.getLogType());
		}

		if (ArrayUtil.isNotEmpty(sysLog.getCreateTime())) {
			wrapper.ge(SysLog::getCreateTime, sysLog.getCreateTime()[0]).le(SysLog::getCreateTime,
					sysLog.getCreateTime()[1]);
		}

		return baseMapper.selectPage(page, wrapper);
	}

	/**
	 * 插入日志
	 * @param sysLog 日志对象
	 * @return true/false
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveLog(SysLogDTO sysLog) {
		TenantBroker.applyAs(sysLog::getTenantId, tenantId -> {
			TenantContextHolder.setTenantId(tenantId);
			SysLog log = new SysLog();
			BeanUtils.copyProperties(sysLog, log, "createTime");
			return baseMapper.insert(log);
		});
		return Boolean.TRUE;
	}

}
