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

package com.mmr.wordtalk.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.app.api.dto.AppUserInfo;
import com.mmr.wordtalk.app.api.entity.AppSocialDetails;
import com.mmr.wordtalk.app.api.entity.AppUser;
import com.mmr.wordtalk.app.handler.LoginHandler;
import com.mmr.wordtalk.app.mapper.AppSocialDetailsMapper;
import com.mmr.wordtalk.app.mapper.AppUserMapper;
import com.mmr.wordtalk.app.service.AppSocialDetailsService;
import com.mmr.wordtalk.common.core.constant.CacheConstants;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 张恩睿
 * @date 2018年08月16日
 */
@Slf4j
@AllArgsConstructor
@Service("appSocialDetailsService")
public class AppSocialDetailsServiceImpl extends ServiceImpl<AppSocialDetailsMapper, AppSocialDetails>
		implements AppSocialDetailsService {

	private final Map<String, LoginHandler> loginHandlerMap;

	private final CacheManager cacheManager;

	private final AppUserMapper appUserMapper;

	/**
	 * 绑定社交账号
	 * @param type type
	 * @param code code
	 * @return
	 */
	@Override
	public Boolean bindSocial(String type, String code) {
		LoginHandler loginHandler = loginHandlerMap.get(type);
		// 绑定逻辑
		String identify = loginHandler.identify(code);
		AppUser user = appUserMapper.selectById(SecurityUtils.getUser().getId());
		loginHandler.bind(user, identify);

		// 更新緩存
		cacheManager.getCache(CacheConstants.USER_DETAILS_MINI).evict(user.getUsername());
		return Boolean.TRUE;
	}

	/**
	 * 根据入参查询用户信息
	 * @param inStr TYPE@code
	 * @return
	 */
	@Override
	public AppUserInfo getUserInfo(String inStr) {
		String[] inStrs = inStr.split(StringPool.AT);
		String type = inStrs[0];
		String loginStr = inStr.substring(type.length() + 1);
		return loginHandlerMap.get(type).handle(loginStr);
	}

}
